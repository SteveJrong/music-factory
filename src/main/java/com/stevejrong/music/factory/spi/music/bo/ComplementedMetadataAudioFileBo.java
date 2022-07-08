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

import java.io.Serializable;

/**
 * 音频文件元数据信息补全Bo
 *
 * @author Steve Jrong
 * @since 1.0
 */
public class ComplementedMetadataAudioFileBo implements Serializable {
    private static final long serialVersionUID = -423525799905277471L;

    /**
     * 已完成信息补全的音频文件的位置（绝对路径）
     */
    private String fileAbsolutePath;

    /**
     * 已完成信息补全的音频文件的歌曲名称
     */
    private String songTitle;

    /**
     * 已完成信息补全的音频文件的歌曲艺术家
     */
    private String songArtist;

    /**
     * 类型。
     * <p>
     * 0 - 在补全信息期间发生异常的音频文件信息Bo；1 - 已完成信息补全的音频文件信息Bo
     */
    private int type;

    public String getFileAbsolutePath() {
        return fileAbsolutePath;
    }

    public void setFileAbsolutePath(String fileAbsolutePath) {
        this.fileAbsolutePath = fileAbsolutePath;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }

    public ComplementedMetadataAudioFileBo() {
    }

    public ComplementedMetadataAudioFileBo(String fileAbsolutePath, String songTitle, String songArtist, int type) {
        this.fileAbsolutePath = fileAbsolutePath;
        this.songTitle = songTitle;
        this.songArtist = songArtist;
        this.type = type;
    }

    @Override
    public String toString() {
        return "ComplementedMetadataAudioFileBo{" +
                "fileAbsolutePath='" + fileAbsolutePath + '\'' +
                ", songTitle='" + songTitle + '\'' +
                ", songArtist='" + songArtist + '\'' +
                ", type=" + type +
                '}';
    }
}
