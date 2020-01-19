package com.stevejrong.music.factory.common.enums;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 音频格式枚举
 */
public enum MusicFormatEnums implements AbstractEnum {

    /**
     * FLAC音频格式
     */
    FLAC_FORMAT {
        @Override
        public String getDesc() {
            return "flac";
        }

        public String getFileSuffix() {
            return ".flac";
        }
    },

    /**
     * Apple Lossless音频格式
     */
    M4A_ALAC_FORMAT {
        @Override
        public String getDesc() {
            return "m4a_alac";
        }

        public String getFileSuffix() {
            return ".m4a";
        }
    };

    /**
     * 根据音频格式描述获取音频格式文件的后缀名
     *
     * @param desc
     * @return
     */
    public static String getFileSuffixByDesc(String desc) {
        for (MusicFormatEnums item : MusicFormatEnums.values()) {
            if (desc.equals(item.getDesc())) {
                Class clazz = item.getClass();

                Method getFileSuffixMethod = null;
                try {
                    getFileSuffixMethod = clazz.getMethod("getFileSuffix");
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }

                String fileSuffix = null;
                try {
                    fileSuffix = getFileSuffixMethod.invoke(item).toString();
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }

                return fileSuffix;
            }
        }

        return null;
    }
}
