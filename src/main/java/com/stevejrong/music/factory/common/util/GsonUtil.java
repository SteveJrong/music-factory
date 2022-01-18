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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stevejrong.music.factory.common.util.sub.DefineTypeAdapterByGson;

/**
 * @author Steve Jrong
 * create date: 2021-11-19 5:06 PM
 * @since 1.0
 */
public final class GsonUtil {
    private static final Gson DEFAULT_GSON_INSTANCE;
    private static final GsonBuilder GSON_BUILDER;

    static {
        GSON_BUILDER = new GsonBuilder();
        DEFAULT_GSON_INSTANCE = GSON_BUILDER
                .setDateFormat(DateTimeUtil.DatePattern.YYYYMMDD_HHMMSS_FORMAT.getValue())
                .create();
    }

    private static <T> Gson getGsonInstanceWithTypeAdapter(Class<T> clazz) {
        return GSON_BUILDER
                .setDateFormat(DateTimeUtil.DatePattern.YYYYMMDD_HHMMSS_FORMAT.getValue())
                .registerTypeAdapter(clazz, new DefineTypeAdapterByGson<T>())
                .create();
    }

    /**
     * Java Bean转换为JSON字符串
     *
     * @param bean 要转换的Java Bean实例
     * @return JSON字符串
     */
    public static <T> String beanToJsonString(T bean) {
        return DEFAULT_GSON_INSTANCE.toJson(bean);
    }

    /**
     * Java Bean转换为JSON字符串
     *
     * @param bean 要转换的Java Bean实例
     * @return JSON字符串
     */
    public static <T> String beanToJsonStringWithTypeAdapter(T bean) {
        return getGsonInstanceWithTypeAdapter(bean.getClass()).toJson(bean);
    }


    /**
     * JSON字符串转换为Java Bean对象
     *
     * @param jsonString JSON字符串
     * @param clazz      要转换成的Java Bean类
     * @param <T>        要转换成的Java Bean类型
     * @return Java Bean对象
     */
    public static <T> T jsonStringToBean(String jsonString, Class<T> clazz) {
        return DEFAULT_GSON_INSTANCE.fromJson(jsonString, clazz);
    }
}