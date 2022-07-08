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
package com.stevejrong.music.factory.config.sub;

/**
 * 音频文件专辑封面压缩配置
 *
 * @author Steve Jrong
 * @since 1.0
 */
public class AlbumPictureCompressionConfig {

    /**
     * 专辑封面压缩后的音频文件存放位置
     */
    private String compressedAudioFileDirectory;

    /**
     * 音频文件专辑封面压缩的像素值（长宽像素值相同）
     */
    private int compressPixelValue;

    public String getCompressedAudioFileDirectory() {
        return compressedAudioFileDirectory;
    }

    public void setCompressedAudioFileDirectory(String compressedAudioFileDirectory) {
        this.compressedAudioFileDirectory = compressedAudioFileDirectory;
    }

    public int getCompressPixelValue() {
        return compressPixelValue;
    }

    public void setCompressPixelValue(int compressPixelValue) {
        this.compressPixelValue = compressPixelValue;
    }
}