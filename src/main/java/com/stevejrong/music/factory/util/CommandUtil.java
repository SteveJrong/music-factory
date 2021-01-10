package com.stevejrong.music.factory.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Util - 命令行工具类
 */
public final class CommandUtil {

    /**
     * 执行CMD/Shell命令
     *
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
