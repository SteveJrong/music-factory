package com.stevejrong.music.factory.provider.service.music.metadata.resolver.query.impl;

import com.google.common.collect.Lists;
import com.stevejrong.music.factory.common.enums.ID3v2FramesForMP3Enum;
import com.stevejrong.music.factory.common.util.DateTimeUtil;
import com.stevejrong.music.factory.common.util.Mp3Util;
import com.stevejrong.music.factory.spi.service.music.metadata.resolver.query.IAudioFileMetadataQueryResolver;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
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
 * MP3音频文件的元数据解析器
 */
public class Mp3MetadataQueryResolver implements IAudioFileMetadataQueryResolver {

    @Override
    public String getSongTitle(AudioFile audioFile) {
        MP3File mp3File = (MP3File) audioFile;

        AbstractID3v2Tag id3v2Tag = mp3File.getID3v2Tag();
        ID3v1Tag id3v1Tag = mp3File.getID3v1Tag();

        // 当存在ID3v1和ID3v2两种标签时，优先将ID3v2作为判断依据
        if (null != id3v2Tag) {
            return Mp3Util.getContentByContentKeyAndMp3FrameNameInID3v2Tag(ID3v2FramesForMP3Enum.TIT2.getValue(),
                    "Text", id3v2Tag);
        } else if (null != id3v1Tag) {
            return id3v1Tag.getFirst(FieldKey.TITLE);
        }

        return null;
    }

    @Override
    public String getSongArtist(AudioFile audioFile) {
        MP3File mp3File = (MP3File) audioFile;

        AbstractID3v2Tag id3v2Tag = mp3File.getID3v2Tag();
        ID3v1Tag id3v1Tag = mp3File.getID3v1Tag();

        // 当存在ID3v1和ID3v2两种标签时，优先将ID3v2作为判断依据
        if (null != id3v2Tag) {
            return Mp3Util.getContentByContentKeyAndMp3FrameNameInID3v2Tag(ID3v2FramesForMP3Enum.TPE1.getValue(),
                    "Text", id3v2Tag);
        } else if (null != id3v1Tag) {
            return id3v1Tag.getFirst(FieldKey.TITLE);
        }

        return null;
    }

    @Override
    public String getSongLyrics(AudioFile audioFile) {
        MP3File mp3File = (MP3File) audioFile;

        AbstractID3v2Tag id3v2Tag = mp3File.getID3v2Tag();

        // 当存在ID3v1和ID3v2两种标签时，优先将ID3v2作为判断依据
        if (null != id3v2Tag) {
            return Mp3Util.getContentByContentKeyAndMp3FrameNameInID3v2Tag(ID3v2FramesForMP3Enum.USLT.getValue(),
                    "Description", id3v2Tag);
        }
        // ID3v1标签不支持歌词，返回null
        return null;
    }

    @Override
    public String getAlbumName(AudioFile audioFile) {
        MP3File mp3File = (MP3File) audioFile;

        AbstractID3v2Tag id3v2Tag = mp3File.getID3v2Tag();
        ID3v1Tag id3v1Tag = mp3File.getID3v1Tag();

        // 当存在ID3v1和ID3v2两种标签时，优先将ID3v2作为判断依据
        if (null != id3v2Tag) {
            return Mp3Util.getContentByContentKeyAndMp3FrameNameInID3v2Tag(ID3v2FramesForMP3Enum.TALB.getValue(),
                    "Text", id3v2Tag);
        } else if (null != id3v1Tag) {
            return id3v1Tag.getFirst(FieldKey.ALBUM);
        }

        return null;
    }

    @Override
    public String getAlbumArtist(AudioFile audioFile) {
        MP3File mp3File = (MP3File) audioFile;

        AbstractID3v2Tag id3v2Tag = mp3File.getID3v2Tag();
        ID3v1Tag id3v1Tag = mp3File.getID3v1Tag();

        // 当存在ID3v1和ID3v2两种标签时，优先将ID3v2作为判断依据
        if (null != id3v2Tag) {
            return Mp3Util.getContentByContentKeyAndMp3FrameNameInID3v2Tag(ID3v2FramesForMP3Enum.TPE2.getValue(),
                    "Text", id3v2Tag);
        } else if (null != id3v1Tag) {
            return id3v1Tag.getFirst(FieldKey.ALBUM_ARTIST);
        }

        return null;
    }

    @Override
    public LocalDate getAlbumPublishDate(AudioFile audioFile) {
        MP3File mp3File = (MP3File) audioFile;

        // 当存在ID3v1和ID3v2两种标签时，优先将ID3v2作为判断依据
        AbstractID3v2Tag id3v2Tag = mp3File.getID3v2Tag();
        ID3v1Tag id3v1Tag = mp3File.getID3v1Tag();

        if (null != id3v2Tag) {
            // ID3v2标签中的发布时间，支持年月日

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
        } else if (null != id3v1Tag
                && CollectionUtils.isNotEmpty(id3v1Tag.getFields(FieldKey.YEAR))
                && StringUtils.isNotBlank(id3v1Tag.getFirstField(FieldKey.YEAR).toString())) {

            if (DateTimeUtil.DATE_PATTERN_OF_YYYY_FORMAT.matcher(id3v1Tag.getFirstField(FieldKey.YEAR).toString()).matches()) {

                // 当在ID3v1标签中存在4位年份的数据时，就默认此信息正确，直接返回当前时间来认为此音频文件ID3v1标签中的发布时间信息没有缺失
                return DateTimeUtil.getNowDate();
            }
        }

        return null;
    }

    @Override
    public String getAlbumDescription(AudioFile audioFile) {
        MP3File mp3File = (MP3File) audioFile;

        // 当存在ID3v1和ID3v2两种标签时，优先将ID3v2作为判断依据
        AbstractID3v2Tag id3v2Tag = mp3File.getID3v2Tag();
        ID3v1Tag id3v1Tag = mp3File.getID3v1Tag();

        if (null != id3v2Tag) {
            return Mp3Util.getContentByContentKeyAndMp3FrameNameInID3v2Tag(ID3v2FramesForMP3Enum.COMM.getValue(),
                    "Description", id3v2Tag);
        } else if (null != id3v1Tag) {
            return id3v1Tag.getFirst(FieldKey.COMMENT);
        }

        return null;
    }

    @Override
    public String getAlbumLanguage(AudioFile audioFile) {
        MP3File mp3File = (MP3File) audioFile;

        // 当存在ID3v1和ID3v2两种标签时，优先将ID3v2作为判断依据
        AbstractID3v2Tag id3v2Tag = mp3File.getID3v2Tag();

        if (null != id3v2Tag) {
            return Mp3Util.getContentByContentKeyAndMp3FrameNameInID3v2Tag(ID3v2FramesForMP3Enum.TLAN.getValue(),
                    "Text", id3v2Tag);
        }

        // ID3v1标签不支持语言类型，返回null
        return null;
    }

    @Override
    public String getAlbumCopyright(AudioFile audioFile) {
        MP3File mp3File = (MP3File) audioFile;

        // 当存在ID3v1和ID3v2两种标签时，优先将ID3v2作为判断依据
        AbstractID3v2Tag id3v2Tag = mp3File.getID3v2Tag();

        if (null != id3v2Tag) {
            return Mp3Util.getContentByContentKeyAndMp3FrameNameInID3v2Tag(ID3v2FramesForMP3Enum.TCOP.getValue(),
                    "Text", id3v2Tag);
        }

        // ID3v1标签不支持版权信息，返回null
        return null;
    }

    @Override
    public byte[] getAlbumPicture(AudioFile audioFile) {
        MP3File mp3File = (MP3File) audioFile;

        AbstractID3v2Tag id3v2Tag = mp3File.getID3v2Tag();
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
