package com.stevejrong.music.factory.config.sub;

import com.stevejrong.music.factory.common.util.StringUtil;

/**
 * 系统基础配置类
 */
public class BaseConfig {

    /**
     * Spring配置文件的文件名称
     */
    private String springConfigurationFileName;

    /**
     * 初始欢迎语
     */
    private String welcomeMessage;

    /**
     * 音频文件分析完成提示信息
     */
    private String analyzingCompletedForAudioFileMessage;

    /**
     * 音频文件元数据信息补全完成提示信息
     */
    private String metadataInfoCompletedForAudioFileMessage;

    public String getSpringConfigurationFileName() {
        return springConfigurationFileName;
    }

    public void setSpringConfigurationFileName(String springConfigurationFileName) {
        this.springConfigurationFileName = springConfigurationFileName;
    }

    public String getWelcomeMessage() {
        return welcomeMessage;
    }

    public void setWelcomeMessage(String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }

    public String getAnalyzingCompletedForAudioFileMessage() {
        return analyzingCompletedForAudioFileMessage;
    }

    public void setAnalyzingCompletedForAudioFileMessage(String analyzingCompletedForAudioFileMessage) {
        this.analyzingCompletedForAudioFileMessage = analyzingCompletedForAudioFileMessage;
    }

    public String getMetadataInfoCompletedForAudioFileMessage() {
        return metadataInfoCompletedForAudioFileMessage;
    }

    public void setMetadataInfoCompletedForAudioFileMessage(String metadataInfoCompletedForAudioFileMessage) {
        this.metadataInfoCompletedForAudioFileMessage = metadataInfoCompletedForAudioFileMessage;
    }
}
