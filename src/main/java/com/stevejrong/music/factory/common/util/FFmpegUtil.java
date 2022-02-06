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
package com.stevejrong.music.factory.common.util;

import com.stevejrong.music.factory.common.enums.SupportOSForFFmpegEnum;
import com.stevejrong.music.factory.config.SystemConfig;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * FFmpeg工具类
 */
public final class FFmpegUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FFmpegUtil.class);

    public static volatile FFmpeg FFMPEG = null;
    public static volatile FFprobe FFPROBE = null;
    private static volatile FFmpegExecutor FFMPEG_EXECUTOR = null;
    private static FFmpegProbeResult FFMPEG_PROBE_RESULT = null;

    /**
     * 获取FFmpeg实例
     *
     * @return FFmpeg对象
     */
    private static synchronized FFmpeg getFfmpegInstanceByOSType() {
        if (null == FFMPEG) {
            SystemConfig systemConfig = SpringBeanUtil.getBean("systemConfig");
            Map<SupportOSForFFmpegEnum, String> ffmpegPathsByOSTypeMaps = systemConfig
                    .getAudioFileFormatConversionConfig().getFfmpegComponentConfig().getFfmpegPathsByOSType();

            SupportOSForFFmpegEnum operatingSystemEnum = PlatformUtil.getOperatingSystemType();

            File ffmpegFile = FileUtil.getResourceFile(ffmpegPathsByOSTypeMaps.get(operatingSystemEnum));
            ffmpegFile.setExecutable(true);

            try {
                FFMPEG = new FFmpeg(ffmpegFile.getPath());
            } catch (IOException e) {
                LOGGER.error(LoggerUtil.builder().append("fFmpegUtil_getFfmpegInstanceByOSType")
                        .append("exception", e).append("exceptionMsg", e.getMessage()).toString());
            }
        }

        return FFMPEG;
    }

    /**
     * 获取FFprobe实例
     *
     * @return FFprobe对象
     */
    private static synchronized FFprobe getFfprobeInstanceByOSType() {
        if (null == FFPROBE) {
            SystemConfig systemConfig = SpringBeanUtil.getBean("systemConfig");
            Map<SupportOSForFFmpegEnum, String> ffprobePathsByOSTypeMaps = systemConfig
                    .getAudioFileFormatConversionConfig().getFfmpegComponentConfig().getFfmprobePathsByOSType();

            SupportOSForFFmpegEnum operatingSystemEnum = PlatformUtil.getOperatingSystemType();

            File ffprobeFile = FileUtil.getResourceFile(ffprobePathsByOSTypeMaps.get(operatingSystemEnum));
            ffprobeFile.setExecutable(true);

            try {
                FFPROBE = new FFprobe(ffprobeFile.getPath());
            } catch (IOException e) {
                LOGGER.error(LoggerUtil.builder().append("fFmpegUtil_getFfprobeInstanceByOSType")
                        .append("exception", e).append("exceptionMsg", e.getMessage()).toString());
            }
        }

        return FFPROBE;
    }

    /**
     * 获取Ffmpeg 执行者的实例
     *
     * @param fFmpegInstacnce Ffmpeg对象
     * @param ffprobeInstance Ffmpeg Probe对象
     * @return FFmpegExecutor对象
     */
    private static synchronized FFmpegExecutor getFfmpegExecutorInstance(FFmpeg fFmpegInstacnce, FFprobe ffprobeInstance) {
        if (null == FFMPEG_EXECUTOR) {
            FFMPEG_EXECUTOR = new FFmpegExecutor(fFmpegInstacnce, ffprobeInstance);
        }

        return FFMPEG_EXECUTOR;
    }

    /**
     * 获取Ffmpeg Probe结果的实例
     *
     * @param ffprobeInstance Ffmpeg Probe对象
     * @param sourceFilePath  原始音频文件位置
     * @return FFmpegProbeResult对象
     */
    private static FFmpegProbeResult getFfmpegProbeResultInstance(FFprobe ffprobeInstance, String sourceFilePath) {
        if (null == FFMPEG_PROBE_RESULT) {
            try {
                FFMPEG_PROBE_RESULT = ffprobeInstance.probe(sourceFilePath);
            } catch (IOException e) {
                LOGGER.error(LoggerUtil.builder().append("fFmpegUtil_getFfmpegProbeResultInstance")
                        .append("exception", e).append("exceptionMsg", e.getMessage()).toString());
            }
        }

        return FFMPEG_PROBE_RESULT;
    }

    /**
     * 将受支持的音频文件转换为FLAC音频文件
     *
     * @param ffmpegBuilder FFmpegBuilder对象
     */
    public static void convert(FFmpegBuilder ffmpegBuilder) {
        getFfmpegExecutorInstance(getFfmpegInstanceByOSType(), getFfprobeInstanceByOSType())
                .createJob(ffmpegBuilder)
                .run();
    }
}
