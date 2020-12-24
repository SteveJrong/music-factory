package com.stevejrong.music.factory.analysis.datasource.impl.qqmusic.vo;

import java.io.Serializable;
import java.util.List;

public class QQMusicInfoSongVo implements Serializable {
    private static final long serialVersionUID = -5075105483046176264L;

    /**
     * QQ音乐内部标识的歌曲信息集合
     */
    private List<QQMusicInfoSongDetailVo> list;

    public List<QQMusicInfoSongDetailVo> getList() {
        return list;
    }

    public void setList(List<QQMusicInfoSongDetailVo> list) {
        this.list = list;
    }

    public QQMusicInfoSongVo() {
    }

    public QQMusicInfoSongVo(List<QQMusicInfoSongDetailVo> list) {
        this.list = list;
    }
}
