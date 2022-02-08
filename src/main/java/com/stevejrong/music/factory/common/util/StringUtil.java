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

import com.google.common.base.Splitter;
import com.stevejrong.music.factory.common.constants.BaseConstants;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
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

    /**
     * 整型值转换为16进制字符串
     *
     * @param sourceNum 源整型值
     * @param length    每个字节占用的位数
     * @return 16进制字符串
     */
    public static String numberToHexString(long sourceNum, int length) {
        //一个字节占两位，所以要乘以2
        length <<= 1;
        StringBuilder res = new StringBuilder(Long.toHexString(sourceNum));
        int comp = length - res.length();

        // 当位数不足时，需要补0
        if (comp > 0) {
            for (int i = 0; i < comp; i++) {
                res.insert(0, "0");
            }
        }

        return res.toString();
    }

    /**
     * 字符串转换为16进制字符串
     *
     * @param sourceString 源字符串
     * @return 16进制字符串
     */
    public static String stringToHexString(String sourceString) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < sourceString.length(); i++) {
            int ch = sourceString.charAt(i);
            sb.append(Integer.toHexString(ch));
        }

        return sb.toString();
    }

    /**
     * 字节数组转换为16进制字符串
     *
     * @param sourceByteArray 源字节数组
     * @return 16进制字符串
     */
    public static String byteArrayToHexString(byte[] sourceByteArray) {
        StringBuffer sb = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < sourceByteArray.length; n++) {
            stmp = (Integer.toHexString(sourceByteArray[n] & 0XFF));
            if (stmp.length() == 1) {
                sb.append("0").append(stmp);
            } else {
                sb.append(stmp);
            }
        }
        return sb.toString();
    }

    /**
     * 按指定长度间隔添加空格符到字符串中
     *
     * @param sourceString 源字符串
     * @param length       添加空格符的字符间隔长度
     * @return 按指定长度间隔添加了空格符的字符串
     */
    public static String addSpaceIntoStringByLength(String sourceString, int length) {
        Iterator<String> iterator = Splitter.fixedLength(length).split(sourceString).iterator();

        int i = 0;
        StringBuffer sb = new StringBuffer();
        while (iterator.hasNext()) {
            if (i != 0 && i % length == 0) {
                sb.append(BaseConstants.SPACE_CHAR);
            }

            sb.append(iterator.next());
            i++;
        }

        return sb.toString();
    }

    /**
     * 字符串内容写入文本文件
     *
     * @param content    要写入的字符串内容
     * @param fileSuffix 保存的文件后缀名
     * @param fileName   保存的文件名称
     * @param filePath   保存的文件路径
     */
    public static void writeStringToTxt(String content, String fileSuffix, String fileName, String filePath) {
        File file = new File(filePath.concat(File.separator).concat(fileName).concat(fileSuffix));

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            Files.write(Paths.get(filePath.concat(File.separator).concat(fileName).concat(fileSuffix)), content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
