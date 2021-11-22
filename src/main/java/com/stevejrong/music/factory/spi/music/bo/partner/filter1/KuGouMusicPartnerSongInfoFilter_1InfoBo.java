/**
 * Copyright 2021 Steve Jrong
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stevejrong.music.factory.spi.music.bo.partner.filter1;

import java.io.Serializable;

/**
 * @author Steve Jrong
 * create date: 2021-11-17 4:18 PM
 * @since 1.0
 */
public class KuGouMusicPartnerSongInfoFilter_1InfoBo implements Serializable {
    private static final long serialVersionUID = 3925372464535566043L;

    /**
     * 歌曲所属的专辑ID
     */
    private String album_id;

    /**
     * 歌曲标题
     */
    private String songname;

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

    public String getSongname() {
        return songname;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}