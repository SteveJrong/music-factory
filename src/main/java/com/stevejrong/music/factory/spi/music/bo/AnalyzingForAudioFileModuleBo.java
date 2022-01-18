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
