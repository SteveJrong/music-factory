package com.stevejrong.music.factory.config;

/**
 * 音频文件转换配置类
 */
public class ConvertMusicFileConfig {

    /**
     * 转换后的音频文件存放目录
     */
    private String musicFileDirectoryOfConverted;

    /**
     * FFmpeg组件的ffmpeg文件路径
     */
    private String ffmpegFileDirectory;

    /**
     * FFmpeg组件的ffprobe文件路径
     */
    private String ffprobeFileDirectory;

    public String getMusicFileDirectoryOfConverted() {
        return musicFileDirectoryOfConverted;
    }

    public void setMusicFileDirectoryOfConverted(String musicFileDirectoryOfConverted) {
        this.musicFileDirectoryOfConverted = musicFileDirectoryOfConverted;
    }

    public String getFfmpegFileDirectory() {
        return ffmpegFileDirectory;
    }

    public void setFfmpegFileDirectory(String ffmpegFileDirectory) {
        this.ffmpegFileDirectory = ffmpegFileDirectory;
    }

    public String getFfprobeFileDirectory() {
        return ffprobeFileDirectory;
    }

    public void setFfprobeFileDirectory(String ffprobeFileDirectory) {
        this.ffprobeFileDirectory = ffprobeFileDirectory;
    }
}
