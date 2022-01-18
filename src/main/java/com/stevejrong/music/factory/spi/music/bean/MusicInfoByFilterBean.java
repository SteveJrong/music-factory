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
package com.stevejrong.music.factory.spi.music.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;

public class MusicInfoByFilterBean extends AbstractFilterBean implements Serializable {
    private static final long serialVersionUID = -4925180047338035848L;

    /**
     * 歌曲标题
     */
    private final String songTitle;

    /**
     * 歌曲艺术家
     */
    private final String songArtist;

    /**
     * 歌曲所属的专辑名称
     */
    private final String albumName;

    /**
     * 歌曲所属的专辑封面
     */
    private final byte[] albumPicture;

    /**
     * 歌曲内嵌歌词
     */
    private String songLyrics;

    /**
     * 歌曲所属专辑的艺术家
     */
    private String albumArtist;

    /**
     * 歌曲所属专辑的发布时间
     */
    private LocalDate albumPublishDate;

    /**
     * 歌曲所属专辑的描述
     */
    private String albumDescription;

    /**
     * 歌曲所属专辑的语言类型
     */
    private String albumLanguage;

    /**
     * 歌曲所属专辑的版权信息
     */
    private String albumCopyright;

    public static class Builder extends AbstractFilterBean {
        /**
         * 歌曲标题
         */
        private final String songTitle;

        /**
         * 歌曲艺术家
         */
        private final String songArtist;

        /**
         * 歌曲所属的专辑名称
         */
        private final String albumName;

        /**
         * 歌曲所属的专辑封面
         */
        private final byte[] albumPicture;

        /**
         * 歌曲内嵌歌词
         */
        private String songLyrics;

        /**
         * 歌曲所属专辑的艺术家
         */
        private String albumArtist;

        /**
         * 歌曲所属专辑的发布时间
         */
        private LocalDate albumPublishDate;

        /**
         * 歌曲所属专辑的描述
         */
        private String albumDescription;

        /**
         * 歌曲所属专辑的语言类型
         */
        private String albumLanguage;

        /**
         * 歌曲所属专辑的版权信息
         */
        private String albumCopyright;

        public Builder(String songTitle, String songArtist, String albumName, byte[] albumPicture) {
            this.songTitle = songTitle;
            this.songArtist = songArtist;
            this.albumName = albumName;
            this.albumPicture = albumPicture;
        }

        public Builder songLyrics(String val) {
            this.songLyrics = val;
            return this;
        }

        public Builder albumArtist(String val) {
            this.albumArtist = val;
            return this;
        }

        public Builder albumPublishDate(LocalDate val) {
            this.albumPublishDate = val;
            return this;
        }

        public Builder albumDescription(String val) {
            this.albumDescription = val;
            return this;
        }

        public Builder albumLanguage(String val) {
            this.albumLanguage = val;
            return this;
        }

        public Builder albumCopyright(String val) {
            this.albumCopyright = val;
            return this;
        }

        public Builder redirectDataOnPreview(Object val) {
            this.redirectDataOnPreview = val;
            return this;
        }

        public MusicInfoByFilterBean build() {
            return new MusicInfoByFilterBean(this);
        }
    }

    private MusicInfoByFilterBean(Builder builder) {
        this.songTitle = builder.songTitle;
        this.songArtist = builder.songArtist;
        this.albumName = builder.albumName;
        this.albumPicture = builder.albumPicture;
        this.songLyrics = builder.songLyrics;
        this.albumArtist = builder.albumArtist;
        this.albumPublishDate = builder.albumPublishDate;
        this.albumDescription = builder.albumDescription;
        this.albumLanguage = builder.albumLanguage;
        this.albumCopyright = builder.albumCopyright;
        this.redirectDataOnPreview = builder.redirectDataOnPreview;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public String getAlbumName() {
        return albumName;
    }

    public byte[] getAlbumPicture() {
        return albumPicture;
    }

    public String getSongLyrics() {
        return songLyrics;
    }

    public String getAlbumArtist() {
        return albumArtist;
    }

    public LocalDate getAlbumPublishDate() {
        return albumPublishDate;
    }

    public String getAlbumDescription() {
        return albumDescription;
    }

    public String getAlbumLanguage() {
        return albumLanguage;
    }

    public String getAlbumCopyright() {
        return albumCopyright;
    }
}
