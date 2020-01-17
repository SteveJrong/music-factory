package com.stevejrong.music.factory.module.impl;

import com.stevejrong.music.factory.analysis.datasource.IPartnerDataSource;
import com.stevejrong.music.factory.analysis.datasource.IPartnerMusicInfoResolver;
import com.stevejrong.music.factory.analysis.datasource.bo.BaseMusicInfoBo;
import com.stevejrong.music.factory.analysis.metadata.persist.resolver.IFileMetadataPersistResolver;
import com.stevejrong.music.factory.analysis.metadata.query.resolver.IFileMetadataQueryResolver;
import com.stevejrong.music.factory.common.enums.FileMetadataPersistResolverEnums;
import com.stevejrong.music.factory.common.enums.FileMetadataQueryResolverEnums;
import com.stevejrong.music.factory.module.AbstractBusinessModule;
import com.stevejrong.music.factory.module.IBusinessModule;
import com.stevejrong.music.factory.module.bo.AnalysisOriginalMusicFileModuleBo;
import com.stevejrong.music.factory.util.FileUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 补全音乐元数据信息
 * 作用：对缺失必要元数据信息的音频文件进行数据补全
 */
public class ComplementsMusicInfoModule extends AbstractBusinessModule implements IBusinessModule {

    /**
     * 需要补全信息的音频文件信息集合
     */
    @Getter
    @Setter
    List<AnalysisOriginalMusicFileModuleBo> needComplementsMusicList;

    /**
     * 第三方音乐数据源Bean名称
     */
    @Getter
    @Setter
    private String partnerDataSourceBeanName;

    /**
     * 第三方音乐信息解析器Bean名称
     */
    @Getter
    @Setter
    private String partnerMusicInfoResolverBeanName;

    @Override
    public Object doAction() {
        ApplicationContext context = new ClassPathXmlApplicationContext(super.springConfigurationFileName);

        needComplementsMusicList.forEach(item -> {

            // 读取要补全信息的音频文件
            AudioFile audioFile = null;
            try {
                audioFile = AudioFileIO.read(new File(item.getFileAbsolutePath()));
            } catch (CannotReadException | ReadOnlyFileException | TagException | IOException | InvalidAudioFrameException e) {
                e.printStackTrace();
            }

            Tag tag = audioFile.getTag();

            String[] keywordsBySearchSong = new String[2];
            if (StringUtils.isEmpty(item.getSongName()) || StringUtils.isBlank(item.getSongName())
                    || StringUtils.isEmpty(item.getSingerName()) || StringUtils.isBlank(item.getSingerName())) {
                // 如果歌曲名称和演唱者有一者为空，则根据音频文件的文件名来获取搜索歌曲时的两个搜索关键字

                // 根据文件名获取两个搜索关键字
                keywordsBySearchSong = FileUtil.getSearchSongKeywordsByFileName(item.getFileName());
            } else {
                // 否则根据音频文件中的元数据信息来获取搜索歌曲时的两个搜索关键字
                AudioHeader audioHeader = audioFile.getAudioHeader();
                String encodingType = audioHeader.getEncodingType();

                String resolverBeanName = FileMetadataQueryResolverEnums.getResolverBeanNameByEncodingType(encodingType);
                IFileMetadataQueryResolver fileMetadataQueryResolver = (IFileMetadataQueryResolver) context.getBean(resolverBeanName);

                // 歌曲名称
                String songName = fileMetadataQueryResolver.getSongName(audioFile);
                // 演唱者
                String singerName = fileMetadataQueryResolver.getSingerName(audioFile);

                keywordsBySearchSong[0] = songName;
                keywordsBySearchSong[1] = singerName;
            }

            // 调用第三方搜索歌曲API，传入两个搜索关键字进行搜索
            IPartnerDataSource partnerDataSource = (IPartnerDataSource) context.getBean(partnerDataSourceBeanName);
            String searchSongResult = partnerDataSource.searchSongByKeywords(keywordsBySearchSong[0], keywordsBySearchSong[1]);

            // 实例化对应的第三方音乐提供商解析器，解析到有用的音乐信息
            IPartnerMusicInfoResolver<BaseMusicInfoBo> partnerMusicInfoResolver = (IPartnerMusicInfoResolver<BaseMusicInfoBo>) context.getBean(partnerMusicInfoResolverBeanName);
            BaseMusicInfoBo baseMusicInfoBo = partnerMusicInfoResolver.getMusicInfoBySearchSongResult(searchSongResult);
            // 查询并设置专辑图片
            byte[] albumPicByteArray = partnerDataSource.searchAlbumPicByPartnerCredential(baseMusicInfoBo.getPartnerCredentialBySearchAlbum());
            baseMusicInfoBo.setAlbumPic(albumPicByteArray);

            /**
             * 设置并保存元数据信息
             */
            Tag persistTag = audioFile.getTag();

            AudioHeader audioHeader = audioFile.getAudioHeader();
            String encodingType = audioHeader.getEncodingType();

            String resolverBeanName = FileMetadataPersistResolverEnums.getResolverBeanNameByEncodingType(encodingType);
            IFileMetadataPersistResolver fileMetadataPersistResolver = (IFileMetadataPersistResolver) context.getBean(resolverBeanName);

            fileMetadataPersistResolver.setSongName(audioFile, persistTag, baseMusicInfoBo.getSongName());
            fileMetadataPersistResolver.setSingerName(audioFile, persistTag, baseMusicInfoBo.getSingerName());
            fileMetadataPersistResolver.setAlbumName(audioFile, persistTag, baseMusicInfoBo.getAlbumName());
            fileMetadataPersistResolver.setAlbumPic(audioFile, persistTag, baseMusicInfoBo.getAlbumPic(), baseMusicInfoBo.getSongName(), baseMusicInfoBo.getSingerName());

            fileMetadataPersistResolver.persistMetadata(audioFile, persistTag);
        });

        return null;
    }
}
