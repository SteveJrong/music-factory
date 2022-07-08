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
package com.stevejrong.music.factory.provider.service.music.albumPictureCompression.impl;

import com.stevejrong.music.factory.common.util.AlbumPictureUtil;
import com.stevejrong.music.factory.common.util.FileUtil;
import com.stevejrong.music.factory.common.util.LoggerUtil;
import com.stevejrong.music.factory.spi.music.bo.parallel.albumPictureCompression.AlbumPictureCompressionTaskBo;
import com.stevejrong.music.factory.spi.service.music.metadata.resolver.persist.IAudioFileMetadataPersistResolver;
import com.stevejrong.music.factory.spi.service.music.metadata.resolver.query.IAudioFileMetadataQueryResolver;
import com.stevejrong.music.factory.spi.service.music.parallel.albumPictureCompression.AbstractAudioFileAlbumPictureCompressor;
import com.stevejrong.music.factory.spi.service.music.parallel.albumPictureCompression.IAudioFileAlbumPictureCompressor;
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
 * 音频文件专辑封面压缩器
 *
 * @author Steve Jrong
 * @since 1.0
 */
public class AudioFileAlbumPictureCompressor extends AbstractAudioFileAlbumPictureCompressor implements IAudioFileAlbumPictureCompressor {
    private static final Logger LOGGER = LoggerFactory.getLogger(AudioFileAlbumPictureCompressor.class);

    @Override
    public synchronized boolean execute(AlbumPictureCompressionTaskBo paramBo) {
        // 目标文件位置
        String targetPath = paramBo.getTargetDirectory() + File.separatorChar + paramBo.getSourceFileName();

        // 源音频文件中的专辑封面字节数组
        byte[] sourceAlbumPictureByteArray = AlbumPictureUtil.getAlbumPictureByteArray(super.getSystemConfig(), paramBo.getSourcePath());

        // 压缩后的专辑封面字节数组
        byte[] compressedAlbumPictureByteArray = AlbumPictureUtil.albumPictureCompressByAlbumPictureByteArray(super.compressPixelValue(),
                super.compressPixelValue(),
                sourceAlbumPictureByteArray);

        // 拷贝源文件副本到目标位置下，以操作副本，而不是源文件
        if (!FileUtil.fileCopy(paramBo.getSourcePath(), targetPath)) {
            LOGGER.error(LoggerUtil.builder().append("audioFileAlbumPictureCompressor_execute", "原始音频文件拷贝异常")
                    .append("exceptionMsg", "原始音频文件拷贝异常")
                    .append("paramBo", paramBo)
                    .append("sourcePath", paramBo.getSourcePath())
                    .append("targetPath", targetPath)
                    .append("sourceAlbumPictureByteArray_length", sourceAlbumPictureByteArray.length)
                    .append("compressedAlbumPictureByteArray_length", compressedAlbumPictureByteArray.length)
                    .toString());

            return false;
        }

        // 读取目标音频文件
        String targetFileSuffix = FileUtil.getFileSuffixWithoutPoint(targetPath);
        /*
         * 这里必须使用同步块来顺序读取，以此来保证多线程下使用AudioFileIO.read()方法加载文件时，不出现脏读。
         * 若去掉此处的线程同步代码，将会发生若干个音频文件的专辑封面图片，会显示成若干个相同的。
         */
        AudioFile targetAudioFile;
        try {
            targetAudioFile = AudioFileIO.read(new File(targetPath));
        } catch (CannotReadException | ReadOnlyFileException | TagException | IOException | InvalidAudioFrameException e) {
            LOGGER.error(LoggerUtil.builder().append("audioFileAlbumPictureCompressor_execute", "读取原始音频文件异常")
                    .append("exception", e).append("exceptionMsg", e.getMessage())
                    .append("targetFileSuffix", targetFileSuffix)
                    .append("paramBo", paramBo)
                    .append("sourcePath", paramBo.getSourcePath())
                    .append("targetPath", targetPath)
                    .append("sourceAlbumPictureByteArray_length", sourceAlbumPictureByteArray.length)
                    .append("compressedAlbumPictureByteArray_length", compressedAlbumPictureByteArray.length)
                    .toString());

            return false;
        }

        // 将压缩后的专辑封面字节数组，写入到目标文件中
        IAudioFileMetadataPersistResolver metadataPersistResolver = (IAudioFileMetadataPersistResolver)
                super.getSystemConfig().getAnalysingAndComplementsForAudioFileConfig().getAudioFileMetadataResolvers().get(targetFileSuffix)
                        .stream().filter(resolver -> resolver instanceof IAudioFileMetadataPersistResolver).findAny().get();
        metadataPersistResolver.setAudioFile(targetAudioFile);

        IAudioFileMetadataQueryResolver metadataQueryResolver = (IAudioFileMetadataQueryResolver)
                super.getSystemConfig().getAnalysingAndComplementsForAudioFileConfig().getAudioFileMetadataResolvers().get(targetFileSuffix)
                        .stream().filter(resolver -> resolver instanceof IAudioFileMetadataQueryResolver).findAny().get();
        metadataPersistResolver.setIAudioFileMetadataQueryResolver(metadataQueryResolver);

        try {
            metadataPersistResolver.setAlbumPicture(compressedAlbumPictureByteArray, true);
        } catch (Exception e) {
            LOGGER.error(LoggerUtil.builder().append("audioFileAlbumPictureCompressor_execute", "设置音频文件元数据中歌曲所属专辑的封面异常")
                    .append("exception", e).append("exceptionMsg", e.getMessage())
                    .append("targetFileSuffix", targetFileSuffix)
                    .append("paramBo", paramBo)
                    .append("sourcePath", paramBo.getSourcePath())
                    .append("targetPath", targetPath)
                    .append("sourceAlbumPictureByteArray_length", sourceAlbumPictureByteArray.length)
                    .append("compressedAlbumPictureByteArray_length", compressedAlbumPictureByteArray.length)
                    .toString());

            return false;
        }

        LOGGER.info(LoggerUtil.builder().append("audioFileAlbumPictureCompressor_execute", "音频文件专辑封面压缩成功")
                .append("sourcePath", paramBo.getSourcePath())
                .append("targetPath", targetPath)
                .toString());

        return true;
    }
}