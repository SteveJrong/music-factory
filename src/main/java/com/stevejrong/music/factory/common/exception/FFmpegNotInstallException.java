package com.stevejrong.music.factory.common.exception;

/**
 * 未安装FFmpeg异常
 */
public class FFmpegNotInstallException extends RuntimeException {
    private static final long serialVersionUID = -709626715988187573L;

    public FFmpegNotInstallException() {
        super("未安装ffmpeg或配置不正确");
    }
}
