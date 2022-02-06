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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 命令行工具类
 *
 * @author Steve Jrong
 * @since 1.0
 */
@Deprecated
public final class CommandUtil {

    /**
     * 执行CMD/Shell命令
     * <p>
     * 对于格式转换功能来说，此方法因稳定性及可操作性较差，故已不再使用命令行的方式进行格式转换
     *
     * @param commands 多个命令参数
     * @return
     */
    public static String execute(String... commands) {
        StringBuffer executeResult = new StringBuffer();

        try {
            Process process = Runtime.getRuntime().exec(commands);

            InputStream in = process.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = br.readLine()) != null) {
                executeResult.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return executeResult.toString();
    }
}
