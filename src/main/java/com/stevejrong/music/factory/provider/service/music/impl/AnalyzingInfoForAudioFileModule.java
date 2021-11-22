package com.stevejrong.music.factory.provider.service.music.impl;

import com.google.common.collect.Lists;
import com.stevejrong.music.factory.common.constants.BaseConstants;
import com.stevejrong.music.factory.common.util.SpringBeanUtil;
import com.stevejrong.music.factory.config.SystemConfig;
import com.stevejrong.music.factory.spi.music.bean.MusicInfoByFilterBean;
import com.stevejrong.music.factory.spi.music.bo.AnalyzingForAudioFileModuleBo;
import com.stevejrong.music.factory.spi.music.bo.validator.filtrated.FiltratedResultBo;
import com.stevejrong.music.factory.spi.music.bo.validator.filtrated.FiltratedResultDataBo;
import com.stevejrong.music.factory.spi.service.music.AbstractMusicFactoryModule;
import com.stevejrong.music.factory.spi.service.music.IMusicFactoryModule;
import com.stevejrong.music.factory.spi.service.music.filter.BaseFilterChain;
import com.stevejrong.music.factory.spi.service.music.metadata.resolver.query.IAudioFileMetadataQueryResolver;
import org.apache.commons.collections4.CollectionUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implements - 音频文件信息分析
 * <p>
 * 作用：判断哪些音频文件需要进行数据补全
 */
public class AnalyzingInfoForAudioFileModule extends AbstractMusicFactoryModule implements IMusicFactoryModule<List<AnalyzingForAudioFileModuleBo>> {

    @Override
    public List<AnalyzingForAudioFileModuleBo> doAction() {
        // 需要做信息补全的音频文件信息集合
        List<AnalyzingForAudioFileModuleBo> needComplementsMusicList = Lists.newArrayList();

        try {
            Files.newDirectoryStream(Paths.get(super.getSystemConfig().getAnalysingAndComplementsForAudioFileConfig().getAudioFileDirectory()),
                    path -> path.toString().endsWith(BaseConstants.FILE_SUFFIX_FLAC) || path.toString().endsWith(BaseConstants.FILE_SUFFIX_MP3))
                    .forEach(file -> {
                        AudioFile audioFile = null;
                        try {
                            audioFile = AudioFileIO.read(new File(file.toAbsolutePath().toString()));
                        } catch (CannotReadException | ReadOnlyFileException | InvalidAudioFrameException | TagException | IOException e) {
                            e.printStackTrace();
                        }

                        AudioHeader audioHeader = audioFile.getAudioHeader();
                        String audioFormat = audioHeader.getFormat().toLowerCase();

                        SystemConfig systemConfig = SpringBeanUtil.getBean("systemConfig");
                        IAudioFileMetadataQueryResolver metadataQueryResolver = (IAudioFileMetadataQueryResolver)
                                systemConfig.getAnalysingAndComplementsForAudioFileConfig().getAudioFileMetadataResolvers().get(audioFormat)
                                        .stream().filter(resolver -> resolver instanceof IAudioFileMetadataQueryResolver).findAny().get();

                        // 歌曲标题
                        String songTitle = metadataQueryResolver.getSongTitle(audioFile);
                        // 歌曲艺术家
                        String songArtist = metadataQueryResolver.getSongArtist(audioFile);
                        // 歌曲内嵌歌词
                        String songLyrics = metadataQueryResolver.getSongLyrics(audioFile);
                        // 歌曲所属专辑的名称
                        String albumName = metadataQueryResolver.getAlbumName(audioFile);
                        // 歌曲所属专辑的艺术家
                        String albumArtist = metadataQueryResolver.getAlbumArtist(audioFile);
                        // 歌曲所属专辑的发布时间
                        LocalDate albumPublishDate = metadataQueryResolver.getAlbumPublishDate(audioFile);
                        // 歌曲所属专辑的描述
                        String albumDescription = metadataQueryResolver.getAlbumDescription(audioFile);
                        // 歌曲所属专辑的语言类型
                        String albumLanguage = metadataQueryResolver.getAlbumLanguage(audioFile);
                        // 歌曲所属专辑的版权信息
                        String albumCopyright = metadataQueryResolver.getAlbumCopyright(audioFile);
                        // 歌曲所属的专辑封面
                        byte[] albumPicture = metadataQueryResolver.getAlbumPicture(audioFile);

                        BaseFilterChain filterChain = SpringBeanUtil.getBean("baseFilterChain");

                        MusicInfoByFilterBean musicInfoBean = new MusicInfoByFilterBean.Builder(songTitle, songArtist, albumName, albumPicture)
                                .albumArtist(albumArtist)
                                .albumPublishDate(albumPublishDate)
                                .albumDescription(albumDescription)
                                .albumLanguage(albumLanguage)
                                .albumCopyright(albumCopyright)
                                .songLyrics(songLyrics)
                                .build();
                        List<FiltratedResultBo> filtratedResultBoList = filterChain.filtrateParams(
                                "analyzingInfoForAudioFileFilterConfig", musicInfoBean);

                        List<Boolean> filtratedFalseResultBoList = filtratedResultBoList.stream()
                                .map(FiltratedResultBo::getFiltratedResult)
                                .map(FiltratedResultDataBo::isResult)
                                .filter(item -> !item)
                                .collect(Collectors.toList());

                        if (CollectionUtils.isNotEmpty(filtratedFalseResultBoList)) {
                            /*
                            歌曲标题、歌曲艺术家、歌曲内嵌歌词、歌曲所属专辑的名称、歌曲所属专辑的艺术家、歌曲所属专辑的发布时间、
                            歌曲所属专辑的描述、歌曲所属专辑的语言类型、歌曲所属专辑的版权信息和歌曲所属的专辑封面，有任意一项缺
                            失，则此音乐文件需要进行数据补全
                             */
                            needComplementsMusicList.add(new AnalyzingForAudioFileModuleBo
                                    .Builder(file.toAbsolutePath().toString(), songTitle, songArtist).build());
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return needComplementsMusicList;
    }
}
