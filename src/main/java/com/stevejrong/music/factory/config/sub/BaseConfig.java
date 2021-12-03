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

    /**
     * 自定义音频文件存放目录提示信息
     */
    private String customAudioFileDirectoryMessage;

    /**
     * 自定义转换格式后的音频文件存放目录提示信息
     */
    private String customConvertedAudioFileDirectoryMessage;

    /**
     * 自定义音频文件存放目录成功的提示信息
     */
    private String customAudioFileDirectorySuccessMessage;

    /**
     * 自定义转换格式后的音频文件存放目录成功的提示信息
     */
    private String customConvertedAudioFileDirectorySuccessMessage;

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

    public String getCustomAudioFileDirectoryMessage() {
        return customAudioFileDirectoryMessage;
    }

    public void setCustomAudioFileDirectoryMessage(String customAudioFileDirectoryMessage) {
        this.customAudioFileDirectoryMessage = customAudioFileDirectoryMessage;
    }

    public String getCustomAudioFileDirectorySuccessMessage() {
        return customAudioFileDirectorySuccessMessage;
    }

    public void setCustomAudioFileDirectorySuccessMessage(String customAudioFileDirectorySuccessMessage) {
        this.customAudioFileDirectorySuccessMessage = customAudioFileDirectorySuccessMessage;
    }

    public String getCustomConvertedAudioFileDirectoryMessage() {
        return customConvertedAudioFileDirectoryMessage;
    }

    public void setCustomConvertedAudioFileDirectoryMessage(String customConvertedAudioFileDirectoryMessage) {
        this.customConvertedAudioFileDirectoryMessage = customConvertedAudioFileDirectoryMessage;
    }

    public String getCustomConvertedAudioFileDirectorySuccessMessage() {
        return customConvertedAudioFileDirectorySuccessMessage;
    }

    public void setCustomConvertedAudioFileDirectorySuccessMessage(String customConvertedAudioFileDirectorySuccessMessage) {
        this.customConvertedAudioFileDirectorySuccessMessage = customConvertedAudioFileDirectorySuccessMessage;
    }
}
