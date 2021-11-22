package com.stevejrong.music.factory.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Util - 字符串操作工具类
 */
public final class StringUtil {

    public static String urlEncode(String words) {
        String encodeResult = null;

        try {
            encodeResult = URLEncoder.encode(words, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return encodeResult;
    }

    public static String replaceSpecialCharOfDirectoryByWindows(String sourceString) {
        return sourceString.replaceAll("[/\\\\:*?|]", "-")
                .replaceAll("[\"<>]", "-");
    }
}
