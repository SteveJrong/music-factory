package com.stevejrong.music.factory.common.enums;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * 文件类型和文件元数据解析器
 */
public enum FileMetadataQueryResolverEnums implements AbstractEnum {
    // FLAC音频文件元数据解析器
    FLAC_METADATA_QUERY_RESOLVER {
        @Override
        public String getDesc() {
            return null;
        }

        public String getQueryResolverBeanName() {
            return "flacMetadataQueryResolver";
        }

        public String getEncodingType() {
            return ".*FLAC.*";
        }
    },
    // MP3音频文件元数据解析器
    MP3_METADATA_QUERY_RESOLVER {
        @Override
        public String getDesc() {
            return null;
        }

        public String getQueryResolverBeanName() {
            return "mp3MetadataQueryResolver";
        }

        public String getEncodingType() {
            return "mp3";
        }
    };

    /**
     * 根据文件类型获取对应的元数据解析器Bean名称
     *
     * @param encodingTypeString jAudioTagger中encodingType返回的文件类型值
     */
    public static String getResolverBeanNameByEncodingType(String encodingTypeString) {
        for (FileMetadataQueryResolverEnums item : FileMetadataQueryResolverEnums.values()) {

            Class clazz = item.getClass();
            Method getResolverBeanNameMethod = null, encodingTypeMethod = null;
            try {
                getResolverBeanNameMethod = clazz.getMethod("getQueryResolverBeanName");
                encodingTypeMethod = clazz.getMethod("getEncodingType");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            String resolverBeanName = null, encodingType = null;
            try {
                resolverBeanName = getResolverBeanNameMethod.invoke(item).toString();
                encodingType = encodingTypeMethod.invoke(item).toString();
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

            if (StringUtils.isNotEmpty(encodingType) && StringUtils.isNotBlank(encodingType) && encodingType.contains(".*")) {
                boolean matchResult = Pattern.matches(encodingType, encodingTypeString);
                if (matchResult) {
                    return resolverBeanName;
                }
            } else {
                return resolverBeanName;
            }
        }

        return null;
    }
}
