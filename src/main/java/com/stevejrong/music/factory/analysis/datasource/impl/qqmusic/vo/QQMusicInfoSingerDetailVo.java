package com.stevejrong.music.factory.analysis.datasource.impl.qqmusic.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class QQMusicInfoSingerDetailVo implements Serializable {
    private static final long serialVersionUID = -5853483927704623621L;

    /**
     * QQ音乐内部标识的歌手名称
     */
    private String name;
}
