package com.stevejrong.music.factory.common.exception;

/**
 * 未安装ffmpeg异常类
 */
public class NotInstallFfmpegException extends RuntimeException {
    private static final long serialVersionUID = -709626715988187573L;

    public NotInstallFfmpegException() {
        super("未安装ffmpeg或配置不正确");
    }
}
