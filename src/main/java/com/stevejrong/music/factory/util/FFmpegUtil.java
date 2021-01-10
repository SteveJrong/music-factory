package com.stevejrong.music.factory.util;

import com.stevejrong.music.factory.common.constants.BaseConstants;
import com.stevejrong.music.factory.config.ConvertMusicFileConfig;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;

import java.io.IOException;

/**
 * FFmpeg工具类
 */
public class FFmpegUtil {

    /**
     * 将受支持的音频文件转换为FLAC音频文件
     *
     * @param sourceFilePath 原始音频文件路径
     * @param targetFilePath 目标音频文件路径
     * @param convertMusicFileConfig 音频文件转换配置
     */
    public static void convertToFlacMusicFile(String sourceFilePath, String targetFilePath, ConvertMusicFileConfig convertMusicFileConfig) {
        /*
        * 使用java-flac-encoder组件也可以将WAV快速转换为FLAC
        *
        * 示例代码：
        *
            FLAC_FileEncoder flacEncoder = new FLAC_FileEncoder();
            File inputFile = new File(sourceFileName + sourceFileFormat);
            File outputFile = new File(targetFileName + targetFileFormat);
            flacEncoder.encode(inputFile, outputFile);
        */

        FFmpeg ffmpeg;
        FFprobe ffprobe;
        FFmpegExecutor executor = null;
        FFmpegProbeResult in = null;
        try {
            ffmpeg = new FFmpeg(convertMusicFileConfig.getFfmpegFileDirectory());
            ffprobe = new FFprobe(convertMusicFileConfig.getFfprobeFileDirectory());

            executor = new FFmpegExecutor(ffmpeg, ffprobe);
            in = ffprobe.probe(sourceFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(in)
                .overrideOutputFiles(true)
                .addOutput(targetFilePath)
                .setFormat(BaseConstants.MUSIC_ENCODE_FLAC)
                .setAudioCodec(BaseConstants.MUSIC_ENCODE_FLAC)
                .setAudioChannels(FFmpeg.AUDIO_STEREO)
                .setAudioSampleFormat(FFmpeg.AUDIO_FORMAT_S16)
                .setAudioSampleRate(FFmpeg.AUDIO_SAMPLE_48000)
                .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
                .done();

        executor.createJob(builder).run();
    }
}
