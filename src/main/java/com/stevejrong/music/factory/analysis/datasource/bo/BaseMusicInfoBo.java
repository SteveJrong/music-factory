package com.stevejrong.music.factory.analysis.datasource.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseMusicInfoBo implements Serializable {
    private static final long serialVersionUID = -4925180047338035848L;

    /**
     * 歌曲名称
     */
    private String songName;

    /**
     * 歌曲演唱者
     */
    private String singerName;

    /**
     * 歌曲所属的专辑名称
     */
    private String albumName;

    /**
     * 搜索歌曲的专辑图片的第三方凭据
     */
    private String partnerCredentialBySearchAlbum;

    /**
     * 歌曲所属专辑的封面图片
     */
    private byte[] albumPic;
}
