package com.stevejrong.music.factory.spi.service.music;

import com.stevejrong.music.factory.config.SystemConfig;

/**
 * 业务模块抽象类
 */
public abstract class AbstractMusicFactoryModule {

    /**
     * 系统配置
     */
    private SystemConfig systemConfig;

    public SystemConfig getSystemConfig() {
        return systemConfig;
    }

    public void setSystemConfig(SystemConfig systemConfig) {
        this.systemConfig = systemConfig;
    }
}
