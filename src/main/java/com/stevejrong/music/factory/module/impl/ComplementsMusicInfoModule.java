package com.stevejrong.music.factory.module.impl;

import com.google.common.collect.Lists;
import com.stevejrong.music.factory.analysis.datasource.IPartnerDataSource;
import com.stevejrong.music.factory.analysis.datasource.IPartnerMusicInfoResolver;
import com.stevejrong.music.factory.analysis.datasource.bo.BaseMusicInfoBo;
import com.stevejrong.music.factory.analysis.metadata.persist.resolver.IFileMetadataPersistResolver;
import com.stevejrong.music.factory.analysis.metadata.query.resolver.IFileMetadataQueryResolver;
import com.stevejrong.music.factory.common.constants.BaseConstants;
import com.stevejrong.music.factory.common.enums.FileMetadataPersistResolverEnums;
import com.stevejrong.music.factory.common.enums.FileMetadataQueryResolverEnums;
import com.stevejrong.music.factory.common.exception.NoSearchResultOfSongException;
import com.stevejrong.music.factory.module.AbstractBusinessModule;
import com.stevejrong.music.factory.module.IBusinessModule;
import com.stevejrong.music.factory.module.bo.AnalysisOriginalMusicFileModuleBo;
import com.stevejrong.music.factory.module.bo.ComplementedMetadataMusicFileBo;
import com.stevejrong.music.factory.util.FileUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ArrayUtils;
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
public class ComplementsMusicInfoModule extends AbstractBusinessModule implements IBusinessModule<Object> {

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
        // 补全信息成功的歌曲信息集合
        List<ComplementedMetadataMusicFileBo> complementedSuccessMusicFileList = Lists.newArrayList();
        // 补全信息失败的歌曲信息集合
        List<ComplementedMetadataMusicFileBo> complementedFailureMusicFileList = Lists.newArrayList();

        needComplementsMusicList.forEach(item -> {

            // 读取要补全信息的音频文件
            AudioFile audioFile = null;
            try {
                audioFile = AudioFileIO.read(new File(item.getFileAbsolutePath()));
            } catch (CannotReadException | ReadOnlyFileException | TagException | IOException | InvalidAudioFrameException e) {
                e.printStackTrace();
            }

            ComplementedMetadataMusicFileBo complementedMetadataMusicFileBo = execute(item, audioFile);

            if (null != complementedMetadataMusicFileBo && complementedMetadataMusicFileBo.getType() == 1) {
                complementedSuccessMusicFileList.add(complementedMetadataMusicFileBo);
            } else {
                complementedFailureMusicFileList.add(complementedMetadataMusicFileBo);
            }
        });

        return Lists.newArrayList(complementedSuccessMusicFileList, complementedFailureMusicFileList);
    }

    public ComplementedMetadataMusicFileBo execute(AnalysisOriginalMusicFileModuleBo analysisOriginalMusicFileModuleBo, AudioFile audioFile) {
        ApplicationContext context = new ClassPathXmlApplicationContext(super.springConfigurationFileName);

        BaseMusicInfoBo baseMusicInfoBo = buildMetadata(context, analysisOriginalMusicFileModuleBo, audioFile);

        ComplementedMetadataMusicFileBo complementedMetadataMusicFileBo;
        if (null == baseMusicInfoBo) {
            // 元数据基础信息构建为空，一般是歌曲没有搜索到，返回补全失败的歌曲信息
            complementedMetadataMusicFileBo = new ComplementedMetadataMusicFileBo();
            complementedMetadataMusicFileBo.setFileAbsolutePath(analysisOriginalMusicFileModuleBo.getFileAbsolutePath());
            complementedMetadataMusicFileBo.setSongName(analysisOriginalMusicFileModuleBo.getSongName());
            complementedMetadataMusicFileBo.setSingerName(analysisOriginalMusicFileModuleBo.getSingerName());
            complementedMetadataMusicFileBo.setType(0);

            return complementedMetadataMusicFileBo;
        }

        persistMetadata(context, audioFile, baseMusicInfoBo);

        complementedMetadataMusicFileBo = new ComplementedMetadataMusicFileBo();
        complementedMetadataMusicFileBo.setFileAbsolutePath(analysisOriginalMusicFileModuleBo.getFileAbsolutePath());
        complementedMetadataMusicFileBo.setSongName(analysisOriginalMusicFileModuleBo.getSongName());
        complementedMetadataMusicFileBo.setSingerName(analysisOriginalMusicFileModuleBo.getSingerName());
        complementedMetadataMusicFileBo.setType(1);

        return complementedMetadataMusicFileBo;
    }

    /**
     * 构建音频文件的正确元数据信息
     *
     * @param context
     * @param item
     * @param audioFile
     * @return
     */
    private BaseMusicInfoBo buildMetadata(ApplicationContext context, AnalysisOriginalMusicFileModuleBo item, AudioFile audioFile) {
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
        BaseMusicInfoBo baseMusicInfoBo;
        try {
            baseMusicInfoBo = partnerMusicInfoResolver.getMusicInfoBySearchSongResult(searchSongResult);
        } catch (NoSearchResultOfSongException e) {
            return null;
        }

        // 查询并设置专辑图片
        byte[] albumPicByteArray = partnerDataSource.searchAlbumPicByPartnerCredential(baseMusicInfoBo.getPartnerCredentialBySearchAlbum());
        if (ArrayUtils.isEmpty(albumPicByteArray)) {
            baseMusicInfoBo.setAlbumPic(FileUtil.imgFileToByteArray(BaseConstants.DEFAULT_ALBUM_PIC_FILE_PATH));
        } else {
            baseMusicInfoBo.setAlbumPic(albumPicByteArray);
        }

        return baseMusicInfoBo;
    }

    /**
     * 保存元数据信息
     *
     * @param context
     * @param audioFile
     * @param baseMusicInfoBo
     */
    private void persistMetadata(ApplicationContext context, AudioFile audioFile, BaseMusicInfoBo baseMusicInfoBo) {
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
    }
}
