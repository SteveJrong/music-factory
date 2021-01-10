package com.stevejrong.music.factory.common.enums;

import com.stevejrong.music.factory.common.constants.BaseConstants;

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
        /**
         * 获取此格式的编码格式
         *
         * @return 此格式的编码格式
         */
        @Override
        public String getEncodeFormat() {
            return BaseConstants.MUSIC_ENCODE_FLAC;
        }

        /**
         * 获取此格式的文件后缀名
         *
         * @return 此格式的文件后缀名
         */
        @Override
        public String getFileSuffix() {
            return BaseConstants.FILE_SUFFIX_FLAC;
        }
    },

    /**
     * M4A（AAC）音频格式
     */
    M4A_AAC_FORMAT {
        /**
         * 获取此格式的编码格式
         *
         * @return 此格式的编码格式
         */
        @Override
        public String getEncodeFormat() {
            return BaseConstants.MUSIC_ENCODE_M4A_AAC;
        }

        /**
         * 获取此格式的文件后缀名
         *
         * @return 此格式的文件后缀名
         */
        @Override
        public String getFileSuffix() {
            return BaseConstants.FILE_SUFFIX_M4A;
        }
    },

    /**
     * APE音频格式
     */
    APE_FORMAT {
        /**
         * 获取此格式的编码格式
         *
         * @return 此格式的编码格式
         */
        @Override
        public String getEncodeFormat() {
            return BaseConstants.MUSIC_ENCODE_APE;
        }

        /**
         * 获取此格式的文件后缀名
         *
         * @return 此格式的文件后缀名
         */
        @Override
        public String getFileSuffix() {
            return BaseConstants.FILE_SUFFIX_APE;
        }
    },

    /**
     * WAV音频格式
     */
    WAV_FORMAT {
        /**
         * 获取此格式的编码格式
         *
         * @return 此格式的编码格式
         */
        @Override
        public String getEncodeFormat() {
            return BaseConstants.MUSIC_ENCODE_WAV;
        }

        /**
         * 获取此格式的文件后缀名
         *
         * @return 此格式的文件后缀名
         */
        @Override
        public String getFileSuffix() {
            return BaseConstants.FILE_SUFFIX_WAV;
        }
    };

    /**
     * 根据音频文件格式的后缀名获取音频文件格式的编码格式
     *
     * @param fileSuffix 音频文件格式的后缀名
     * @return 音频文件格式的编码格式
     */
    public static String getEncodeFormatByFileSuffix(String fileSuffix) {
        for (MusicFormatEnums item : MusicFormatEnums.values()) {
            if (fileSuffix.equals(item.getFileSuffix())) {
                Class clazz = item.getClass();

                Method getEncodeFormatMethod = null;
                try {
                    getEncodeFormatMethod = clazz.getMethod("getEncodeFormat");
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }

                String encodeFormat = null;
                try {
                    encodeFormat = getEncodeFormatMethod.invoke(item).toString();
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }

                return encodeFormat;
            }
        }

        return null;
    }

    /**
     * 获取此格式的编码格式
     *
     * @return 此格式的编码格式
     */
    String getEncodeFormat() {
        return null;
    }

    /**
     * 获取此格式的文件后缀名
     *
     * @return 此格式的文件后缀名
     */
    String getFileSuffix() {
        return null;
    }
}
