package com.stevejrong.music.factory.common.enums;

public interface AbstractEnum {

    /**
     * 获取枚举编号
     * <p>
     * 当枚举的ordinal()值无法满足实际需要，需要自行定义枚举编号时使用
     *
     * @return 枚举的自定义编号
     */
    default Integer getCode() {
        return null;
    }

    /**
     * 获取枚举值
     * <p>
     * 定义枚举携带的值
     *
     * @return 枚举携带的值
     */
    default String getValue() {
        return null;
    }

    /**
     * 获取枚举描述
     *
     * @return 枚举的描述
     */
    default String getDesc() {
        return null;
    }
}
