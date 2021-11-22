package com.stevejrong.music.factory.spi.music.bo;

import java.io.Serializable;

public class ComplementedMetadataAudioFileBo implements Serializable {
    private static final long serialVersionUID = -423525799905277471L;

    /**
     * 已完成信息补全的音频文件的位置（绝对路径）
     */
    private String fileAbsolutePath;

    /**
     * 已完成信息补全的音频文件的歌曲名称
     */
    private String songTitle;

    /**
     * 已完成信息补全的音频文件的歌曲艺术家
     */
    private String songArtist;

    /**
     * 类型。
     * <p>
     * 0 - 在补全信息期间发生异常的音频文件信息Bo；1 - 已完成信息补全的音频文件信息Bo
     */
    private int type;

    public String getFileAbsolutePath() {
        return fileAbsolutePath;
    }

    public void setFileAbsolutePath(String fileAbsolutePath) {
        this.fileAbsolutePath = fileAbsolutePath;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }

    public ComplementedMetadataAudioFileBo() {
    }

    public ComplementedMetadataAudioFileBo(String fileAbsolutePath, String songTitle, String songArtist, int type) {
        this.fileAbsolutePath = fileAbsolutePath;
        this.songTitle = songTitle;
        this.songArtist = songArtist;
        this.type = type;
    }
}
