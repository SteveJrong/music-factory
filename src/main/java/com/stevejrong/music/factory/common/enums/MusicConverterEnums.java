package com.stevejrong.music.factory.common.enums;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 音频转换器枚举
 * 目前仅支持 格式N -> FLAC 格式的转换
 *
 * 此类已弃用
 */
@Deprecated
public enum MusicConverterEnums implements AbstractEnum {

    /**
     * M4A（AAC）音频格式转换为FLAC音频格式
     */
    CONVERT_M4A_AAC_TO_FLAC {
        @Override
        public String getDesc() {
            return null;
        }

        public MusicFormatEnums getSourceFormatInfo() {
            return MusicFormatEnums.M4A_AAC_FORMAT;
        }

        public MusicFormatEnums getTargetFormatInfo() {
            return MusicFormatEnums.FLAC_FORMAT;
        }

        public String getMusicConverterBeanName() {
            return "m4aAacToFlacConverter";
        }
    },

    /**
     * APE音频格式转换为FLAC音频格式
     */
    CONVERT_APE_TO_FLAC {
        @Override
        public String getDesc() {
            return null;
        }

        public MusicFormatEnums getSourceFormatInfo() {
            return MusicFormatEnums.APE_FORMAT;
        }

        public MusicFormatEnums getTargetFormatInfo() {
            return MusicFormatEnums.FLAC_FORMAT;
        }

        public String getMusicConverterBeanName() {
            return "apeToFlacConverter";
        }
    },

    /**
     * WAV音频格式转换为FLAC音频格式
     */
    CONVERT_WAV_TO_FLAC {
        @Override
        public String getDesc() {
            return null;
        }

        public MusicFormatEnums getSourceFormatInfo() {
            return MusicFormatEnums.WAV_FORMAT;
        }

        public MusicFormatEnums getTargetFormatInfo() {
            return MusicFormatEnums.FLAC_FORMAT;
        }

        public String getMusicConverterBeanName() {
            return "wavToFlacConverter";
        }
    };


    /**
     * 根据待转换的音频格式和转换为的音频格式获取音频格式转换器的Bean名称
     *
     * @param sourceFormat
     * @param targetFormat
     * @return
     */
    public static String getMusicConverterBeanNameBySourceFormatAndTargetFormat(String sourceFormat, String targetFormat) {
        for (MusicConverterEnums item : MusicConverterEnums.values()) {
            Class clazz = item.getClass();
            Method getSourceFormatInfoMethod = null, getTargetFormatInfoMethod = null, getMusicConverterBeanNameMethod = null;
            try {
                getSourceFormatInfoMethod = clazz.getMethod("getSourceFormatInfo");
                getTargetFormatInfoMethod = clazz.getMethod("getTargetFormatInfo");
                getMusicConverterBeanNameMethod = clazz.getMethod("getMusicConverterBeanName");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            MusicFormatEnums sourceFormatInfo = null, targetFormatInfo = null;
            String converterBeanName = null;
            try {
                sourceFormatInfo = (MusicFormatEnums) getSourceFormatInfoMethod.invoke(item);
                targetFormatInfo = (MusicFormatEnums) getTargetFormatInfoMethod.invoke(item);
                converterBeanName = getMusicConverterBeanNameMethod.invoke(item).toString();
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

            if (sourceFormat.equals(sourceFormatInfo.getDesc()) && targetFormat.equals(targetFormatInfo.getDesc())) {
                return converterBeanName;
            }
        }

        return null;
    }
}
