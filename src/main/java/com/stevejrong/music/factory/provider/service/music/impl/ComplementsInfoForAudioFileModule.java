package com.stevejrong.music.factory.provider.service.music.impl;

import com.google.common.collect.Lists;
import com.stevejrong.music.factory.common.util.FileUtil;
import com.stevejrong.music.factory.common.util.SpringBeanUtil;
import com.stevejrong.music.factory.config.SystemConfig;
import com.stevejrong.music.factory.config.sub.FilterGroupsConfig;
import com.stevejrong.music.factory.spi.music.bean.partner.AbstractPartnerSongInfoFilterCriteriaBean;
import com.stevejrong.music.factory.spi.music.bo.AnalyzingForAudioFileModuleBo;
import com.stevejrong.music.factory.spi.music.bo.ComplementedMetadataAudioFileBo;
import com.stevejrong.music.factory.spi.music.bo.analyzing.datasource.PartnerSongInfoBo;
import com.stevejrong.music.factory.spi.music.bo.validator.filtrated.FiltratedResultBo;
import com.stevejrong.music.factory.spi.service.music.AbstractMusicFactoryModule;
import com.stevejrong.music.factory.spi.service.music.IMusicFactoryModule;
import com.stevejrong.music.factory.spi.service.music.analyzing.partner.resolver.IPartnerSongInfoResolver;
import com.stevejrong.music.factory.spi.service.music.filter.BaseFilterChain;
import com.stevejrong.music.factory.spi.service.music.metadata.resolver.persist.IAudioFileMetadataPersistResolver;
import com.stevejrong.music.factory.spi.service.music.metadata.resolver.query.IAudioFileMetadataQueryResolver;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import java.io.IOException;
import java.util.List;

/**
 * Service Implements - 音频文件信息补全
 * <p>
 * 作用：对元数据信息缺失的音频文件进行信息填充补全
 */
public class ComplementsInfoForAudioFileModule extends AbstractMusicFactoryModule implements IMusicFactoryModule<Object> {

    /**
     * 需要补全信息的音频文件信息集合
     */
    private List<AnalyzingForAudioFileModuleBo> needComplementsMusicList;

    /**
     * 当前第三方音乐服务平台数据源配置的Bean名称
     */
    private String partnerSongInfoDataSourceActiveConfigName;

    public List<AnalyzingForAudioFileModuleBo> getNeedComplementsMusicList() {
        return needComplementsMusicList;
    }

    public void setNeedComplementsMusicList(List<AnalyzingForAudioFileModuleBo> needComplementsMusicList) {
        this.needComplementsMusicList = needComplementsMusicList;
    }

    public String getPartnerSongInfoDataSourceActiveConfigName() {
        return partnerSongInfoDataSourceActiveConfigName;
    }

    public void setPartnerSongInfoDataSourceActiveConfigName(String partnerSongInfoDataSourceActiveConfigName) {
        this.partnerSongInfoDataSourceActiveConfigName = partnerSongInfoDataSourceActiveConfigName;
    }

    @Override
    public Object doAction() {
        // 音频文件信息补全成功的歌曲信息集合
        List<ComplementedMetadataAudioFileBo> complementedSuccessMusicFileList = Lists.newArrayList();
        // 音频文件信息补全失败的歌曲信息集合
        List<ComplementedMetadataAudioFileBo> complementedFailureMusicFileList = Lists.newArrayList();

        needComplementsMusicList.forEach(item -> {
            // 读取需要补全信息的音频文件
            AudioFile audioFile = null;
            try {
                audioFile = AudioFileIO.read(FileUtils.getFile(item.getAudioFilePath()));
            } catch (CannotReadException | ReadOnlyFileException | TagException | IOException | InvalidAudioFrameException e) {
                e.printStackTrace();
            }

            ComplementedMetadataAudioFileBo complementedMetadataAudioFileBo = execute(item, audioFile);

            if (null != complementedMetadataAudioFileBo && complementedMetadataAudioFileBo.getType() == 1) {
                complementedSuccessMusicFileList.add(complementedMetadataAudioFileBo);
            } else {
                complementedFailureMusicFileList.add(complementedMetadataAudioFileBo);
            }
        });

        return Lists.newArrayList(complementedSuccessMusicFileList, complementedFailureMusicFileList);
    }

    public ComplementedMetadataAudioFileBo execute(AnalyzingForAudioFileModuleBo analyzingForAudioFileModuleBo, AudioFile audioFile) {
        PartnerSongInfoBo partnerSongInfoBo = getMetadata(analyzingForAudioFileModuleBo, audioFile);

        ComplementedMetadataAudioFileBo complementedMetadataMusicFileBo;
        if (null == partnerSongInfoBo) {
            // 元数据基础信息构建为空，一般是歌曲没有搜索到，返回补全失败的歌曲信息
            complementedMetadataMusicFileBo = new ComplementedMetadataAudioFileBo();
            complementedMetadataMusicFileBo.setFileAbsolutePath(analyzingForAudioFileModuleBo.getAudioFilePath());
            complementedMetadataMusicFileBo.setSongTitle(analyzingForAudioFileModuleBo.getSongTitle());
            complementedMetadataMusicFileBo.setSongArtist(analyzingForAudioFileModuleBo.getSongArtist());
            complementedMetadataMusicFileBo.setType(0);

            return complementedMetadataMusicFileBo;
        }

        persistMetadata(audioFile, partnerSongInfoBo);

        complementedMetadataMusicFileBo = new ComplementedMetadataAudioFileBo();
        complementedMetadataMusicFileBo.setFileAbsolutePath(analyzingForAudioFileModuleBo.getAudioFilePath());
        complementedMetadataMusicFileBo.setSongTitle(analyzingForAudioFileModuleBo.getSongTitle());
        complementedMetadataMusicFileBo.setSongArtist(analyzingForAudioFileModuleBo.getSongArtist());
        complementedMetadataMusicFileBo.setType(1);

        return complementedMetadataMusicFileBo;
    }

    /**
     * 获取音频文件的正确元数据信息
     *
     * @param item
     * @param audioFile
     * @return
     */
    private PartnerSongInfoBo getMetadata(AnalyzingForAudioFileModuleBo item, AudioFile audioFile) {
        SystemConfig systemConfig = SpringBeanUtil.getBean("systemConfig");
        FilterGroupsConfig filterGroupConfig = systemConfig.getFilterGroupsConfig()
                .stream()
                .filter(config -> config.getFilterGroupTag()
                        .equals(partnerSongInfoDataSourceActiveConfigName))
                .findFirst().get();

        AbstractPartnerSongInfoFilterCriteriaBean partnerSongInfoFilterCriteriaBean = filterGroupConfig.getPartnerSongInfoFilterCriteriaBean();

        if (StringUtils.isBlank(item.getSongTitle()) || StringUtils.isBlank(item.getSongArtist())) {
            // 若歌曲标题或歌曲艺术家，有一者为空，则根据音频文件的文件名来获取搜索歌曲时的两个搜索关键字

            // 根据文件名获取两个搜索关键字
            partnerSongInfoFilterCriteriaBean.setSearchKeywords(FileUtil.getSearchSongKeywordsByAudioFileName(item.getAudioFileName()));
        } else {
            // 否则根据音频文件中的元数据信息来获取搜索歌曲时的两个搜索关键字
            AudioHeader audioHeader = audioFile.getAudioHeader();
            String audioFormat = audioHeader.getFormat().toLowerCase();

            IAudioFileMetadataQueryResolver metadataQueryResolver = (IAudioFileMetadataQueryResolver)
                    systemConfig.getAnalysingAndComplementsForAudioFileConfig().getAudioFileMetadataResolvers().get(audioFormat)
                            .stream().filter(resolver -> resolver instanceof IAudioFileMetadataQueryResolver).findAny().get();

            // 歌曲标题
            String songTitle = metadataQueryResolver.getSongTitle(audioFile);
            // 歌曲艺术家
            String songArtist = metadataQueryResolver.getSongArtist(audioFile);

            partnerSongInfoFilterCriteriaBean.setSearchKeywords(songTitle + " " + songArtist);
        }

        BaseFilterChain filterChain = SpringBeanUtil.getBean("baseFilterChain");
        List<FiltratedResultBo> filtratedResultBoList = filterChain.filtrateParams(partnerSongInfoDataSourceActiveConfigName, partnerSongInfoFilterCriteriaBean);

        IPartnerSongInfoResolver partnerSongInfoResolver = ((FilterGroupsConfig)
                SpringBeanUtil.getBean(partnerSongInfoDataSourceActiveConfigName))
                .getPartnerSongInfoResolverBean();

        return partnerSongInfoResolver.getSongInfoByPartnerSongInfo(filtratedResultBoList);
    }

    /**
     * 保存元数据信息
     *
     * @param audioFile
     * @param partnerSongInfoBo
     */
    private void persistMetadata(AudioFile audioFile, PartnerSongInfoBo partnerSongInfoBo) {
        Tag persistTag = audioFile.getTagOrCreateDefault();

        AudioHeader audioHeader = audioFile.getAudioHeader();
        String audioFormat = audioHeader.getFormat().toLowerCase();

        SystemConfig systemConfig = SpringBeanUtil.getBean("systemConfig");
        IAudioFileMetadataPersistResolver metadataPersistResolver = (IAudioFileMetadataPersistResolver)
                systemConfig.getAnalysingAndComplementsForAudioFileConfig().getAudioFileMetadataResolvers().get(audioFormat)
                        .stream().filter(resolver -> resolver instanceof IAudioFileMetadataQueryResolver).findAny().get();

        /*metadataPersistResolver.setSongTitle(audioFile, persistTag, baseMusicInfoBo.getSongTitle());
        metadataPersistResolver.setSongArtist(audioFile, persistTag, baseMusicInfoBo.getSongArtist());
        metadataPersistResolver.setAlbumName(audioFile, persistTag, baseMusicInfoBo.getAlbumName());
        metadataPersistResolver.setAlbumPicture(audioFile, persistTag, baseMusicInfoBo.getAlbumPicture(), baseMusicInfoBo.getSongTitle(), baseMusicInfoBo.getSongArtist());

        metadataPersistResolver.persistMetadata(audioFile, persistTag);*/
    }
}
