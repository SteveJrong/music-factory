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
package com.stevejrong.music.factory.config;


import com.stevejrong.music.factory.config.sub.*;

import java.util.List;

/**
 * 系统配置类
 *
 * @author Steve Jrong
 * @since 1.0
 */
public class SystemConfig {

    /**
     * 系统基础配置
     */
    private BaseConfig baseConfig;

    /**
     * 过滤器组配置
     */
    private List<FilterGroupsConfig> filterGroupsConfig;

    /**
     * 分析和补全音乐信息配置
     */
    private AnalysingAndComplementsForAudioFileConfig analysingAndComplementsForAudioFileConfig;

    /**
     * 音频文件转换配置
     */
    private AudioFileFormatConversionConfig audioFileFormatConversionConfig;

    /**
     * 音频文件专辑封面压缩配置
     */
    private AlbumPictureCompressionConfig albumPictureCompressionConfig;

    public BaseConfig getBaseConfig() {
        return baseConfig;
    }

    public void setBaseConfig(BaseConfig baseConfig) {
        this.baseConfig = baseConfig;
    }

    public List<FilterGroupsConfig> getFilterGroupsConfig() {
        return filterGroupsConfig;
    }

    public void setFilterGroupsConfig(List<FilterGroupsConfig> filterGroupsConfig) {
        this.filterGroupsConfig = filterGroupsConfig;
    }

    public AnalysingAndComplementsForAudioFileConfig getAnalysingAndComplementsForAudioFileConfig() {
        return analysingAndComplementsForAudioFileConfig;
    }

    public void setAnalysingAndComplementsForAudioFileConfig(AnalysingAndComplementsForAudioFileConfig analysingAndComplementsForAudioFileConfig) {
        this.analysingAndComplementsForAudioFileConfig = analysingAndComplementsForAudioFileConfig;
    }

    public AudioFileFormatConversionConfig getAudioFileFormatConversionConfig() {
        return audioFileFormatConversionConfig;
    }

    public void setAudioFileFormatConversionConfig(AudioFileFormatConversionConfig audioFileFormatConversionConfig) {
        this.audioFileFormatConversionConfig = audioFileFormatConversionConfig;
    }

    public AlbumPictureCompressionConfig getAlbumPictureCompressionConfig() {
        return albumPictureCompressionConfig;
    }

    public void setAlbumPictureCompressionConfig(AlbumPictureCompressionConfig albumPictureCompressionConfig) {
        this.albumPictureCompressionConfig = albumPictureCompressionConfig;
    }
}
