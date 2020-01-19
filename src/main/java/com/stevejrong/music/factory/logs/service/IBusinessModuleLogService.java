package com.stevejrong.music.factory.logs.service;

/**
 * 日志业务接口
 */
public interface IBusinessModuleLogService {

    /**
     * 方法正常执行后的日志处理
     *
     * @param params 可选参数
     */
    void afterReturnDoAction(Object... params);

    /**
     * 方法执行期间出现异常的日志处理
     *
     * @param params 可选参数
     */
    void afterExceptionDoAction(Object... params);
}
