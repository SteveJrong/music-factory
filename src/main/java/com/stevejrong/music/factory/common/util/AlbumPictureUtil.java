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

import com.stevejrong.music.factory.config.SystemConfig;
import com.stevejrong.music.factory.spi.service.music.metadata.resolver.query.IAudioFileMetadataQueryResolver;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.flac.metadatablock.MetadataBlockDataPicture;
import org.jaudiotagger.tag.TagException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * 音频文件的专辑封面图片工具类
 *
 * @author Steve Jrong
 * @since 1.0
 */
public final class AlbumPictureUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(AlbumPictureUtil.class);

    /**
     * 构建用于专辑封面的MetadataBlockDataPicture图片对象
     *
     * @param albumPictureByteArray 图片Byte数组
     * @return MetadataBlockDataPicture图片对象
     */
    public static MetadataBlockDataPicture buildMetadataBlockDataPicture(byte[] albumPictureByteArray) {
        BufferedImage bufferedImage = ArrayUtil.getBufferedImageByPictureByteArray(albumPictureByteArray);

        return new MetadataBlockDataPicture(albumPictureByteArray,
                bufferedImage.getType(),
                "image/jpeg",
                "",
                bufferedImage.getWidth(),
                bufferedImage.getHeight(),
                0,
                0);
    }

    /**
     * 构建Ogg Vorbis音频文件格式元数据中，Base64编码的Blob二进制专辑封面图片数据
     *
     * @param albumPictureByteArray 音频文件中读出的专辑封面图片字节数组
     * @return Base64编码的Blob二进制专辑封面图片数据
     */
    public static String buildBase64BlobMetadataStringOfAlbumPictureByOggVorbis(byte[] albumPictureByteArray) {
        int albumCoverType = 3;
        String albumCoverMimeType = "image/jpeg";
        int albumCoverMimeTypeLength = albumCoverMimeType.length();
        String albumCoverDescription = "Cover (front)";
        int albumCoverDescriptionLength = albumCoverDescription.length();

        StringBuffer sb = new StringBuffer(StringUtil.numberToHexString(albumCoverType, 4));
        sb.append(StringUtil.numberToHexString(albumCoverMimeTypeLength, 4))
                .append(StringUtil.stringToHexString(albumCoverMimeType))
                .append(StringUtil.numberToHexString(albumCoverDescriptionLength, 4))
                .append(StringUtil.stringToHexString(albumCoverDescription));

        for (int i = 0; i < 4; i++) {
            sb.append(StringUtil.numberToHexString(0, 4));
        }

        sb.append(StringUtil.numberToHexString(albumPictureByteArray.length, 4));
        sb.append(StringUtil.byteArrayToHexString(albumPictureByteArray));

        byte[] resultBytes = new byte[0];
        try {
            resultBytes = Hex.decodeHex(sb.toString());
        } catch (DecoderException e) {
            LOGGER.error(LoggerUtil.builder().append("albumPictureUtil_buildBase64BlobMetadataStringOfAlbumPictureByOggVorbis",
                            "构建Ogg Vorbis音频文件格式元数据中，Base64编码的Blob二进制专辑封面图片数据")
                    .append("exception", e).append("exceptionMsg", e.getMessage()).toString());
        }

        return new String(Base64.encodeBase64(resultBytes));
    }

    /**
     * 对字节数组形式的专辑封面图片进行压缩
     *
     * @param width                       要压缩的图片宽度。单位为像素。
     * @param height                      要压缩的图片高度。单位为像素。
     * @param sourceAlbumPictureByteArray 源专辑图片封面字节数组
     * @return 压缩后的专辑图片封面字节数组
     */
    public static byte[] albumPictureCompressByAlbumPictureByteArray(int width, int height, byte[] sourceAlbumPictureByteArray) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            Thumbnails.of(new ByteArrayInputStream(sourceAlbumPictureByteArray)).size(width, height).toOutputStream(outputStream);
        } catch (IOException e) {
            LOGGER.error(LoggerUtil.builder().append("albumPictureUtil_albumPictureCompressByAlbumPictureByteArray",
                            "构建Ogg Vorbis音频文件格式元数据中，Base64编码的Blob二进制专辑封面图片数据")
                    .append("exception", e).append("exceptionMsg", e.getMessage()).toString());
        }

        return outputStream.toByteArray();
    }

    /**
     * 从源音频文件中，获取元数据信息中专辑封面图片的字节数组
     *
     * @param sourcePath 源音频文件位置
     * @return 源音频文件专辑封面图片元数据信息的字节数组
     */
    public static byte[] getAlbumPictureByteArray(SystemConfig systemConfig, String sourcePath) {
        String sourceFileSuffix = FileUtil.getFileSuffixWithoutPoint(sourcePath);
        IAudioFileMetadataQueryResolver metadataQueryResolverBySourceFile = FileUtil.getAudioFileMetadataQueryResolverByFileSuffix(systemConfig, sourceFileSuffix);

        AudioFile sourceAudioFile = null;
        synchronized (AlbumPictureUtil.class) {
            /*
             * 这里必须使用同步块来顺序读取，以此来保证多线程下使用AudioFileIO.read()方法加载文件时，不出现脏读。
             * 若去掉此处的线程同步代码，将会发生若干个音频文件的专辑封面图片，会显示成若干个相同的。
             */
            try {
                sourceAudioFile = AudioFileIO.read(new File(sourcePath));
            } catch (CannotReadException | ReadOnlyFileException | TagException | IOException | InvalidAudioFrameException e) {

                LOGGER.error(LoggerUtil.builder().append("albumPictureUtil_getAlbumPictureByteArray",
                                "从源音频文件中，获取元数据信息中专辑封面图片的字节数组")
                        .append("exception", e).append("exceptionMsg", e.getMessage()).toString());
            }
        }

        metadataQueryResolverBySourceFile.setAudioFile(sourceAudioFile);
        return metadataQueryResolverBySourceFile.getAlbumPicture(false);
    }
}