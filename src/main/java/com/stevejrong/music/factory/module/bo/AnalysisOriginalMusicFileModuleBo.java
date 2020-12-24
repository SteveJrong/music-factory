package com.stevejrong.music.factory.module.bo;

import java.io.Serializable;

public class AnalysisOriginalMusicFileModuleBo implements Serializable {
    private static final long serialVersionUID = -1591136590926715426L;

    /**
     * 需要做信息补全的音频文件的绝对路径
     */
    private String fileAbsolutePath;

    /**
     * 需要做信息补全的音频文件的文件名，不含后缀名
     */
    private String fileName;

    /**
     * 需要做信息补全的音频文件的歌曲名称
     */
    private String songName;

    /**
     * 需要做信息补全的音频文件的演唱者
     */
    private String singerName;

    public String getFileAbsolutePath() {
        return fileAbsolutePath;
    }

    public void setFileAbsolutePath(String fileAbsolutePath) {
        this.fileAbsolutePath = fileAbsolutePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public AnalysisOriginalMusicFileModuleBo() {
    }

    public AnalysisOriginalMusicFileModuleBo(String fileAbsolutePath, String fileName, String songName, String singerName) {
        this.fileAbsolutePath = fileAbsolutePath;
        this.fileName = fileName;
        this.songName = songName;
        this.singerName = singerName;
    }
}
