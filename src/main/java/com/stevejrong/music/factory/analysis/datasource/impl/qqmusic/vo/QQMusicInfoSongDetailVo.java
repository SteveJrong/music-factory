package com.stevejrong.music.factory.analysis.datasource.impl.qqmusic.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QQMusicInfoSongDetailVo implements Serializable {
    private static final long serialVersionUID = 8480558002112197668L;

    /**
     * QQ音乐内部标识的专辑ID
     */
    private Integer albumid;

    /**
     * QQ音乐内部标识的专辑ID
     * 此ID可用于获取歌曲的专辑图片信息
     */
    private String albummid;

    /**
     * QQ音乐内部标识的专辑名称
     */
    private String albumname;

    /**
     * QQ音乐内部标识的此歌曲所属的碟片编号
     */
    private Integer belongCD;

    /**
     * QQ音乐内部标识的此歌曲所在的碟片索引
     */
    private Integer cdIdx;

    /**
     * QQ音乐内部标识的此歌曲的歌手信息集合
     */
    private List<QQMusicInfoSingerDetailVo> singer;

    /**
     * QQ音乐内部标识的歌曲名称
     */
    private String songname;
}
