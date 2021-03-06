/*
 *             Copyright (C) 2022 Steve Jrong
 *
 * 	   GitHub Homepage: https://www.github.com/SteveJrong
 *      Gitee Homepage: https://gitee.com/stevejrong1024
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stevejrong.music.factory.provider.service.music.impl;

import com.google.common.collect.Lists;
import com.stevejrong.music.factory.common.util.*;
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
import org.jaudiotagger.tag.TagException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * ????????????????????????
 * <p>
 * ???????????????????????????????????????????????????????????????
 *
 * @author Steve Jrong
 * @since 1.0
 */
public class ComplementsInfoForAudioFileModule extends AbstractMusicFactoryModule implements IMusicFactoryModule<Object> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComplementsInfoForAudioFileModule.class);

    /**
     * ?????????????????????????????????????????????
     */
    private List<AnalyzingForAudioFileModuleBo> needComplementsAudioFileList;

    /**
     * ???????????????????????????????????????????????????Bean??????
     */
    private String partnerSongInfoDataSourceActiveConfigName;

    public List<AnalyzingForAudioFileModuleBo> getNeedComplementsAudioFileList() {
        return needComplementsAudioFileList;
    }

    public void setNeedComplementsAudioFileList(List<AnalyzingForAudioFileModuleBo> needComplementsAudioFileList) {
        this.needComplementsAudioFileList = needComplementsAudioFileList;
    }

    public String getPartnerSongInfoDataSourceActiveConfigName() {
        return partnerSongInfoDataSourceActiveConfigName;
    }

    public void setPartnerSongInfoDataSourceActiveConfigName(String partnerSongInfoDataSourceActiveConfigName) {
        this.partnerSongInfoDataSourceActiveConfigName = partnerSongInfoDataSourceActiveConfigName;
    }

    @Override
    public Object doAction() {
        // ???????????????????????????????????????????????????
        List<ComplementedMetadataAudioFileBo> complementSuccessAudioFileList = Lists.newCopyOnWriteArrayList();
        // ???????????????????????????????????????????????????
        List<ComplementedMetadataAudioFileBo> complementFailureAudioFileList = Lists.newCopyOnWriteArrayList();

        this.getNeedComplementsAudioFileList().forEach(item -> {
            // ???????????????????????????????????????
            AudioFile audioFile = null;

            try {
                audioFile = AudioFileIO.read(FileUtils.getFile(item.getAudioFilePath()));
            } catch (CannotReadException | ReadOnlyFileException | TagException | IOException | InvalidAudioFrameException e) {
                LOGGER.error(LoggerUtil.builder().append("complementsInfoForAudioFileModule_doAction", "????????????????????????")
                        .append("exception", e).append("exceptionMsg", e.getMessage()).append("audioFilePath", item.getAudioFilePath()).toString());
            }

            LOGGER.info(LoggerUtil.builder().append("complementsInfoForAudioFileModule_doAction", "?????????????????????????????????")
                    .append("filePath", item.getAudioFilePath()).toString());

            ComplementedMetadataAudioFileBo complementedMetadataAudioFileBo = this.execute(item, audioFile);
            if (null != complementedMetadataAudioFileBo && complementedMetadataAudioFileBo.getType() == 1) {
                complementSuccessAudioFileList.add(complementedMetadataAudioFileBo);
            } else {
                complementFailureAudioFileList.add(complementedMetadataAudioFileBo);
            }
        });

        return Lists.newArrayList(complementSuccessAudioFileList, complementFailureAudioFileList);
    }

    /**
     * ??????????????????????????????
     *
     * @param analyzingForAudioFileModuleBo
     * @param audioFile                     ??????????????????
     * @return
     */
    public ComplementedMetadataAudioFileBo execute(AnalyzingForAudioFileModuleBo analyzingForAudioFileModuleBo, AudioFile audioFile) {
        // ??????????????????????????????????????????
        PartnerSongInfoBo partnerSongInfoBo = this.getMetadata(analyzingForAudioFileModuleBo, audioFile);

        ComplementedMetadataAudioFileBo complementedMetadataMusicFileBo;
        if (BeanUtil.checkAllFieldsIsNullValue(partnerSongInfoBo)) {
            // ??????????????????????????????????????????????????????????????????????????????????????????????????????
            complementedMetadataMusicFileBo = new ComplementedMetadataAudioFileBo();
            complementedMetadataMusicFileBo.setFileAbsolutePath(analyzingForAudioFileModuleBo.getAudioFilePath());
            complementedMetadataMusicFileBo.setSongTitle(analyzingForAudioFileModuleBo.getSongTitle());
            complementedMetadataMusicFileBo.setSongArtist(analyzingForAudioFileModuleBo.getSongArtist());
            complementedMetadataMusicFileBo.setType(0);

            LOGGER.warn(LoggerUtil.builder().append("complementsInfoForAudioFileModule_execute", "???????????????????????????????????????")
                    .append("audioFilePath", analyzingForAudioFileModuleBo.getAudioFilePath())
                    .append("songTitle", analyzingForAudioFileModuleBo.getSongTitle())
                    .append("songArtist", analyzingForAudioFileModuleBo.getSongArtist())
                    .toString());

            return complementedMetadataMusicFileBo;
        }

        // ?????????????????????
        this.persistMetadata(audioFile, partnerSongInfoBo);

        complementedMetadataMusicFileBo = new ComplementedMetadataAudioFileBo();
        complementedMetadataMusicFileBo.setFileAbsolutePath(analyzingForAudioFileModuleBo.getAudioFilePath());
        complementedMetadataMusicFileBo.setSongTitle(analyzingForAudioFileModuleBo.getSongTitle());
        complementedMetadataMusicFileBo.setSongArtist(analyzingForAudioFileModuleBo.getSongArtist());
        complementedMetadataMusicFileBo.setType(1);

        LOGGER.info(LoggerUtil.builder().append("complementsInfoForAudioFileModule_execute", "?????????????????????????????????????????????")
                .append("filePath", analyzingForAudioFileModuleBo.getAudioFilePath())
                .toString());

        return complementedMetadataMusicFileBo;
    }

    /**
     * ??????????????????????????????????????????
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
            // ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????

            // ??????????????????????????????????????????
            partnerSongInfoFilterCriteriaBean.setSearchKeywords(FileUtil.getSearchSongKeywordsByAudioFileName(item.getAudioFileName()));

            LOGGER.info(LoggerUtil.builder().append("complementsInfoForAudioFileModule_getMetadata")
                    .append("?????????????????????????????????????????????").append("searchSongKeywords", partnerSongInfoFilterCriteriaBean.getSearchKeywords())
                    .append("audioFilePath", audioFile.getFile().getAbsolutePath())
                    .toString());
        } else {
            // ?????????????????????????????????????????????????????????????????????????????????????????????
            AudioHeader audioHeader = audioFile.getAudioHeader();
            String audioFormat = audioHeader.getFormat().toLowerCase();

            IAudioFileMetadataQueryResolver metadataQueryResolver = (IAudioFileMetadataQueryResolver)
                    systemConfig.getAnalysingAndComplementsForAudioFileConfig().getAudioFileMetadataResolvers().get(audioFormat)
                            .stream().filter(resolver -> resolver instanceof IAudioFileMetadataQueryResolver).findAny().get();
            metadataQueryResolver.setAudioFile(audioFile);

            // ????????????
            String songTitle = metadataQueryResolver.getSongTitle();
            // ???????????????
            String songArtist = metadataQueryResolver.getSongArtist();

            partnerSongInfoFilterCriteriaBean.setSearchKeywords(StringUtil.removeSpecialChars(songTitle)
                    + " " + StringUtil.removeSpecialChars(songArtist));

            LOGGER.info(LoggerUtil.builder().append("complementsInfoForAudioFileModule_getMetadata")
                    .append("??????????????????????????????????????????????????????").append("searchSongKeywords", partnerSongInfoFilterCriteriaBean.getSearchKeywords())
                    .append("audioFilePath", audioFile.getFile().getAbsolutePath())
                    .toString());
        }

        BaseFilterChain filterChain = SpringBeanUtil.getBean("baseFilterChain");
        List<FiltratedResultBo> filtratedResultBoList = filterChain.filtrateParams(partnerSongInfoDataSourceActiveConfigName, partnerSongInfoFilterCriteriaBean);

        LOGGER.info(LoggerUtil.builder().append("complementsInfoForAudioFileModule_getMetadata")
                .append("filtratedResultBoList", filtratedResultBoList)
                .append("searchSongKeywords", partnerSongInfoFilterCriteriaBean.getSearchKeywords())
                .append("audioFilePath", audioFile.getFile().getAbsolutePath())
                .toString());

        IPartnerSongInfoResolver partnerSongInfoResolver = ((FilterGroupsConfig)
                SpringBeanUtil.getBean(partnerSongInfoDataSourceActiveConfigName))
                .getPartnerSongInfoResolverBean();
        return partnerSongInfoResolver.getSongInfoByPartnerSongInfo(filtratedResultBoList);
    }

    /**
     * ?????????????????????
     *
     * @param audioFile
     * @param partnerSongInfoBo
     */
    private void persistMetadata(AudioFile audioFile, PartnerSongInfoBo partnerSongInfoBo) {
        AudioHeader audioHeader = audioFile.getAudioHeader();
        String audioFormat = audioHeader.getFormat().toLowerCase();

        SystemConfig systemConfig = SpringBeanUtil.getBean("systemConfig");
        IAudioFileMetadataPersistResolver metadataPersistResolver = (IAudioFileMetadataPersistResolver)
                systemConfig.getAnalysingAndComplementsForAudioFileConfig().getAudioFileMetadataResolvers().get(audioFormat)
                        .stream().filter(resolver -> resolver instanceof IAudioFileMetadataPersistResolver).findAny().get();
        metadataPersistResolver.setAudioFile(audioFile);

        IAudioFileMetadataQueryResolver metadataQueryResolver = (IAudioFileMetadataQueryResolver)
                systemConfig.getAnalysingAndComplementsForAudioFileConfig().getAudioFileMetadataResolvers().get(audioFormat)
                        .stream().filter(resolver -> resolver instanceof IAudioFileMetadataQueryResolver).findAny().get();
        metadataPersistResolver.setIAudioFileMetadataQueryResolver(metadataQueryResolver);

        metadataPersistResolver.setSongTitle(partnerSongInfoBo.getSongTitle());
        metadataPersistResolver.setSongArtist(partnerSongInfoBo.getSongArtist());
        metadataPersistResolver.setAlbumName(partnerSongInfoBo.getAlbumName());
        metadataPersistResolver.setAlbumPicture(partnerSongInfoBo.getAlbumPicture(), false);
        metadataPersistResolver.setSongLyrics(partnerSongInfoBo.getSongLyrics());
        metadataPersistResolver.setAlbumArtist(partnerSongInfoBo.getAlbumArtist());
        metadataPersistResolver.setAlbumPublishDate(partnerSongInfoBo.getAlbumPublishDate());
        metadataPersistResolver.setAlbumDescription(partnerSongInfoBo.getAlbumDescription());
        metadataPersistResolver.setAlbumLanguage(partnerSongInfoBo.getAlbumLanguage());
        metadataPersistResolver.setAlbumCopyright(partnerSongInfoBo.getAlbumCopyright());
    }
}
