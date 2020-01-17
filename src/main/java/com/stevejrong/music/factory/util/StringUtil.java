package com.stevejrong.music.factory.util;

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
}
