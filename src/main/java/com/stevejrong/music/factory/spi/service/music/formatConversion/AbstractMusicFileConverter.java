package com.stevejrong.music.factory.spi.service.music.formatConversion;

import com.stevejrong.music.factory.config.SystemConfig;

public abstract class AbstractMusicFileConverter implements IAudioFileConverter {

    private SystemConfig systemConfig;

    public SystemConfig getSystemConfig() {
        return systemConfig;
    }

    public void setSystemConfig(SystemConfig systemConfig) {
        this.systemConfig = systemConfig;
    }
}
