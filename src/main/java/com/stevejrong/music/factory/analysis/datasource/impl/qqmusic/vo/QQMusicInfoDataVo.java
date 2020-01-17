package com.stevejrong.music.factory.analysis.datasource.impl.qqmusic.vo;

import lombok.Data;

import java.io.Serializable;

@Data
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
}
