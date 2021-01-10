package com.stevejrong.music.factory.common.enums;

public interface AbstractEnum {

    /**
     * 获取枚举描述
     *
     * @return 枚举的描述
     */
    default String getDesc() {
        return null;
    }

    /**
     * 获取枚举值
     * 当枚举的ordinal()值无法满足实际需要，需要自行定义枚举的Code时使用
     *
     * @return 枚举的自定义Code值
     */
    default Integer getCode() {
        return null;
    }
}
