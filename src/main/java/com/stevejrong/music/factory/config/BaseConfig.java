package com.stevejrong.music.factory.config;

/**
 * 系统基础配置类
 */
public class BaseConfig {

    /**
     * Spring配置文件的文件名称
     */
    private String springConfigurationFileName;

    public String getSpringConfigurationFileName() {
        return springConfigurationFileName;
    }

    public void setSpringConfigurationFileName(String springConfigurationFileName) {
        this.springConfigurationFileName = springConfigurationFileName;
    }
}
