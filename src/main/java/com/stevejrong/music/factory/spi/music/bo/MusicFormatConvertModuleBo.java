package com.stevejrong.music.factory.spi.music.bo;

import java.io.Serializable;

public class MusicFormatConvertModuleBo implements Serializable {
    private static final long serialVersionUID = -7561630163510737142L;

    /**
     * 需要机型格式转换的音频文件的绝对路径
     */
    private String fileAbsolutePath;

    /**
     * 需要机型格式转换的音频文件的歌曲名称
     */
    private String songName;

    /**
     * 需要机型格式转换的音频文件的演唱者
     */
    private String singerName;

    /**
     * 需要机型格式转换的音频文件的音频格式
     */
    private String encodingType;

    /**
     * 需要机型格式转换的音频文件的音频比特率（Kbps）
     */
    private String bitRate;

    /**
     * 需要机型格式转换的音频文件的音频编码器
     */
    private boolean encoder;

    public String getFileAbsolutePath() {
        return fileAbsolutePath;
    }

    public void setFileAbsolutePath(String fileAbsolutePath) {
        this.fileAbsolutePath = fileAbsolutePath;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public String getEncodingType() {
        return encodingType;
    }

    public void setEncodingType(String encodingType) {
        this.encodingType = encodingType;
    }

    public String getBitRate() {
        return bitRate;
    }

    public void setBitRate(String bitRate) {
        this.bitRate = bitRate;
    }

    public boolean isEncoder() {
        return encoder;
    }

    public void setEncoder(boolean encoder) {
        this.encoder = encoder;
    }

    public MusicFormatConvertModuleBo() {
    }

    public MusicFormatConvertModuleBo(String fileAbsolutePath, String songName, String singerName, String encodingType, String bitRate, boolean encoder) {
        this.fileAbsolutePath = fileAbsolutePath;
        this.songName = songName;
        this.singerName = singerName;
        this.encodingType = encodingType;
        this.bitRate = bitRate;
        this.encoder = encoder;
    }
}
