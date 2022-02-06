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

/**
 * 随机数工具类
 *
 * @author Steve Jrong
 * @since 1.0
 */
public final class RandomUtil {

    /**
     * 获取含数字和大小写字母的随机字符串
     *
     * @param length 随机字符串的长度
     * @return 含数字和字母的随机字符串
     */
    public static String getRandomStringWithNumericAndWord(int length) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int val = (int) (Math.random() * 62);
            if (val < 10) {
                sb.append(val);
            } else if (val < 36) {
                sb.append((char) (val + 'A' - 10));
            } else {
                sb.append((char) (val + 'a' - 36));
            }
        }

        return sb.toString();
    }

    /**
     * 获取一个指定范围内的随机数字
     *
     * @param min 生成随机数的最小下限值
     * @param max 生成随机数的最大上限值
     * @return 指定范围内的随机数字
     */
    public static int getARandomNumeric(int min, int max) {
        return Double.valueOf(min + (Math.random() * (max - min)) + "").intValue();
    }

    /**
     * 获取指定长度的数字字符串
     *
     * @param length 要生成的数字字符串长度
     * @return 数字字符串
     */
    public static String getRandomStringWithNumeric(int length) {
        int min = 1, max = 9;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(Double.valueOf(min + (Math.random() * (max - min)) + "").intValue());
        }

        return sb.toString();
    }
}