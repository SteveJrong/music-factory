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

import com.stevejrong.music.factory.common.enums.BaseEnums;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * HTTP工具类
 *
 * @author Steve Jrong
 * @since 1.0
 */
public final class HttpUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);

    private static final ThreadLocal<OkHttpClient> httpClientThreadLocal = new ThreadLocal<OkHttpClient>();

    /**
     * 获取线程安全的OkHttpClient实例
     *
     * @return 线程安全的OkHttpClient实例
     */
    private static OkHttpClient getOkHttpClientInstance() {
        OkHttpClient httpClient = httpClientThreadLocal.get();
        if (null == httpClient) {
            httpClient = new OkHttpClient();
            httpClientThreadLocal.set(httpClient);
        }
        return httpClient;
    }

    /**
     * 发起GET请求（内部方法）
     *
     * @param url           请求地址
     * @param headerParams  请求头参数，Map类型
     * @param requestParams 请求体参数，Map类型
     * @return ResponseBody响应体
     */
    private static ResponseBody getSync(String url, Map<String, String> headerParams, Map<String, String> requestParams) {
        ResponseBody result = null;
        OkHttpClient httpClient = getOkHttpClientInstance();

        Request.Builder requestBuilder = new Request.Builder();

        if (null != headerParams && !headerParams.isEmpty()) {
            // 添加请求头参数
            headerParams.forEach(requestBuilder::addHeader);
        }

        // URL参数拼接
        StringBuffer urlParamsStr = new StringBuffer("");
        if (null != requestParams) {
            urlParamsStr.append("?");

            for (Map.Entry item : requestParams.entrySet()) {
                urlParamsStr.append(item.getKey()).append("=").append(item.getValue()).append("&");
            }
        }

        if (StringUtils.isNotBlank(urlParamsStr)) {
            url = url + urlParamsStr.substring(0, urlParamsStr.length() - 1);
        }

        Request request = requestBuilder.url(url).build();

        Call call = httpClient.newCall(request);

        try {
            Response response = call.execute();
            result = response.body();
        } catch (IOException e) {
            LOGGER.error(LoggerUtil.builder().append("httpUtil_getSync", "发起GET请求（内部方法）")
                    .append("exception", e).append("exceptionMsg", e.getMessage()).toString());
        }

        return result;
    }

    /**
     * 发起同步POST请求
     *
     * @param url                     请求地址
     * @param httpRequestBodyDataType HTTP请求的请求体数据类型
     * @param headerParams            请求头参数，Map类型
     * @param bodyParamsString        请求体参数，字符串类型
     * @return ResponseBody响应体
     */
    private static ResponseBody postSync(String url, BaseEnums.HttpRequestBodyDataType httpRequestBodyDataType, Map<String, String> headerParams, String bodyParamsString) {
        ResponseBody result = null;
        OkHttpClient httpClient = getOkHttpClientInstance();

        // 添加请求头参数
        Request.Builder requestBuilder = new Request.Builder();
        if (null != headerParams && !headerParams.isEmpty()) {
            headerParams.forEach(requestBuilder::addHeader);
        }

        // 创建RequestBody对象并使用JSON格式进行封装
        RequestBody requestBody = null;
        if (httpRequestBodyDataType == BaseEnums.HttpRequestBodyDataType.APPLICATION_JSON) {
            requestBody = RequestBody.create(MediaType.parse(BaseEnums.HttpRequestBodyDataType.APPLICATION_JSON.getDesc()), bodyParamsString);
        } else if (httpRequestBodyDataType == BaseEnums.HttpRequestBodyDataType.APPLICATION_XML) {
            requestBody = RequestBody.create(MediaType.parse(BaseEnums.HttpRequestBodyDataType.APPLICATION_XML.getDesc()), bodyParamsString);
        }

        Request request = new Request.Builder().post(requestBody).url(url).build();

        Call call = httpClient.newCall(request);

        try {
            Response response = call.execute();
            result = response.body();
        } catch (IOException e) {
            LOGGER.error(LoggerUtil.builder().append("httpUtil_postSync", "发起同步POST请求")
                    .append("exception", e).append("exceptionMsg", e.getMessage()).toString());
        }

        return result;
    }

    /**
     * 发起无请求头且带参的GET请求
     *
     * @param url           请求地址
     * @param requestParams 请求体参数，Map类型
     * @return 字符串格式的响应结果
     */
    public static String get(String url, Map<String, String> requestParams) {
        String result = null;
        try {
            result = getSync(url, null, requestParams).string();
        } catch (IOException e) {
            LOGGER.error(LoggerUtil.builder().append("httpUtil_get", "发起无请求头且带参的GET请求")
                    .append("exception", e).append("exceptionMsg", e.getMessage()).toString());
        }

        return result;
    }

    /**
     * 发起无请求头、带参且用于获取URL图片为字节数组的GET请求
     *
     * @param url           URL图片的请求地址
     * @param requestParams 请求体参数，Map类型
     * @return 图片字节数组
     */
    public static byte[] getImage(String url, Map<String, String> requestParams) {
        byte[] result = null;
        try {
            result = getSync(url, null, requestParams).bytes();
        } catch (IOException e) {
            LOGGER.error(LoggerUtil.builder().append("httpUtil_getImage", "发起无请求头、带参且用于获取URL图片为字节数组的GET请求")
                    .append("exception", e).append("exceptionMsg", e.getMessage()).toString());
        }

        return result;
    }

    /**
     * 发起无请求头且带参的GET请求
     * 用于获取二进制数据
     *
     * @param url           请求地址
     * @param requestParams 请求体参数，Map类型
     * @return 字符串格式的响应结果
     */
    public static byte[] getOfBytes(String url, Map<String, String> requestParams) {
        byte[] result = null;
        try {
            result = getSync(url, null, requestParams).bytes();
        } catch (IOException e) {
            LOGGER.error(LoggerUtil.builder().append("httpUtil_getOfBytes", "发起无请求头且带参的GET请求")
                    .append("exception", e).append("exceptionMsg", e.getMessage()).toString());
        }

        return result;
    }

    /**
     * 发起有请求头的且带参的GET请求
     *
     * @param url           请求地址
     * @param headerParams  请求头参数，Map类型
     * @param requestParams 请求体参数，Map类型
     * @return 字符串格式的响应结果
     */
    public static Object get(String url, Map<String, String> headerParams, Map<String, String> requestParams) {
        Object result = null;
        try {
            result = getSync(url, headerParams, requestParams).string();
        } catch (IOException e) {
            LOGGER.error(LoggerUtil.builder().append("httpUtil_get", "发起有请求头的且带参的GET请求")
                    .append("exception", e).append("exceptionMsg", e.getMessage()).toString());
        }

        return result;
    }

    /**
     * 发起无请求头且带参的POST请求
     *
     * @param url                     请求地址
     * @param httpRequestBodyDataType HTTP请求的请求体数据类型
     * @param bodyParamsString        请求体参数，字符串类型
     * @return 字符串格式的响应结果
     */
    public static String post(String url, BaseEnums.HttpRequestBodyDataType httpRequestBodyDataType, String bodyParamsString) {
        String result = null;
        try {
            result = postSync(url, httpRequestBodyDataType, null, bodyParamsString).string();
        } catch (IOException e) {
            LOGGER.error(LoggerUtil.builder().append("httpUtil_post", "发起无请求头且带参的POST请求")
                    .append("exception", e).append("exceptionMsg", e.getMessage()).toString());
        }

        return result;
    }

    /**
     * 发起有请求头且带参的POST请求
     *
     * @param url                     请求地址
     * @param httpRequestBodyDataType HTTP请求的请求体数据类型
     * @param headerParams            请求头参数，Map类型
     * @param bodyParamsString        请求体参数，字符串类型
     * @return 字符串格式的响应结果
     */
    public static String post(String url, BaseEnums.HttpRequestBodyDataType httpRequestBodyDataType, Map<String, String> headerParams, String bodyParamsString) {
        String result = null;
        try {
            result = postSync(url, httpRequestBodyDataType, headerParams, bodyParamsString).string();
        } catch (IOException e) {
            LOGGER.error(LoggerUtil.builder().append("httpUtil_post", "发起有请求头且带参的POST请求")
                    .append("exception", e).append("exceptionMsg", e.getMessage()).toString());
        }

        return result;
    }
}
