package com.stevejrong.music.factory.spi.music.bo.analyzing.datasource;

import java.io.Serializable;
import java.time.LocalDate;

public class PartnerSongInfoBo implements Serializable {
    private static final long serialVersionUID = 2000901512499577020L;

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

    public static class Builder {
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

        public PartnerSongInfoBo build() {
            return new PartnerSongInfoBo(this);
        }
    }

    private PartnerSongInfoBo(Builder builder) {
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
