package com.stevejrong.music.factory.config;

import com.stevejrong.music.factory.config.sub.*;

import java.util.List;

/**
 * 系统配置类
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
}
