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
import com.stevejrong.music.factory.config.SystemConfig;
import com.stevejrong.music.factory.spi.service.music.metadata.resolver.query.IAudioFileMetadataQueryResolver;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * 抽象音频文件转换器类
 *
 * @author Steve Jrong
 * @since 1.0
 */
public abstract class AbstractAudioFileConverter implements IAudioFileConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractAudioFileConverter.class);

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
     * @param sourcePath      源音频文件位置
     * @param targetDirectory 目标音频文件目录
     * @param targetFileName  目标音频文件名称
     * @param ffmpegBuilder   FFmpegBuilder构建对象
     * @return 经过子类设置的FFmpegBuilder构建对象
     */
    public abstract FFmpegBuilder setFFmpegBuilder(String sourcePath, String targetDirectory, String targetFileName, FFmpegBuilder ffmpegBuilder);

    /**
     * 从源音频文件中，获取元数据信息中专辑封面图片的字节数组
     *
     * @param sourcePath 源音频文件位置
     * @return 源音频文件专辑封面图片元数据信息的字节数组
     */
    protected byte[] getAlbumPictureByteArray(String sourcePath) {
        String sourceFileSuffix = FileUtil.getFileSuffix(sourcePath);
        IAudioFileMetadataQueryResolver metadataQueryResolverBySourceFile = this.getAudioFileMetadataQueryResolverByFileSuffix(sourceFileSuffix);

        byte[] sourceAlbumPictureByteArray;
        AudioFile sourceAudioFile = null;
        try {
            sourceAudioFile = AudioFileIO.read(new File(sourcePath));
        } catch (CannotReadException | ReadOnlyFileException | TagException | IOException | InvalidAudioFrameException e) {
            e.printStackTrace();
        }

        return metadataQueryResolverBySourceFile.getAlbumPicture(sourceAudioFile, false);
    }

    /**
     * 根据音频文件的文件后缀名获取对应的音频文件元数据解析器
     *
     * @param fileSuffix 音频文件的后缀名
     * @return 音频文件元数据解析器
     */
    private IAudioFileMetadataQueryResolver getAudioFileMetadataQueryResolverByFileSuffix(String fileSuffix) {
        return (IAudioFileMetadataQueryResolver) systemConfig
                .getAnalysingAndComplementsForAudioFileConfig().getAudioFileMetadataResolvers().get(fileSuffix)
                .stream().filter(resolver -> resolver instanceof IAudioFileMetadataQueryResolver).findAny().get();
    }
}
