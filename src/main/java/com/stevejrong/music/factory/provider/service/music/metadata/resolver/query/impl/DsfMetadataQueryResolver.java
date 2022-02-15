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
package com.stevejrong.music.factory.provider.service.music.metadata.resolver.query.impl;

import com.google.common.collect.Lists;
import com.stevejrong.music.factory.common.enums.ID3v2FramesForMP3Enum;
import com.stevejrong.music.factory.common.util.DateTimeUtil;
import com.stevejrong.music.factory.common.util.Mp3Util;
import com.stevejrong.music.factory.spi.service.music.metadata.resolver.query.AbstractAudioFileMetadataQueryResolver;
import com.stevejrong.music.factory.spi.service.music.metadata.resolver.query.IAudioFileMetadataQueryResolver;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.tag.TagField;
import org.jaudiotagger.tag.id3.*;
import org.jaudiotagger.tag.id3.framebody.FrameBodyAPIC;
import org.jaudiotagger.tag.id3.framebody.FrameBodyTDAT;
import org.jaudiotagger.tag.id3.framebody.FrameBodyTYER;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Dsf（索尼专有格式）音频文件的元数据解析器
 *
 * @author Steve Jrong
 * @since 1.0
 */
public class DsfMetadataQueryResolver extends AbstractAudioFileMetadataQueryResolver implements IAudioFileMetadataQueryResolver {

    @Override
    public void setAudioFile(AudioFile audioFile) {
        super.audioFile = audioFile;
    }

    @Override
    public String getSongTitle() {
        AbstractID3v2Tag id3v2Tag = (AbstractID3v2Tag) super.audioFile.getTag();

        if (null != id3v2Tag) {
            return Mp3Util.getContentByContentKeyAndMp3FrameNameInID3v2Tag(ID3v2FramesForMP3Enum.TIT2.getValue(),
                    "Text", id3v2Tag);
        }

        return null;
    }

    @Override
    public String getSongArtist() {
        AbstractID3v2Tag id3v2Tag = (AbstractID3v2Tag) super.audioFile.getTag();

        if (null != id3v2Tag) {
            return Mp3Util.getContentByContentKeyAndMp3FrameNameInID3v2Tag(ID3v2FramesForMP3Enum.TPE1.getValue(),
                    "Text", id3v2Tag);
        }

        return null;
    }

    @Override
    public String getSongLyrics() {
        AbstractID3v2Tag id3v2Tag = (AbstractID3v2Tag) super.audioFile.getTag();

        if (null != id3v2Tag) {
            return Mp3Util.getContentByContentKeyAndMp3FrameNameInID3v2Tag(ID3v2FramesForMP3Enum.USLT.getValue(),
                    "Lyrics", id3v2Tag);
        }

        return null;
    }

    @Override
    public String getAlbumName() {
        AbstractID3v2Tag id3v2Tag = (AbstractID3v2Tag) super.audioFile.getTag();

        if (null != id3v2Tag) {
            return Mp3Util.getContentByContentKeyAndMp3FrameNameInID3v2Tag(ID3v2FramesForMP3Enum.TALB.getValue(),
                    "Text", id3v2Tag);
        }

        return null;
    }

    @Override
    public String getAlbumArtist() {
        AbstractID3v2Tag id3v2Tag = (AbstractID3v2Tag) super.audioFile.getTag();

        if (null != id3v2Tag) {
            return Mp3Util.getContentByContentKeyAndMp3FrameNameInID3v2Tag(ID3v2FramesForMP3Enum.TPE1.getValue(),
                    "Text", id3v2Tag);
        }

        return null;
    }

    @Override
    public LocalDate getAlbumPublishDate() {
        AbstractID3v2Tag id3v2Tag = (AbstractID3v2Tag) super.audioFile.getTag();

        if (null != id3v2Tag) {
            List<TagField> tagFieldList = Optional.ofNullable(
                    id3v2Tag.getFields(ID3v2FramesForMP3Enum.TYERTDAT.getValue())).orElse(Lists.newArrayList());
            TyerTdatAggregatedFrame body = null;
            if (CollectionUtils.isNotEmpty(tagFieldList)) {
                body = (TyerTdatAggregatedFrame) id3v2Tag.getFields(ID3v2FramesForMP3Enum.TYERTDAT.getValue()).get(0);
            }

            if (null != body) {
                Optional<AbstractTagFrameBody> publishYearBody = body.getFrames().stream().map(AbstractTagFrame::getBody)
                        .filter(item -> item instanceof FrameBodyTYER)
                        .findFirst();
                Optional<AbstractTagFrameBody> publishMonthAndDayBody = body.getFrames().stream().map(AbstractTagFrame::getBody)
                        .filter(item -> item instanceof FrameBodyTDAT)
                        .findFirst();

                String albumPublishDate = publishYearBody.get().getObjectValue("Text").toString()
                        + publishMonthAndDayBody.get().getObjectValue("Text").toString();

                if (DateTimeUtil.DATE_PATTERN_OF_YYYYMMDD_FORMAT.matcher(albumPublishDate).matches()) {

                    return DateTimeUtil.stringToLocalDate(DateTimeUtil.DatePattern.YYYYMMDD_FORMAT.getValue(),
                            publishYearBody.get().getObjectValue("Text").toString()
                                    + publishMonthAndDayBody.get().getObjectValue("Text").toString());
                } else if (DateTimeUtil.DATE_PATTERN_OF_YYYYMMDD_FORMAT_WITHOUT_SYMBOL.matcher(albumPublishDate).matches()) {

                    return DateTimeUtil.stringToLocalDate(DateTimeUtil.DatePattern.YYYYMMDD_FORMAT_WITHOUT_SYMBOL.getValue(),
                            publishYearBody.get().getObjectValue("Text").toString()
                                    + publishMonthAndDayBody.get().getObjectValue("Text").toString());
                }
            }
        }

        return null;
    }

    @Override
    public String getAlbumDescription() {
        AbstractID3v2Tag id3v2Tag = (AbstractID3v2Tag) super.audioFile.getTag();

        if (null != id3v2Tag) {
            return Mp3Util.getContentByContentKeyAndMp3FrameNameInID3v2Tag(ID3v2FramesForMP3Enum.COMM.getValue(),
                    "Text", id3v2Tag);
        }

        return null;
    }

    @Override
    public String getAlbumLanguage() {
        AbstractID3v2Tag id3v2Tag = (AbstractID3v2Tag) super.audioFile.getTag();

        if (null != id3v2Tag) {
            return Mp3Util.getContentByContentKeyAndMp3FrameNameInID3v2Tag(ID3v2FramesForMP3Enum.TLAN.getValue(),
                    "Text", id3v2Tag);
        }

        return null;
    }

    @Override
    public String getAlbumCopyright() {
        AbstractID3v2Tag id3v2Tag = (AbstractID3v2Tag) super.audioFile.getTag();

        if (null != id3v2Tag) {
            return Mp3Util.getContentByContentKeyAndMp3FrameNameInID3v2Tag(ID3v2FramesForMP3Enum.TCOP.getValue(),
                    "Text", id3v2Tag);
        }

        return null;
    }

    @Override
    public byte[] getAlbumPicture(boolean sizeLimit) {
        AbstractID3v2Tag id3v2Tag = (AbstractID3v2Tag) super.audioFile.getTag();

        AbstractID3v2Frame frame = null;
        if (null != id3v2Tag) {
            if (id3v2Tag.getFrame(ID3v2FramesForMP3Enum.APIC.getValue()) instanceof ArrayList) {
                frame = (AbstractID3v2Frame) ((List) id3v2Tag.getFrame(ID3v2FramesForMP3Enum.APIC.getValue())).get(0);
            } else {
                frame = (AbstractID3v2Frame) id3v2Tag.getFrame(ID3v2FramesForMP3Enum.APIC.getValue());
            }
        }

        if (null != frame && null != frame.getBody() && ArrayUtils.isNotEmpty(((FrameBodyAPIC) frame.getBody()).getImageData())) {
            return ((FrameBodyAPIC) frame.getBody()).getImageData();
        }

        // ID3v1标签不支持专辑封面，返回null
        return null;
    }
}