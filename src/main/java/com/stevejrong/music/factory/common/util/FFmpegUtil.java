package com.stevejrong.music.factory.common.util;

import com.stevejrong.music.factory.common.constants.BaseConstants;
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

    public static FFmpeg FFMPEG = null;
    public static FFprobe FFPROBE = null;

    /**
     * 获取FFmpeg实例
     *
     * @return FFmpeg对象
     */
    public static FFmpeg getFfmpegInstanceByOSType() {
        if (null == FFMPEG) {
            SystemConfig systemConfig = SpringBeanUtil.getBean("systemConfig");
            Map<SupportOSForFFmpegEnum, String> ffmpegPathsByOSTypeMaps = systemConfig
                    .getAudioFileFormatConversionConfig().getFfmpegComponentConfig().getFfmpegPathsByOSType();

            SupportOSForFFmpegEnum operatingSystemEnum = PlatformUtil.getOperatingSystemType();

            File ffmpegFile = FileUtil.getResourceFile(
                    ffmpegPathsByOSTypeMaps.get(operatingSystemEnum));
            ffmpegFile.setExecutable(true);
            try {
                FFMPEG = new FFmpeg(ffmpegFile.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return FFMPEG;
    }

    /**
     * 获取FFprobe实例
     *
     * @return FFprobe对象
     */
    public static FFprobe getFfprobeInstanceByOSType() {
        if (null == FFPROBE) {
            SystemConfig systemConfig = SpringBeanUtil.getBean("systemConfig");
            Map<SupportOSForFFmpegEnum, String> ffprobePathsByOSTypeMaps = systemConfig
                    .getAudioFileFormatConversionConfig().getFfmpegComponentConfig().getFfmprobePathsByOSType();

            SupportOSForFFmpegEnum operatingSystemEnum = PlatformUtil.getOperatingSystemType();

            File ffprobeFile = FileUtil.getResourceFile(
                    ffprobePathsByOSTypeMaps.get(operatingSystemEnum));
            ffprobeFile.setExecutable(true);
            try {
                FFPROBE = new FFprobe(ffprobeFile.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return FFPROBE;
    }

    /**
     * 将受支持的音频文件转换为FLAC音频文件
     *
     * @param sourceFilePath                  原始音频文件位置
     * @param targetFilePath                  目标音频文件位置
     */
    public static void convertToFlac(String sourceFilePath, String targetFilePath) {
        FFmpegExecutor executor = null;
        FFmpegProbeResult in = null;
        try {
            executor = new FFmpegExecutor(getFfmpegInstanceByOSType(), getFfprobeInstanceByOSType());
            in = getFfprobeInstanceByOSType().probe(sourceFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(in)
                .overrideOutputFiles(true)
                .addOutput(targetFilePath)
                .setFormat(BaseConstants.MUSIC_ENCODE_FLAC)
                .setAudioCodec(BaseConstants.MUSIC_ENCODE_FLAC)
                .setStrict(FFmpegBuilder.Strict.NORMAL)
                .done();

        executor.createJob(builder).run();
    }
}
