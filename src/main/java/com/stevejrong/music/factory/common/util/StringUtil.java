package com.stevejrong.music.factory.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Pattern;

/**
 * Util - 字符串操作工具类
 */
public final class StringUtil {
    public static final Pattern SPECIAL_CHARS_PATTERN = Pattern.compile(
            "[\\n`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]");

    /**
     * URL编码
     *
     * @param words
     * @return
     */
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

    /**
     * 移除特殊字符
     *
     * @param sourceString 原字符串
     * @return 移除特殊字符后的字符串
     */
    public static String removeSpecialChars(String sourceString) {
        return SPECIAL_CHARS_PATTERN.matcher(sourceString).replaceAll(" ");
    }
}
