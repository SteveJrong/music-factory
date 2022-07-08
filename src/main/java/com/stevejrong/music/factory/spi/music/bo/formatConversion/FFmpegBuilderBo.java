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
package com.stevejrong.music.factory.spi.music.bo.formatConversion;

import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.io.Serializable;

/**
 * 构建FFmpegBuilder对象的Bo
 *
 * @author Steve Jrong
 * @since 1.0
 */
public final class FFmpegBuilderBo implements Serializable {
    private static final long serialVersionUID = -4292881036916049008L;

    /**
     * 源音频文件位置
     */
    private final String sourcePath;

    /**
     * 目标音频文件目录
     */
    private final String targetDirectory;

    /**
     * 目标音频文件名称
     */
    private final String targetFileName;

    /**
     * 目标音频文件的文件后缀名
     */
    private final String targetFileSuffix;

    /**
     * 目标音频文件的FFmpeg编码器名称
     */
    private final String targetAudioCodecName;

    /**
     * FFmpegBuilder构建对象
     */
    private final FFmpegBuilder ffmpegBuilder;

    public static class Builder {
        /**
         * 源音频文件位置
         */
        private final String sourcePath;

        /**
         * 目标音频文件目录
         */
        private final String targetDirectory;

        /**
         * 目标音频文件名称
         */
        private final String targetFileName;

        /**
         * 目标音频文件的文件后缀名
         */
        private final String targetFileSuffix;

        /**
         * 目标音频文件的FFmpeg编码器名称
         */
        private String targetAudioCodecName = null;

        /**
         * FFmpegBuilder构建对象
         */
        private FFmpegBuilder ffmpegBuilder = null;

        public Builder(String sourcePath, String targetDirectory, String targetFileName, String targetFileSuffix) {
            this.sourcePath = sourcePath;
            this.targetDirectory = targetDirectory;
            this.targetFileName = targetFileName;
            this.targetFileSuffix = targetFileSuffix;
        }

        public Builder targetAudioCodecName(String val) {
            this.targetAudioCodecName = val;
            return this;
        }

        public Builder ffmpegBuilder(FFmpegBuilder val) {
            this.ffmpegBuilder = val;
            return this;
        }

        public FFmpegBuilderBo build() {
            return new FFmpegBuilderBo(this);
        }
    }

    private FFmpegBuilderBo(Builder builder) {
        this.sourcePath = builder.sourcePath;
        this.targetDirectory = builder.targetDirectory;
        this.targetFileName = builder.targetFileName;
        this.targetFileSuffix = builder.targetFileSuffix;
        this.targetAudioCodecName = builder.targetAudioCodecName;
        this.ffmpegBuilder = builder.ffmpegBuilder;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public String getTargetDirectory() {
        return targetDirectory;
    }

    public String getTargetFileName() {
        return targetFileName;
    }

    public String getTargetFileSuffix() {
        return targetFileSuffix;
    }

    public String getTargetAudioCodecName() {
        return targetAudioCodecName;
    }

    public FFmpegBuilder getFfmpegBuilder() {
        return ffmpegBuilder;
    }

    @Override
    public String toString() {
        return "FFmpegBuilderBo{" +
                "sourcePath='" + sourcePath + '\'' +
                ", targetDirectory='" + targetDirectory + '\'' +
                ", targetFileName='" + targetFileName + '\'' +
                ", targetFileSuffix='" + targetFileSuffix + '\'' +
                ", targetAudioCodecName='" + targetAudioCodecName + '\'' +
                ", ffmpegBuilder=" + ffmpegBuilder +
                '}';
    }
}