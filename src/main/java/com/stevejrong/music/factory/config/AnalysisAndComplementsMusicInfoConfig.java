package com.stevejrong.music.factory.config;

/**
 * 分析和补全音乐信息配置类
 */
public class AnalysisAndComplementsMusicInfoConfig {

    /**
     * 原始音频文件存放目录
     */
    private String musicFileDirectoryOfOriginal;

    public String getMusicFileDirectoryOfOriginal() {
        return musicFileDirectoryOfOriginal;
    }

    public void setMusicFileDirectoryOfOriginal(String musicFileDirectoryOfOriginal) {
        this.musicFileDirectoryOfOriginal = musicFileDirectoryOfOriginal;
    }
}
