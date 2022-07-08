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
 * 音频文件格式转换Bo
 *
 * @author Steve Jrong
 * @since 1.0
 */
public final class AudioFileFormatConversionModuleBo implements Serializable {
    private static final long serialVersionUID = -7561630163510737142L;

    /**
     * 需要机型格式转换的音频文件的绝对路径
     */
    private String fileAbsolutePath;

    /**
     * 需要机型格式转换的音频文件的歌曲名称
     */
    private String songName;

    /**
     * 需要机型格式转换的音频文件的演唱者
     */
    private String singerName;

    /**
     * 需要机型格式转换的音频文件的音频格式
     */
    private String encodingType;

    /**
     * 需要机型格式转换的音频文件的音频比特率（Kbps）
     */
    private String bitRate;

    /**
     * 需要机型格式转换的音频文件的音频编码器
     */
    private boolean encoder;

    public String getFileAbsolutePath() {
        return fileAbsolutePath;
    }

    public void setFileAbsolutePath(String fileAbsolutePath) {
        this.fileAbsolutePath = fileAbsolutePath;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public String getEncodingType() {
        return encodingType;
    }

    public void setEncodingType(String encodingType) {
        this.encodingType = encodingType;
    }

    public String getBitRate() {
        return bitRate;
    }

    public void setBitRate(String bitRate) {
        this.bitRate = bitRate;
    }

    public boolean isEncoder() {
        return encoder;
    }

    public void setEncoder(boolean encoder) {
        this.encoder = encoder;
    }

    public AudioFileFormatConversionModuleBo() {
    }

    public AudioFileFormatConversionModuleBo(String fileAbsolutePath, String songName, String singerName, String encodingType, String bitRate, boolean encoder) {
        this.fileAbsolutePath = fileAbsolutePath;
        this.songName = songName;
        this.singerName = singerName;
        this.encodingType = encodingType;
        this.bitRate = bitRate;
        this.encoder = encoder;
    }

    @Override
    public String toString() {
        return "AudioFileFormatConversionModuleBo{" +
                "fileAbsolutePath='" + fileAbsolutePath + '\'' +
                ", songName='" + songName + '\'' +
                ", singerName='" + singerName + '\'' +
                ", encodingType='" + encodingType + '\'' +
                ", bitRate='" + bitRate + '\'' +
                ", encoder=" + encoder +
                '}';
    }
}
