package com.stevejrong.music.factory.module;

/**
 * 业务模块接口
 *
 * @param <T>
 */
public interface IBusinessModule<T> {

    /**
     * 业务执行方法
     *
     * @return
     */
    T doAction();
}
