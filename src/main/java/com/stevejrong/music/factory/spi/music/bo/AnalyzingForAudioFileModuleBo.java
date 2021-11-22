package com.stevejrong.music.factory.spi.music.bo;

import java.io.File;
import java.io.Serializable;

public class AnalyzingForAudioFileModuleBo implements Serializable {
    private static final long serialVersionUID = -1591136590926715426L;

    /**
     * 需要做信息补全的音频文件位置
     */
    private final String audioFilePath;

    /**
     * 需要做信息补全的音频文件文件名（不含后缀名）
     */
    private final String audioFileName;

    /**
     * 需要做信息补全的音频文件歌曲标题
     */
    private final String songTitle;

    /**
     * 需要做信息补全的音频文件的歌曲艺术家
     */
    private final String songArtist;

    public static class Builder {
        /**
         * 需要做信息补全的音频文件位置
         */
        private final String audioFilePath;

        /**
         * 需要做信息补全的音频文件文件名（不含后缀名）
         */
        private final String audioFileName;

        /**
         * 需要做信息补全的音频文件歌曲标题
         */
        private final String songTitle;

        /**
         * 需要做信息补全的音频文件的歌曲艺术家
         */
        private final String songArtist;

        public Builder(String audioFilePath, String songTitle, String songArtist) {
            this.audioFilePath = audioFilePath;

            String fileName = audioFilePath.substring(audioFilePath.lastIndexOf(File.separator) + 1, audioFilePath.lastIndexOf("."));
            this.audioFileName = fileName;

            this.songTitle = songTitle;
            this.songArtist = songArtist;
        }

        public AnalyzingForAudioFileModuleBo build() {
            return new AnalyzingForAudioFileModuleBo(this);
        }
    }

    private AnalyzingForAudioFileModuleBo(Builder builder) {
        this.audioFilePath = builder.audioFilePath;
        this.audioFileName = builder.audioFileName;
        this.songTitle = builder.songTitle;
        this.songArtist = builder.songArtist;
    }

    public String getAudioFilePath() {
        return audioFilePath;
    }

    public String getAudioFileName() {
        return audioFileName;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public String getSongArtist() {
        return songArtist;
    }
}
