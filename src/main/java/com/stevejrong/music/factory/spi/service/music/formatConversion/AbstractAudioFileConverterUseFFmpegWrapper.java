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
package com.stevejrong.music.factory.spi.service.music.formatConversion;

import com.stevejrong.music.factory.common.util.AlbumPictureUtil;
import com.stevejrong.music.factory.common.util.FFmpegUtil;
import com.stevejrong.music.factory.common.util.LoggerUtil;
import com.stevejrong.music.factory.spi.music.bo.formatConversion.FFmpegBuilderBo;
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
        String targetPath = ffmpegBuilderBo.getTargetDirectory() + File.separatorChar + ffmpegBuilderBo.getTargetFileName()
                + this.targetFileSuffix();

        return ffmpegBuilderBo.getFfmpegBuilder().addOutput(targetPath)
                .disableVideo()
                .setAudioCodec(ffmpegBuilderBo.getTargetAudioCodecName())
                .setFormat(ffmpegBuilderBo.getTargetFileSuffix().replace(".", ""))
                .setAudioQuality(10.0)
                .addMetaTag("metadata_block_picture", AlbumPictureUtil.buildBase64BlobMetadataStringOfAlbumPictureByOggVorbis(
                        AlbumPictureUtil.albumPictureCompressByAlbumPictureByteArray(
                                500, 500,
                                super.getAlbumPictureByteArray(ffmpegBuilderBo.getSourcePath()))))
                .setStrict(FFmpegBuilder.Strict.NORMAL)
                .done();
    }

    @Override
    public boolean convert(String sourcePath, String targetDirectory, String targetFileName) {
        FFmpegBuilder ffmpegBuilder = this.getFFmpegBuilder(this.buildFFmpegBuilderBo(sourcePath, targetDirectory, targetFileName));

        try {
            FFmpegUtil.convert(ffmpegBuilder);

            LOGGER.info(LoggerUtil.builder().append("abstractAudioFileConverterUseFFmpegWrapper_convert", "成功地转换了音频文件")
                    .append("result", true)
                    .append("sourcePath", sourcePath)
                    .append("targetPath", targetDirectory + File.separatorChar + targetFileName + targetFileSuffix())
                    .toString());

            return true;
        } catch (Exception e) {
            LOGGER.error(LoggerUtil.builder().append("abstractAudioFileConverterUseFFmpegWrapper_convert", "转换音频文件失败")
                    .append("exception", e).append("exceptionMsg", e.getMessage()).toString());

            return false;
        }
    }
}