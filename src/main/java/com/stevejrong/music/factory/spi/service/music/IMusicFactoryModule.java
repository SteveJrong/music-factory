package com.stevejrong.music.factory.spi.service.music;

/**
 * Service - 通用业务模块接口
 *
 * @param <T>
 */
public interface IMusicFactoryModule<T> {

    /**
     * 业务执行方法
     *
     * @return
     */
    T doAction();
}
