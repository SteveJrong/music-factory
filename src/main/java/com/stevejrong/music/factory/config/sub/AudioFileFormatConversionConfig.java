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

import java.util.List;

/**
 * 音频文件转换配置类
 *
 * @author Steve Jrong
 * @since 1.0
 */
public class AudioFileFormatConversionConfig {

    /**
     * 转换格式后的音频文件存放位置
     */
    private String convertedAudioFileDirectory;

    /**
     * FFmpeg组件配置
     */
    private FFmpegComponentConfig ffmpegComponentConfig;

    /**
     * 音频格式转换时所需动态链接库文件的操作系统支持配置
     */
    private ResourcesByOperatingSystemConfig formatConversionDllUtilsComponentConfig;

    /**
     * 音频文件格式转换器集合
     */
    private List<IAudioFileConverter> audioFileConverters;

    /**
     * 用户选择的音频文件格式转换器对象集合
     */
    private List<IAudioFileConverter> selectedAudioFileConverters;

    public String getConvertedAudioFileDirectory() {
        return convertedAudioFileDirectory;
    }

    public void setConvertedAudioFileDirectory(String convertedAudioFileDirectory) {
        this.convertedAudioFileDirectory = convertedAudioFileDirectory;
    }

    public FFmpegComponentConfig getFfmpegComponentConfig() {
        return ffmpegComponentConfig;
    }

    public void setFfmpegComponentConfig(FFmpegComponentConfig ffmpegComponentConfig) {
        this.ffmpegComponentConfig = ffmpegComponentConfig;
    }

    public List<IAudioFileConverter> getAudioFileConverters() {
        return audioFileConverters;
    }

    public void setAudioFileConverters(List<IAudioFileConverter> audioFileConverters) {
        this.audioFileConverters = audioFileConverters;
    }

    public List<IAudioFileConverter> getSelectedAudioFileConverters() {
        return selectedAudioFileConverters;
    }

    public void setSelectedAudioFileConverters(List<IAudioFileConverter> selectedAudioFileConverters) {
        this.selectedAudioFileConverters = selectedAudioFileConverters;
    }

    public ResourcesByOperatingSystemConfig getFormatConversionDllUtilsComponentConfig() {
        return formatConversionDllUtilsComponentConfig;
    }

    public void setFormatConversionDllUtilsComponentConfig(ResourcesByOperatingSystemConfig formatConversionDllUtilsComponentConfig) {
        this.formatConversionDllUtilsComponentConfig = formatConversionDllUtilsComponentConfig;
    }
}
