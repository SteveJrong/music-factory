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
package com.stevejrong.music.factory.spi.music.bo.partner.filter2;

import java.io.Serializable;

/**
 * @author Steve Jrong
 * @since 1.0
 */
public final class KuGouMusicPartnerSongInfoFilter_2DataBo implements Serializable {
    private static final long serialVersionUID = -1838170602873317161L;

    private String album_id;

    private String lyrics;

    public String getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    @Override
    public String toString() {
        return "KuGouMusicPartnerSongInfoFilter_2DataBo{" +
                "album_id='" + album_id + '\'' +
                ", lyrics='" + lyrics + '\'' +
                '}';
    }
}