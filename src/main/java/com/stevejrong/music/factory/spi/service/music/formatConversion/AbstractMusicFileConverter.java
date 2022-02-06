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

import com.stevejrong.music.factory.common.util.FileUtil;
import com.stevejrong.music.factory.common.util.LoggerUtil;
import com.stevejrong.music.factory.config.SystemConfig;
import com.stevejrong.music.factory.spi.service.music.metadata.resolver.persist.IAudioFileMetadataPersistResolver;
import com.stevejrong.music.factory.spi.service.music.metadata.resolver.query.IAudioFileMetadataQueryResolver;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.apache.commons.lang3.ArrayUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public abstract class AbstractMusicFileConverter implements IAudioFileConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMusicFileConverter.class);

    private SystemConfig systemConfig;

    public SystemConfig getSystemConfig() {
        return systemConfig;
    }

    public void setSystemConfig(SystemConfig systemConfig) {
        this.systemConfig = systemConfig;
    }

    /**
     * 创建默认的FFmpegBuilder对象
     *
     * @param sourcePath
     * @return
     */
    protected FFmpegBuilder createDefaultFFmpegBuilder(String sourcePath) {
        FFmpegBuilder ffmpegBuilder = new FFmpegBuilder()
                .overrideOutputFiles(true)
                .setInput(sourcePath);

        return ffmpegBuilder;
    }

    /**
     * 设置格式转换时的特殊个性化参数到FFmpegBuilder构建对象中
     * <p>
     * 只要进行格式转换，则FFmpeg组件的参数一定要设置，故必须强制自类重写此方法。
     *
     * @param targetDirectory
     * @param targetFileName
     * @param ffmpegBuilder
     * @return
     */
    public abstract FFmpegBuilder setFFmpegBuilder(String targetDirectory, String targetFileName, FFmpegBuilder ffmpegBuilder);

    /**
     * 从源音频文件中，复制专辑封面图片到目标音频文件的元数据信息中
     *
     * @param sourcePath
     * @param targetPath
     * @return
     */
    protected boolean copyAlbumPicture(String sourcePath, String targetPath) {
        String sourceFileSuffix = FileUtil.getFileSuffix(sourcePath);
        String targetFileSuffix = FileUtil.getFileSuffix(targetPath);

        IAudioFileMetadataQueryResolver metadataQueryResolverBySourceFile = this.getAudioFileMetadataQueryResolverByFileSuffix(sourceFileSuffix);

        byte[] sourceAlbumPictureByteArray;
        try {
            AudioFile sourceAudioFile = AudioFileIO.read(new File(sourcePath));
            AudioFile targetAudioFile = AudioFileIO.read(new File(targetPath));

            sourceAlbumPictureByteArray = metadataQueryResolverBySourceFile.getAlbumPicture(sourceAudioFile, false);
            if (ArrayUtils.isNotEmpty(sourceAlbumPictureByteArray)) {

                IAudioFileMetadataPersistResolver metadataPersistResolver = (IAudioFileMetadataPersistResolver) systemConfig
                        .getAnalysingAndComplementsForAudioFileConfig().getAudioFileMetadataResolvers().get(targetFileSuffix)
                        .stream().filter(resolver -> resolver instanceof IAudioFileMetadataPersistResolver).findAny().get();

                metadataPersistResolver.setAudioFile(targetAudioFile);

                IAudioFileMetadataQueryResolver metadataQueryResolverByTargetFile = this.getAudioFileMetadataQueryResolverByFileSuffix(targetFileSuffix);
                metadataPersistResolver.setIAudioFileMetadataQueryResolver(metadataQueryResolverByTargetFile);

                metadataPersistResolver.setAlbumPicture(sourceAlbumPictureByteArray);
            } else {

                LOGGER.warn(LoggerUtil.builder().append("abstractMusicFileConverter_copyAlbumPicture", "音频文件专辑封面图片拷贝")
                        .append("sourcePath", sourcePath).append("sourceAlbumPictureByteArray_length", sourceAlbumPictureByteArray.length)
                        .append("msg", "源音频文件无专辑封面图片，无法拷贝")
                        .toString());
            }

            LOGGER.info(LoggerUtil.builder().append("abstractMusicFileConverter_copyAlbumPicture", "音频文件专辑封面图片拷贝")
                    .append("sourcePath", sourcePath).append("targetPath", targetPath)
                    .append("sourceAlbumPictureByteArray_length", sourceAlbumPictureByteArray.length)
                    .toString());
        } catch (Exception e) {
            LOGGER.error(LoggerUtil.builder().append("abstractMusicFileConverter_copyAlbumPicture", "音频文件专辑封面图片拷贝")
                    .append("exception", e).append("exceptionMsg", e.getMessage()).toString());

            return false;
        }

        return true;
    }

    private IAudioFileMetadataQueryResolver getAudioFileMetadataQueryResolverByFileSuffix(String fileSuffix) {
        return (IAudioFileMetadataQueryResolver) systemConfig
                .getAnalysingAndComplementsForAudioFileConfig().getAudioFileMetadataResolvers().get(fileSuffix)
                .stream().filter(resolver -> resolver instanceof IAudioFileMetadataQueryResolver).findAny().get();
    }
}
