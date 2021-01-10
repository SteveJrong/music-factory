package com.stevejrong.music.factory.module.impl;

import com.google.common.collect.Lists;
import com.stevejrong.music.factory.analysis.metadata.query.resolver.IFileMetadataQueryResolver;
import com.stevejrong.music.factory.common.constants.BaseConstants;
import com.stevejrong.music.factory.common.enums.FileMetadataQueryResolverEnums;
import com.stevejrong.music.factory.config.SystemConfig;
import com.stevejrong.music.factory.module.AbstractBusinessModule;
import com.stevejrong.music.factory.module.IBusinessModule;
import com.stevejrong.music.factory.module.bo.AnalysisOriginalMusicFileModuleBo;
import org.apache.commons.lang3.StringUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * 分析原始音频文件
 * 作用：判断哪些音频文件需要进行数据补全
 */
public class AnalysisOriginalMusicFileModule extends AbstractBusinessModule implements IBusinessModule<List<AnalysisOriginalMusicFileModuleBo>> {

    /**
     * 系统配置
     */
    private SystemConfig systemConfig;

    public SystemConfig getSystemConfig() {
        return systemConfig;
    }

    public void setSystemConfig(SystemConfig systemConfig) {
        this.systemConfig = systemConfig;
    }

    @Override
    public List<AnalysisOriginalMusicFileModuleBo> doAction() {
        ApplicationContext context = new ClassPathXmlApplicationContext(systemConfig.getBaseConfig().getSpringConfigurationFileName());
        // 需要做信息补全的音频文件信息集合
        List<AnalysisOriginalMusicFileModuleBo> needComplementsMusicList = Lists.newArrayList();

        try {
            Files.newDirectoryStream(Paths.get(systemConfig.getAnalysisAndComplementsMusicInfoConfig().getMusicFileDirectoryOfOriginal()),
                    path -> path.toString().endsWith(BaseConstants.FILE_SUFFIX_FLAC) || path.toString().endsWith(BaseConstants.FILE_SUFFIX_MP3))
                    .forEach(file -> {
                        AudioFile audioFile = null;
                        try {
                            audioFile = AudioFileIO.read(new File(file.toAbsolutePath().toString()));
                        } catch (CannotReadException | ReadOnlyFileException | InvalidAudioFrameException | TagException | IOException e) {
                            e.printStackTrace();
                        }

                        AudioHeader audioHeader = audioFile.getAudioHeader();
                        String encodingType = audioHeader.getEncodingType();

                        String resolverBeanName = FileMetadataQueryResolverEnums.getResolverBeanNameByEncodingType(encodingType);
                        IFileMetadataQueryResolver fileMetadataQueryResolver = (IFileMetadataQueryResolver) context.getBean(resolverBeanName);

                        // 歌曲名称
                        String songName = fileMetadataQueryResolver.getSongName(audioFile);
                        // 演唱者
                        String singerName = fileMetadataQueryResolver.getSingerName(audioFile);
                        // 专辑名称
                        String albumName = fileMetadataQueryResolver.getAlbumName(audioFile);
                        // 专辑图片
                        byte[] albumPic = fileMetadataQueryResolver.getAlbumPic(audioFile);

                        if ((StringUtils.isEmpty(songName) || StringUtils.isBlank(songName))
                                || (StringUtils.isEmpty(singerName) || StringUtils.isBlank(singerName))
                                || (StringUtils.isEmpty(albumName) || StringUtils.isBlank(albumName))
                                || (null == albumPic || albumPic.length <= 0)) {
                            // 歌曲名、演唱者、专辑名称或专辑图片，有任意一项缺失，则此音乐文件需要进行数据补全

                            AnalysisOriginalMusicFileModuleBo analysisOriginalMusicFileModuleBo = new AnalysisOriginalMusicFileModuleBo();
                            analysisOriginalMusicFileModuleBo.setFileAbsolutePath(file.toAbsolutePath().toString());
                            analysisOriginalMusicFileModuleBo.setFileName(file.getFileName().toString().substring(0, file.getFileName().toString().lastIndexOf(".")));
                            analysisOriginalMusicFileModuleBo.setSongName(songName);
                            analysisOriginalMusicFileModuleBo.setSingerName(singerName);

                            needComplementsMusicList.add(analysisOriginalMusicFileModuleBo);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return needComplementsMusicList;
    }
}
