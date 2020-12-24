package com.stevejrong.music.factory.module;

/**
 * 业务模块抽象类
 */
public abstract class AbstractBusinessModule {

    /**
     * Spring配置文件名
     */
    protected String springConfigurationFileName;

    public String getSpringConfigurationFileName() {
        return springConfigurationFileName;
    }

    public void setSpringConfigurationFileName(String springConfigurationFileName) {
        this.springConfigurationFileName = springConfigurationFileName;
    }
}
