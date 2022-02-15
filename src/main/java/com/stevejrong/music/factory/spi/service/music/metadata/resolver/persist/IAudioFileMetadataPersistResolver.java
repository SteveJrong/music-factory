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
package com.stevejrong.music.factory.spi.service.music.metadata.resolver.persist;

import com.stevejrong.music.factory.common.constants.BaseConstants;
import com.stevejrong.music.factory.common.util.LoggerUtil;
import com.stevejrong.music.factory.spi.service.music.metadata.resolver.query.IAudioFileMetadataQueryResolver;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.dsf.DsfFileWriter;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.AbstractID3v1Tag;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.ID3v23Tag;
import org.jaudiotagger.tag.images.Artwork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;

/**
 * 文件元数据存储器接口
 * <p>
 * 针对不同的音频文件类型，去对应实现不同的元数据存储器，保存元数据信息
 *
 * @author Steve Jrong
 * @since 1.0
 */
public interface IAudioFileMetadataPersistResolver {
    Logger LOGGER = LoggerFactory.getLogger(IAudioFileMetadataPersistResolver.class);

    /**
     * 设置音频文件对象
     *
     * @param audioFile 音频文件对象
     */
    void setAudioFile(AudioFile audioFile);

    /**
     * 设置音频文件文件元数据解析器接口的实例
     *
     * @param metadataQueryResolver 音频文件文件元数据解析器接口的实例
     */
    void setIAudioFileMetadataQueryResolver(IAudioFileMetadataQueryResolver metadataQueryResolver);

    /**
     * 设置音频文件元数据中的歌曲标题
     *
     * @param songTitle 歌曲标题
     */
    void setSongTitle(String songTitle);

    /**
     * 设置音频文件元数据中的歌曲艺术家
     *
     * @param songArtist 歌曲艺术家
     */
    void setSongArtist(String songArtist);

    /**
     * 设置音频文件元数据中歌曲所属的专辑名称
     *
     * @param albumName 歌曲所属的专辑名称
     */
    void setAlbumName(String albumName);

    /**
     * 设置音频文件元数据中歌曲所属专辑的封面
     *
     * @param albumPictureByteArray 歌曲所属专辑的封面
     */
    void setAlbumPicture(byte[] albumPictureByteArray);

    /**
     * 设置音频文件元数据中的歌曲内嵌歌词
     *
     * @param songLyrics 歌曲内嵌歌词
     */
    void setSongLyrics(String songLyrics);

    /**
     * 设置音频文件元数据中的歌曲所属专辑的艺术家
     *
     * @param albumArtist 歌曲所属专辑的艺术家
     */
    void setAlbumArtist(String albumArtist);

    /**
     * 设置音频文件元数据中的歌曲所属专辑的发布时间
     *
     * @param albumPublishDate 歌曲所属专辑的发布时间
     */
    void setAlbumPublishDate(LocalDate albumPublishDate);

    /**
     * 设置音频文件元数据中的歌曲所属专辑的描述
     *
     * @param albumDescription 歌曲所属专辑的描述
     */
    void setAlbumDescription(String albumDescription);

    /**
     * 设置音频文件元数据中的歌曲所属专辑的语言类型
     *
     * @param albumLanguage 歌曲所属专辑的语言类型
     */
    void setAlbumLanguage(String albumLanguage);

    /**
     * 设置音频文件元数据中的歌曲所属专辑的版权信息
     *
     * @param albumCopyright 歌曲所属专辑的版权信息
     */
    void setAlbumCopyright(String albumCopyright);

    /**
     * 设置专辑封面图片并应用
     *
     * @param artwork   专辑封面图片对象
     * @param audioFile 音频文件对象
     */
    default void setFieldAndCommit(Tag tag, Artwork artwork, AudioFile audioFile) {
        try {
            tag.setField(artwork);

            if (audioFile instanceof MP3File) {
                ((MP3File) audioFile).save();
            } else {
                AudioFileIO.write(audioFile);
            }
        } catch (CannotWriteException | IOException | TagException e) {
            LOGGER.error(LoggerUtil.builder().append("iAudioFileMetadataPersistResolver_setFieldAndCommit", "设置专辑封面并应用")
                    .append("exception", e).append("exceptionMsg", e.getMessage()).toString());
        }
    }

    /**
     * 设置属性并应用
     *
     * @param tag        音频文件标签对象
     * @param fieldKey   属性标签对象
     * @param fieldValue 属性标签值
     * @param audioFile  音频文件对象
     */
    default void setFieldAndCommit(Tag tag, FieldKey fieldKey, String fieldValue, AudioFile audioFile) {
        try {
            tag.setField(fieldKey, fieldValue);

            if (tag instanceof AbstractID3v2Tag && audioFile instanceof MP3File
                    && !((MP3File) audioFile).hasID3v2Tag()) {

                ((MP3File) audioFile).setID3v2Tag((ID3v23Tag) tag);
                audioFile.commit();
            } else if (tag instanceof AbstractID3v1Tag && audioFile instanceof MP3File
                    && !((MP3File) audioFile).hasID3v1Tag()) {

                ((MP3File) audioFile).setID3v1Tag(tag);
                audioFile.commit();
            }

            if (audioFile instanceof MP3File) {

                ((MP3File) audioFile).save();
            } else if (BaseConstants.FILE_SUFFIX_DSF.equals(audioFile.getExt())) {

                // 处理DSF音频文件的元数据信息保存
                audioFile.setTag(tag);
                new DsfFileWriter().write(audioFile);
            } else {

                // 处理除MP3和DSF音频文件以外的其他音频文件的元数据信息保存
                AudioFileIO.write(audioFile);
            }
        } catch (TagException | CannotWriteException | IOException e) {
            LOGGER.error(LoggerUtil.builder().append("iAudioFileMetadataPersistResolver_setFieldAndCommit", "设置属性并应用")
                    .append("exception", e).append("exceptionMsg", e.getMessage()).toString());
        }
    }
}
