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
package com.stevejrong.music.factory.spi.music.vo.partner.filter1;

import java.io.Serializable;

/**
 * @author Steve Jrong
 * create date: 2021-11-17 4:18 PM
 * @since 1.0
 */
public class KuGouMusicPartnerSongInfoFilter_1InfoVo implements Serializable {
    private static final long serialVersionUID = 3925372464535566043L;

    /**
     * 歌曲所属的专辑ID
     */
    private String album_id;

    /**
     * 歌曲所属的专辑ID（备用）
     */
    private String album_audio_id;

    /**
     * 歌曲标题
     */
    private String songname;

    /**
     * 歌曲标题（备用）
     */
    private String singername;

    /**
     * 歌曲哈希码
     */
    private String hash;

    public String getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
    }

    public String getAlbum_audio_id() {
        return album_audio_id;
    }

    public void setAlbum_audio_id(String album_audio_id) {
        this.album_audio_id = album_audio_id;
    }

    public String getSongname() {
        return songname;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public String getSingername() {
        return singername;
    }

    public void setSingername(String singername) {
        this.singername = singername;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}