/*
 *             Copyright (C) 2022 Steve Jrong
 *
 * 	   GitHub Homepage: https://www.github.com/SteveJrong
 *      Gitee Homepage: https://gitee.com/stevejrong1024
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stevejrong.music.factory.common.util;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射工具类
 *
 * @author Steve Jrong
 * @since 1.0
 */
public final class ReflectionUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 获取反射方法的返回值
     *
     * @param methodName 要进行反射的方法名称
     * @param object     方法反射时所需的实例对象
     * @param parameters 要反射方法的入参
     * @param <T>
     * @return
     */
    public static <T> T getMethodValueByReflect(String methodName, Object object, Object... parameters) {
        Method[] methods = object.getClass().getDeclaredMethods();

        Method correctMethod = null;
        for (Method method : methods) {
            if (methodName.equals(method.getName())) {
                correctMethod = method;
                break;
            }
        }

        try {

            return ArrayUtils.isEmpty(parameters) ? (T) correctMethod.invoke(object)
                    : (T) correctMethod.invoke(object, parameters);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.error(LoggerUtil.builder().append("reflectionUtil_getMethodValueByReflect", "获取反射方法的返回值")
                    .append("exception", e).append("exceptionMsg", e.getMessage()).toString());

            return null;
        }
    }
}