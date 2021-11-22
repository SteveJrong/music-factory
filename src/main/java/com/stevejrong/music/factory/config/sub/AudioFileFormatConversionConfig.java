package com.stevejrong.music.factory.config.sub;

/**
 * 音频文件转换配置类
 */
public class AudioFileFormatConversionConfig {

    /**
     * 转换格式后的音频文件存放位置
     */
    private String convertedAudioFileDirectory;

    /**
     * FFmpeg组件中，ffmpeg文件的位置
     */
    private String ffmpegFileDirectory;

    /**
     * FFmpeg组件中，ffprobe文件的位置
     */
    private String ffprobeFileDirectory;

    public String getConvertedAudioFileDirectory() {
        return convertedAudioFileDirectory;
    }

    public void setConvertedAudioFileDirectory(String convertedAudioFileDirectory) {
        this.convertedAudioFileDirectory = convertedAudioFileDirectory;
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
