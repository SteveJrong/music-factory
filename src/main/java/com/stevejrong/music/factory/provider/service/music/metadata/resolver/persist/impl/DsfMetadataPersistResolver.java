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
package com.stevejrong.music.factory.provider.service.music.metadata.resolver.persist.impl;

import com.google.common.collect.Lists;
import com.stevejrong.music.factory.common.enums.ID3v2FramesForMP3Enum;
import com.stevejrong.music.factory.common.util.*;
import com.stevejrong.music.factory.spi.service.music.metadata.resolver.persist.AbstractAudioFileMetadataPersistResolver;
import com.stevejrong.music.factory.spi.service.music.metadata.resolver.persist.IAudioFileMetadataPersistResolver;
import com.stevejrong.music.factory.spi.service.music.metadata.resolver.query.IAudioFileMetadataQueryResolver;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagField;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.AbstractTagFrame;
import org.jaudiotagger.tag.id3.AbstractTagFrameBody;
import org.jaudiotagger.tag.id3.TyerTdatAggregatedFrame;
import org.jaudiotagger.tag.id3.framebody.FrameBodyTDAT;
import org.jaudiotagger.tag.id3.framebody.FrameBodyTYER;
import org.jaudiotagger.tag.images.Artwork;
import org.jaudiotagger.tag.images.ArtworkFactory;

import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Dsf（索尼专有格式）音频文件的元数据存储器
 *
 * @author Steve Jrong
 * @since 1.0
 */
public class DsfMetadataPersistResolver extends AbstractAudioFileMetadataPersistResolver implements IAudioFileMetadataPersistResolver {

    @Override
    public void setAudioFile(AudioFile audioFile) {
        super.audioFile = audioFile;
    }

    @Override
    public void setIAudioFileMetadataQueryResolver(IAudioFileMetadataQueryResolver metadataQueryResolver) {
        super.metadataQueryResolver = metadataQueryResolver;
    }

    @Override
    public void setSongTitle(String songTitle) {
        AbstractID3v2Tag id3v2Tag = (AbstractID3v2Tag) DsfUtil.checkID3v2Tag(super.getAudioFile());

        String songTitleForID3v2Tag = Mp3Util.getContentByContentKeyAndMp3FrameNameInID3v2Tag(ID3v2FramesForMP3Enum.TIT2.getValue(), "Text", id3v2Tag);
        if (StringUtils.isNotBlank(songTitle) && StringUtils.isBlank(songTitleForID3v2Tag)) {
            // 当ID3v2标签中没有歌曲名称时，需设置ID3v2标签中的歌曲名称信息
            setFieldAndCommit(id3v2Tag, FieldKey.TITLE, songTitle, getAudioFile());
        }
    }

    @Override
    public void setSongArtist(String songArtist) {
        AbstractID3v2Tag id3v2Tag = (AbstractID3v2Tag) DsfUtil.checkID3v2Tag(super.getAudioFile());

        String songTitleForID3v2Tag = Mp3Util.getContentByContentKeyAndMp3FrameNameInID3v2Tag(ID3v2FramesForMP3Enum.TPE1.getValue(), "Text", id3v2Tag);
        if (StringUtils.isNotBlank(songArtist) && StringUtils.isBlank(songTitleForID3v2Tag)) {
            // 当ID3v2标签中没有歌曲艺术家时，需设置ID3v2标签中的歌曲艺术家信息
            setFieldAndCommit(id3v2Tag, FieldKey.ARTIST, songArtist, getAudioFile());
        }
    }

    @Override
    public void setAlbumName(String albumName) {
        AbstractID3v2Tag id3v2Tag = (AbstractID3v2Tag) DsfUtil.checkID3v2Tag(super.getAudioFile());

        String songTitleForID3v2Tag = Mp3Util.getContentByContentKeyAndMp3FrameNameInID3v2Tag(ID3v2FramesForMP3Enum.TALB.getValue(), "Text", id3v2Tag);
        if (StringUtils.isNotBlank(albumName) && StringUtils.isBlank(songTitleForID3v2Tag)) {
            // 当ID3v2标签中没有歌曲所属的专辑名称时，需设置ID3v2标签中的歌曲所属的专辑名称信息
            setFieldAndCommit(id3v2Tag, FieldKey.ALBUM, albumName, getAudioFile());
        }
    }

    @Override
    public void setAlbumPicture(byte[] albumPictureByteArray, boolean forceMode) {
        AbstractID3v2Tag id3v2Tag = (AbstractID3v2Tag) DsfUtil.checkID3v2Tag(super.getAudioFile());

        if (ArrayUtils.isEmpty(albumPictureByteArray)) {
            // 若第三方在线音乐服务平台中都没有查询到专辑图片，则使用默认专辑图片
            albumPictureByteArray = FileUtil.getDefaultAlbumPictureByteArray();
        }

        // 读取音频文件内的专辑封面图片
        byte[] originalAlbumPictureByteArray = Mp3Util.getContentByContentKeyAndMp3FrameNameInID3v2Tag(ID3v2FramesForMP3Enum.APIC.getValue(), "PictureData", id3v2Tag);

        BufferedImage originalAlbumPicture = null;
        if (ArrayUtils.isNotEmpty(originalAlbumPictureByteArray)) {
            originalAlbumPicture = ArrayUtil.getBufferedImageByPictureByteArray(originalAlbumPictureByteArray);
        }

        if (forceMode || ArrayUtils.isEmpty(originalAlbumPictureByteArray)
                || (null != originalAlbumPicture && originalAlbumPicture.getWidth() * originalAlbumPicture.getHeight() < 500 * 500)) {
            // 先删除专辑封面属性
            id3v2Tag.deleteField(FieldKey.COVER_ART);

            // 再设置新的专辑封面图片
            Artwork artwork = ArtworkFactory.createArtworkFromMetadataBlockDataPicture(AlbumPictureUtil.buildMetadataBlockDataPicture(albumPictureByteArray));
            setFieldAndCommit(id3v2Tag, artwork, getAudioFile());
        }
    }

    @Override
    public void setSongLyrics(String songLyrics) {
        AbstractID3v2Tag id3v2Tag = (AbstractID3v2Tag) DsfUtil.checkID3v2Tag(super.getAudioFile());

        String songTitleForID3v2Tag = Mp3Util.getContentByContentKeyAndMp3FrameNameInID3v2Tag(ID3v2FramesForMP3Enum.USLT.getValue(), "Lyrics", id3v2Tag);
        if (StringUtils.isNotBlank(songLyrics) && StringUtils.isBlank(songTitleForID3v2Tag)) {
            // 当ID3v2标签中没有歌曲内嵌歌词时，需设置ID3v2标签中的歌曲内嵌歌词信息
            setFieldAndCommit(id3v2Tag, FieldKey.LYRICS, songLyrics, getAudioFile());
        }
    }

    @Override
    public void setAlbumArtist(String albumArtist) {
        AbstractID3v2Tag id3v2Tag = (AbstractID3v2Tag) DsfUtil.checkID3v2Tag(super.getAudioFile());

        String songTitleForID3v2Tag = Mp3Util.getContentByContentKeyAndMp3FrameNameInID3v2Tag(ID3v2FramesForMP3Enum.TALB.getValue(), "Text", id3v2Tag);
        if (StringUtils.isNotBlank(albumArtist) && StringUtils.isBlank(songTitleForID3v2Tag)) {
            // 当ID3v2标签中没有歌曲所属专辑的艺术家时，需设置ID3v2标签中的歌曲所属专辑的艺术家信息
            setFieldAndCommit(id3v2Tag, FieldKey.LYRICS, albumArtist, getAudioFile());
        }
    }

    @Override
    public void setAlbumPublishDate(LocalDate albumPublishDate) {
        AbstractID3v2Tag id3v2Tag = (AbstractID3v2Tag) DsfUtil.checkID3v2Tag(super.getAudioFile());

        // ID3v2标签中的发布时间，支持年月日
        List<TagField> tagFieldList = Optional.ofNullable(id3v2Tag.getFields(ID3v2FramesForMP3Enum.TYERTDAT.getValue())).orElse(Lists.newArrayList());

        Optional<AbstractTagFrameBody> publishYearBody, publishMonthAndDayBody;
        String publishYear = null, publishMonthAndDay = null;
        if (CollectionUtils.isNotEmpty(tagFieldList)) {
            publishYearBody = ((TyerTdatAggregatedFrame) tagFieldList.get(0)).getFrames().stream().map(AbstractTagFrame::getBody)
                    .filter(item -> item instanceof FrameBodyTYER)
                    .findFirst();
            publishMonthAndDayBody = ((TyerTdatAggregatedFrame) tagFieldList.get(0)).getFrames().stream().map(AbstractTagFrame::getBody)
                    .filter(item -> item instanceof FrameBodyTDAT)
                    .findFirst();

            publishYear = publishYearBody.get().getObjectValue("Text").toString();
            publishMonthAndDay = publishMonthAndDayBody.get().getObjectValue("Text").toString();
        }

        if (null != albumPublishDate && (StringUtils.isBlank(publishYear) || StringUtils.isBlank(publishMonthAndDay))) {
            // 当ID3v2标签中的歌曲所属专辑的发布年份和月日有任意一项缺失时，需设置ID3v2标签中的歌曲所属专辑的发布时间信息
            setFieldAndCommit(id3v2Tag, FieldKey.YEAR,
                    DateTimeUtil.localDateToString(DateTimeUtil.DatePattern.YYYYMMDD_FORMAT.getValue(), albumPublishDate), getAudioFile());
        }
    }

    @Override
    public void setAlbumDescription(String albumDescription) {
        AbstractID3v2Tag id3v2Tag = (AbstractID3v2Tag) DsfUtil.checkID3v2Tag(super.getAudioFile());

        String albumNameForID3v2Tag = Mp3Util.getContentByContentKeyAndMp3FrameNameInID3v2Tag(ID3v2FramesForMP3Enum.COMM.getValue(), "Text", id3v2Tag);
        if (StringUtils.isNotBlank(albumDescription) && StringUtils.isBlank(albumNameForID3v2Tag)) {
            // 当ID3v2标签中没有歌曲所属专辑的描述时，需设置ID3v2标签中的歌曲所属专辑的描述信息
            setFieldAndCommit(id3v2Tag, FieldKey.COMMENT, albumDescription, getAudioFile());
        }
    }

    @Override
    public void setAlbumLanguage(String albumLanguage) {
        AbstractID3v2Tag id3v2Tag = (AbstractID3v2Tag) DsfUtil.checkID3v2Tag(super.getAudioFile());

        String albumLanguageForID3v2Tag = Mp3Util.getContentByContentKeyAndMp3FrameNameInID3v2Tag(ID3v2FramesForMP3Enum.TLAN.getValue(), "Text", id3v2Tag);
        if (StringUtils.isNotBlank(albumLanguage) && StringUtils.isBlank(albumLanguageForID3v2Tag)) {
            // 当ID3v2标签中没有歌曲所属专辑的语言类型时，需设置ID3v2标签中的歌曲所属专辑的语言类型信息
            setFieldAndCommit(id3v2Tag, FieldKey.LANGUAGE, albumLanguage, getAudioFile());
        }
    }

    @Override
    public void setAlbumCopyright(String albumCopyright) {
        AbstractID3v2Tag id3v2Tag = (AbstractID3v2Tag) DsfUtil.checkID3v2Tag(super.getAudioFile());

        String albumCopyrightForID3v2Tag = Mp3Util.getContentByContentKeyAndMp3FrameNameInID3v2Tag(ID3v2FramesForMP3Enum.TCOP.getValue(), "Text", id3v2Tag);
        if (StringUtils.isNotBlank(albumCopyright) && StringUtils.isBlank(albumCopyrightForID3v2Tag)) {
            // 当ID3v2标签中没有歌曲所属专辑的版权信息时，需设置ID3v2标签中的歌曲所属专辑的版权信息信息
            setFieldAndCommit(id3v2Tag, FieldKey.COPYRIGHT, albumCopyright, getAudioFile());
        }
    }
}