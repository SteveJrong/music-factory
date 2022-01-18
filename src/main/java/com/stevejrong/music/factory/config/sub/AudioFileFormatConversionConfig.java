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

import com.stevejrong.music.factory.spi.service.music.formatConversion.IAudioFileConverter;

import java.util.Map;

/**
 * 音频文件转换配置类
 */
public class AudioFileFormatConversionConfig {

    /**
     * 转换格式后的音频文件存放位置
     */
    private String convertedAudioFileDirectory;

    /**
     * FFmpeg组件配置
     */
    private FfmpegComponentConfig ffmpegComponentConfig;

    /**
     * 音频文件格式和转换器对应关系Map集合
     * <p>
     * Key - 音频文件编码类型（如mp3、flac等）；Value - 格式转换器
     */
    private Map<String, IAudioFileConverter> audioFileConverters;

    public String getConvertedAudioFileDirectory() {
        return convertedAudioFileDirectory;
    }

    public void setConvertedAudioFileDirectory(String convertedAudioFileDirectory) {
        this.convertedAudioFileDirectory = convertedAudioFileDirectory;
    }

    public FfmpegComponentConfig getFfmpegComponentConfig() {
        return ffmpegComponentConfig;
    }

    public void setFfmpegComponentConfig(FfmpegComponentConfig ffmpegComponentConfig) {
        this.ffmpegComponentConfig = ffmpegComponentConfig;
    }

    public Map<String, IAudioFileConverter> getAudioFileConverters() {
        return audioFileConverters;
    }

    public void setAudioFileConverters(Map<String, IAudioFileConverter> audioFileConverters) {
        this.audioFileConverters = audioFileConverters;
    }
}
