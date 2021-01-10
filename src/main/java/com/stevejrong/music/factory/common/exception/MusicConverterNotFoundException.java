package com.stevejrong.music.factory.common.exception;

/**
 * 未找到音频转换器异常
 */
public class MusicConverterNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -5715006554824530572L;

    public MusicConverterNotFoundException() {
        super("未找到音频转换器或原始音频格式不受支持");
    }
}
