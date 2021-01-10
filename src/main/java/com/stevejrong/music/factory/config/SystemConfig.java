package com.stevejrong.music.factory.config;

/**
 * 系统配置类
 */
public class SystemConfig {

    /**
     * 系统基础配置
     */
    private BaseConfig baseConfig;

    /**
     * 分析和补全音乐信息配置
     */
    private AnalysisAndComplementsMusicInfoConfig analysisAndComplementsMusicInfoConfig;

    /**
     * 音频文件转换配置
     */
    private ConvertMusicFileConfig convertMusicFileConfig;

    public BaseConfig getBaseConfig() {
        return baseConfig;
    }

    public void setBaseConfig(BaseConfig baseConfig) {
        this.baseConfig = baseConfig;
    }

    public AnalysisAndComplementsMusicInfoConfig getAnalysisAndComplementsMusicInfoConfig() {
        return analysisAndComplementsMusicInfoConfig;
    }

    public void setAnalysisAndComplementsMusicInfoConfig(AnalysisAndComplementsMusicInfoConfig analysisAndComplementsMusicInfoConfig) {
        this.analysisAndComplementsMusicInfoConfig = analysisAndComplementsMusicInfoConfig;
    }

    public ConvertMusicFileConfig getConvertMusicFileConfig() {
        return convertMusicFileConfig;
    }

    public void setConvertMusicFileConfig(ConvertMusicFileConfig convertMusicFileConfig) {
        this.convertMusicFileConfig = convertMusicFileConfig;
    }
}
