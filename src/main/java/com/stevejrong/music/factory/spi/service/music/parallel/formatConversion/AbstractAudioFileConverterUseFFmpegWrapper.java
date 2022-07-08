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
package com.stevejrong.music.factory.spi.service.music.parallel.formatConversion;

import com.stevejrong.music.factory.common.constants.BaseConstants;
import com.stevejrong.music.factory.common.util.AlbumPictureUtil;
import com.stevejrong.music.factory.common.util.FFmpegUtil;
import com.stevejrong.music.factory.common.util.LoggerUtil;
import com.stevejrong.music.factory.spi.music.bo.formatConversion.FFmpegBuilderBo;
import com.stevejrong.music.factory.spi.music.bo.parallel.formatConversion.AudioFileFormatConversionTaskBo;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * 适用于使用FFmpegJava包装类组件进行音频文件格式转换的音频格式转换器抽象类
 * <p>
 * 当音频文件格式转换器类，使用FFmpegJava包装类组件进行音频文件格式转换时，需要继承此抽象类
 *
 * @author Steve Jrong
 * @since 1.0
 */
public abstract class AbstractAudioFileConverterUseFFmpegWrapper extends AbstractAudioFileConverter implements IAudioFileConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractAudioFileConverterUseFFmpegWrapper.class);

    /**
     * 构建FFmpegBuilder对象时所需的FFmpegBuilderBo对象
     *
     * @param sourcePath
     * @param targetDirectory
     * @param targetFileName
     * @return
     */
    protected abstract FFmpegBuilderBo buildFFmpegBuilderBo(String sourcePath, String targetDirectory, String targetFileName);

    /**
     * 设置并获取目标音频文件的FFmpeg编码器名称
     *
     * @return
     */
    protected abstract String getTargetAudioCodecName();

    @Override
    public FFmpegBuilder getFFmpegBuilder(FFmpegBuilderBo ffmpegBuilderBo) {
        String targetPath = ffmpegBuilderBo.getTargetDirectory() + File.separatorChar
                + ffmpegBuilderBo.getTargetFileName()
                + this.targetFileSuffix();

        return ffmpegBuilderBo.getFfmpegBuilder().addOutput(targetPath)
                .disableVideo()
                .setAudioCodec(ffmpegBuilderBo.getTargetAudioCodecName())
                .setFormat(ffmpegBuilderBo.getTargetFileSuffix().replace(BaseConstants.POINT_CHAR, BaseConstants.SPACE_STRING))
                .setAudioQuality(BaseConstants.DEFAULT_AUDIO_QUALITY)
                .addMetaTag(BaseConstants.METADATA_BLOCK_PICTURE, AlbumPictureUtil.buildBase64BlobMetadataStringOfAlbumPictureByOggVorbis(
                        AlbumPictureUtil.albumPictureCompressByAlbumPictureByteArray(
                                super.getSystemConfig().getAlbumPictureCompressionConfig().getCompressPixelValue(),
                                super.getSystemConfig().getAlbumPictureCompressionConfig().getCompressPixelValue(),
                                AlbumPictureUtil.getAlbumPictureByteArray(super.getSystemConfig(), ffmpegBuilderBo.getSourcePath()))))
                .setStrict(FFmpegBuilder.Strict.NORMAL)
                .done();
    }

    @Override
    public boolean execute(AudioFileFormatConversionTaskBo paramBo) {
        String sourcePath = paramBo.getSourcePath();
        String targetDirectory = paramBo.getTargetDirectory();
        String targetFileName = paramBo.getSourceFileName();

        FFmpegBuilderBo fFmpegBuilderBo = this.buildFFmpegBuilderBo(sourcePath, targetDirectory, targetFileName);
        FFmpegBuilder ffmpegBuilder = this.getFFmpegBuilder(fFmpegBuilderBo);

        try {
            FFmpegUtil.convert(ffmpegBuilder);

            LOGGER.info(LoggerUtil.builder().append("abstractAudioFileConverterUseFFmpegWrapper_execute", "音频文件格式转换成功")
                    .append("sourcePath", sourcePath)
                    .append("targetPath", targetDirectory + File.separatorChar + targetFileName + this.targetFileSuffix())
                    .toString());

            return true;
        } catch (Exception e) {
            LOGGER.error(LoggerUtil.builder().append("abstractAudioFileConverterUseFFmpegWrapper_convert", "音频文件格式转换异常")
                    .append("exception", e).append("exceptionMsg", e.getMessage())
                    .append("fFmpegBuilderBo", fFmpegBuilderBo)
                    .append("sourcePath", sourcePath)
                    .append("targetDirectory", targetDirectory)
                    .append("targetFileName", targetFileName)
                    .append("targetPath", targetDirectory + File.separatorChar + targetFileName + this.targetFileSuffix())
                    .toString());

            return false;
        }
    }
}