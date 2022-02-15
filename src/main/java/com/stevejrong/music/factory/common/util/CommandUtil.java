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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * 命令行工具类
 *
 * @author Steve Jrong
 * @since 1.0
 */
public final class CommandUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandUtil.class);

    /**
     * 执行CMD/Shell命令
     * <p>
     * 对于格式转换功能来说，此方法因稳定性及可操作性较差，故已不再使用命令行的方式进行格式转换
     *
     * @param commands 多个命令参数
     * @return
     * @see
     */
    @Deprecated
    public static String execute(String... commands) {
        StringBuffer executeResult = new StringBuffer();

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(commands);
        // 将标准输入流和错误输入流合并，通过标准输入流读取信息
        processBuilder.redirectErrorStream(true);

        try {
            // 启动进程
            Process start = processBuilder.start();
            // 获取输入流
            InputStream inputStream = start.getInputStream();
            // 转成字符输入流
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            int len = -1;
            char[] c = new char[1024];
            // 读取输入流中的内容
            while ((len = inputStreamReader.read(c)) != -1) {
                executeResult.append(new String(c, 0, len));
            }
            inputStream.close();
        } catch (IOException e) {
            LOGGER.error(LoggerUtil.builder().append("commandUtil_execute", "执行CMD/Shell命令")
                    .append("exception", e).append("exceptionMsg", e.getMessage()).toString());
        }

        return executeResult.toString();
    }
}
