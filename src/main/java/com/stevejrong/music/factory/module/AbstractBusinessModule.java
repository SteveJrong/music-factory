package com.stevejrong.music.factory.module;

import lombok.Getter;
import lombok.Setter;

/**
 * 业务模块抽象类
 */
public abstract class AbstractBusinessModule {

    /**
     * Spring配置文件名
     */
    @Getter
    @Setter
    protected String springConfigurationFileName;
}
