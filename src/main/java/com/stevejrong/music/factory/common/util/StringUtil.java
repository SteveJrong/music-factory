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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author Steve Jrong
 * @since 1.0
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
