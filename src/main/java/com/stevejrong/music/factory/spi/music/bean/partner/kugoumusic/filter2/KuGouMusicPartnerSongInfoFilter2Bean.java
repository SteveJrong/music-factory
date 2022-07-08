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
package com.stevejrong.music.factory.spi.music.bean.partner.kugoumusic.filter2;

import java.io.Serializable;

/**
 * @author Steve Jrong
 * @since 1.0
 */
public final class KuGouMusicPartnerSongInfoFilter2Bean implements Serializable {
    private static final long serialVersionUID = 4570695505611684068L;

    private String r;

    private String hash;

    private int mid;

    private String album_id;

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
    }

    public KuGouMusicPartnerSongInfoFilter2Bean(String r, String hash, int mid, String album_id) {
        this.r = r;
        this.hash = hash;
        this.mid = mid;
        this.album_id = album_id;
    }

    @Override
    public String toString() {
        return "KuGouMusicPartnerSongInfoFilter2Bean{" +
                "r='" + r + '\'' +
                ", hash='" + hash + '\'' +
                ", mid=" + mid +
                ", album_id='" + album_id + '\'' +
                '}';
    }
}