/*
 *             Copyright (C) 2022 Steve Jrong
 * 
 * 	   GitHub Homepage: https://www.github.com/SteveJrong
 *      Gitee Homepage: https://gitee.com/stevejrong1024
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stevejrong.music.factory.spi.music.vo.analyzing.partner.datasource.qqmusic;

import java.io.Serializable;
import java.util.List;

/**
 * @author Steve Jrong
 * @since 1.0
 */
public final class QQMusicInfoSongDetailVo implements Serializable {
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

    public Integer getAlbumid() {
        return albumid;
    }

    public void setAlbumid(Integer albumid) {
        this.albumid = albumid;
    }

    public String getAlbummid() {
        return albummid;
    }

    public void setAlbummid(String albummid) {
        this.albummid = albummid;
    }

    public String getAlbumname() {
        return albumname;
    }

    public void setAlbumname(String albumname) {
        this.albumname = albumname;
    }

    public Integer getBelongCD() {
        return belongCD;
    }

    public void setBelongCD(Integer belongCD) {
        this.belongCD = belongCD;
    }

    public Integer getCdIdx() {
        return cdIdx;
    }

    public void setCdIdx(Integer cdIdx) {
        this.cdIdx = cdIdx;
    }

    public List<QQMusicInfoSingerDetailVo> getSinger() {
        return singer;
    }

    public void setSinger(List<QQMusicInfoSingerDetailVo> singer) {
        this.singer = singer;
    }

    public String getSongname() {
        return songname;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public QQMusicInfoSongDetailVo() {
    }

    public QQMusicInfoSongDetailVo(Integer albumid, String albummid, String albumname, Integer belongCD, Integer cdIdx, List<QQMusicInfoSingerDetailVo> singer, String songname) {
        this.albumid = albumid;
        this.albummid = albummid;
        this.albumname = albumname;
        this.belongCD = belongCD;
        this.cdIdx = cdIdx;
        this.singer = singer;
        this.songname = songname;
    }

    @Override
    public String toString() {
        return "QQMusicInfoSongDetailVo{" +
                "albumid=" + albumid +
                ", albummid='" + albummid + '\'' +
                ", albumname='" + albumname + '\'' +
                ", belongCD=" + belongCD +
                ", cdIdx=" + cdIdx +
                ", songname='" + songname + '\'' +
                '}';
    }
}
