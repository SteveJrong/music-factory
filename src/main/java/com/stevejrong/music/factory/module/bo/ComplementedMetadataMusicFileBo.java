package com.stevejrong.music.factory.module.bo;

import java.io.Serializable;

public class ComplementedMetadataMusicFileBo implements Serializable {
    private static final long serialVersionUID = -423525799905277471L;

    /**
     * 已完成信息补全的音频文件的绝对路径
     */
    private String fileAbsolutePath;

    /**
     * 已完成信息补全的音频文件的歌曲名称
     */
    private String songName;

    /**
     * 已完成信息补全的音频文件的演唱者
     */
    private String singerName;

    /**
     * 类型。0-在补全信息期间发生异常的音频文件信息Bo；1-已完成信息补全的音频文件信息Bo
     */
    private Integer type;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public ComplementedMetadataMusicFileBo() {
    }

    public ComplementedMetadataMusicFileBo(String fileAbsolutePath, String songName, String singerName, Integer type) {
        this.fileAbsolutePath = fileAbsolutePath;
        this.songName = songName;
        this.singerName = singerName;
        this.type = type;
    }
}
