package com.stevejrong.music.factory.analysis.datasource.bo;

import java.io.Serializable;

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

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getPartnerCredentialBySearchAlbum() {
        return partnerCredentialBySearchAlbum;
    }

    public void setPartnerCredentialBySearchAlbum(String partnerCredentialBySearchAlbum) {
        this.partnerCredentialBySearchAlbum = partnerCredentialBySearchAlbum;
    }

    public byte[] getAlbumPic() {
        return albumPic;
    }

    public void setAlbumPic(byte[] albumPic) {
        this.albumPic = albumPic;
    }

    public BaseMusicInfoBo() {
    }

    public BaseMusicInfoBo(String songName, String singerName, String albumName, String partnerCredentialBySearchAlbum, byte[] albumPic) {
        this.songName = songName;
        this.singerName = singerName;
        this.albumName = albumName;
        this.partnerCredentialBySearchAlbum = partnerCredentialBySearchAlbum;
        this.albumPic = albumPic;
    }
}
