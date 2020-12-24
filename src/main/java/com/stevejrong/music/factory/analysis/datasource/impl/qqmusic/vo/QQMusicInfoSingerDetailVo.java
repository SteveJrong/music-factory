package com.stevejrong.music.factory.analysis.datasource.impl.qqmusic.vo;

import java.io.Serializable;

public class QQMusicInfoSingerDetailVo implements Serializable {
    private static final long serialVersionUID = -5853483927704623621L;

    /**
     * QQ音乐内部标识的歌手名称
     */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public QQMusicInfoSingerDetailVo() {
    }

    public QQMusicInfoSingerDetailVo(String name) {
        this.name = name;
    }
}
