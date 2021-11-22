package com.stevejrong.music.factory.spi.music.vo.analyzing.partner.datasource.qqmusic;

import java.io.Serializable;

public class QQMusicInfoDataVo implements Serializable {
    private static final long serialVersionUID = -3730619791236909919L;

    /**
     * QQ音乐内部标识的请求的搜索关键字原始数据
     */
    private String keyword;

    /**
     * QQ音乐内部标识的歌曲信息
     */
    private QQMusicInfoSongVo song;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public QQMusicInfoSongVo getSong() {
        return song;
    }

    public void setSong(QQMusicInfoSongVo song) {
        this.song = song;
    }

    public QQMusicInfoDataVo() {
    }

    public QQMusicInfoDataVo(String keyword, QQMusicInfoSongVo song) {
        this.keyword = keyword;
        this.song = song;
    }
}
