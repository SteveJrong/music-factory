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
import com.stevejrong.music.factory.common.util.FileUtil;
import com.stevejrong.music.factory.common.util.LoggerUtil;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ????????????????????????
 * <p>
 * ????????????????????????????????????????????????
 *
 * @author Steve Jrong
 * @since 1.0
 */
public class AnalyzingInfoForAudioFileModule extends AbstractMusicFactoryModule implements IMusicFactoryModule<List<AnalyzingForAudioFileModuleBo>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnalyzingInfoForAudioFileModule.class);

    @Override
    public List<AnalyzingForAudioFileModuleBo> doAction() {
        // ????????????????????????????????????????????????
        List<AnalyzingForAudioFileModuleBo> needComplementsMusicList = Lists.newCopyOnWriteArrayList();

        try {
            // ??????????????????????????????????????????????????????????????????
            Files.list(Paths.get(super.getSystemConfig().getAnalysingAndComplementsForAudioFileConfig().getAudioFileDirectory()))
                    .filter(path -> {
                                if (Files.isDirectory(path)) {
                                    // ?????????????????????????????????????????????
                                    return false;
                                }

                                for (Map.Entry<String, List> item : super.getSystemConfig().getAnalysingAndComplementsForAudioFileConfig().getAudioFileMetadataResolvers().entrySet()) {
                                    if (item.getKey().equals(FileUtil.getFileSuffixWithoutPoint(path.toAbsolutePath().toString()))) {
                                        return true;
                                    }
                                }

                                return false;
                            }
                    )
                    .forEach(file -> {
                        AudioFile audioFile = null;

                        try {
                            audioFile = AudioFileIO.read(new File(file.toAbsolutePath().toString()));
                        } catch (Exception e) {
                            LOGGER.error(LoggerUtil.builder().append("analyzingInfoForAudioFileModule_doAction", "????????????????????????")
                                    .append("exception", e).append("exceptionMsg", e.getMessage()).append("audioFilePath", file.toAbsolutePath().toString()).toString());
                        }

                        AudioHeader audioHeader = audioFile.getAudioHeader();
                        String audioFormat = audioHeader.getFormat().toLowerCase();

                        SystemConfig systemConfig = SpringBeanUtil.getBean("systemConfig");
                        IAudioFileMetadataQueryResolver metadataQueryResolver = (IAudioFileMetadataQueryResolver)
                                systemConfig.getAnalysingAndComplementsForAudioFileConfig().getAudioFileMetadataResolvers().get(audioFormat)
                                        .stream().filter(resolver -> resolver instanceof IAudioFileMetadataQueryResolver).findAny().get();
                        metadataQueryResolver.setAudioFile(audioFile);

                        // ????????????
                        String songTitle = metadataQueryResolver.getSongTitle();
                        // ???????????????
                        String songArtist = metadataQueryResolver.getSongArtist();
                        // ??????????????????
                        String songLyrics = metadataQueryResolver.getSongLyrics();
                        // ???????????????????????????
                        String albumName = metadataQueryResolver.getAlbumName();
                        // ??????????????????????????????
                        String albumArtist = metadataQueryResolver.getAlbumArtist();
                        // ?????????????????????????????????
                        LocalDate albumPublishDate = metadataQueryResolver.getAlbumPublishDate();
                        // ???????????????????????????
                        String albumDescription = metadataQueryResolver.getAlbumDescription();
                        // ?????????????????????????????????
                        String albumLanguage = metadataQueryResolver.getAlbumLanguage();
                        // ?????????????????????????????????
                        String albumCopyright = metadataQueryResolver.getAlbumCopyright();
                        // ???????????????????????????
                        byte[] albumPicture = metadataQueryResolver.getAlbumPicture(true);

                        BaseFilterChain filterChain = SpringBeanUtil.getBean("baseFilterChain");

                        MusicInfoByFilterBean musicInfoBean = new MusicInfoByFilterBean.Builder(songTitle, songArtist, albumName, albumPicture)
                                .albumArtist(albumArtist)
                                .albumPublishDate(albumPublishDate)
                                .albumDescription(albumDescription)
                                .albumLanguage(albumLanguage)
                                .albumCopyright(albumCopyright)
                                .songLyrics(songLyrics)
                                .build();

                        LOGGER.info(LoggerUtil.builder().append("analyzingInfoForAudioFileModule_doAction", "??????????????????")
                                .append("audioFilePath", file.getFileName().toAbsolutePath())
                                .append("musicInfoBean", musicInfoBean)
                                .toString());

                        List<FiltratedResultBo> filtratedResultBoList = filterChain.filtrateParams(
                                "analyzingInfoForAudioFileFilterConfig", musicInfoBean);
                        List<Boolean> filtratedFalseResultBoList = filtratedResultBoList.stream()
                                .map(FiltratedResultBo::getFiltratedResult)
                                .map(FiltratedResultDataBo::isResult)
                                .filter(item -> !item)
                                .collect(Collectors.toList());

                        LOGGER.info(LoggerUtil.builder().append("analyzingInfoForAudioFileModule_doAction", "????????????????????????")
                                .append("audioFilePath", file.getFileName().toAbsolutePath())
                                .append("filtratedFalseResultBoList", filtratedFalseResultBoList)
                                .toString());

                        if (CollectionUtils.isNotEmpty(filtratedFalseResultBoList)) {
                            /*
                            ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                            ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                            ????????????????????????????????????????????????
                             */
                            needComplementsMusicList.add(new AnalyzingForAudioFileModuleBo
                                    .Builder(file.toAbsolutePath().toString(), songTitle, songArtist).build());
                        }
                    });
        } catch (IOException e) {
            LOGGER.error(LoggerUtil.builder().append("analyzingInfoForAudioFileModule_doAction", "??????????????????????????????")
                    .append("exception", e).append("exceptionMsg", e.getMessage()).toString());
        }

        return needComplementsMusicList;
    }
}
