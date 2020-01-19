package com.stevejrong.music.factory.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Util - 命令行工具类
 */
public final class CommandUtil {

    public static String execute(String command) {
        StringBuffer executeResult = new StringBuffer();

        try {
            Process process = Runtime.getRuntime().exec(command);

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
