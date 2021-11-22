package com.stevejrong.music.factory.provider.service.music.metadata.resolver.query.impl;

import com.stevejrong.music.factory.spi.service.music.metadata.resolver.query.IAudioFileMetadataQueryResolver;
import org.apache.commons.lang3.StringUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagField;
import org.jaudiotagger.tag.flac.FlacTag;

import java.time.LocalDate;

/**
 * Flac音频文件的元数据解析器
 */
public class FlacMetadataQueryResolver implements IAudioFileMetadataQueryResolver {

    @Override
    public String getSongTitle(AudioFile audioFile) {
        FlacTag flacTag = (FlacTag) audioFile.getTag();

        TagField tagField = flacTag.getFirstField(FieldKey.TITLE);
        if (null != tagField && StringUtils.isNotEmpty(tagField.toString()) && StringUtils.isNotBlank(tagField.toString())) {
            return tagField.toString();
        }

        return null;
    }

    @Override
    public String getSongArtist(AudioFile audioFile) {
        FlacTag flacTag = (FlacTag) audioFile.getTag();

        TagField tagField = flacTag.getFirstField(FieldKey.ARTIST);
        if (null != tagField && StringUtils.isNotEmpty(tagField.toString()) && StringUtils.isNotBlank(tagField.toString())) {
            return tagField.toString();
        }

        return null;
    }

    @Override
    public String getSongLyrics(AudioFile audioFile) {
        FlacTag flacTag = (FlacTag) audioFile.getTag();

        TagField tagField = flacTag.getFirstField(FieldKey.LYRICS);
        if (null != tagField && StringUtils.isNotEmpty(tagField.toString()) && StringUtils.isNotBlank(tagField.toString())) {
            return tagField.toString();
        }

        return null;
    }

    @Override
    public String getAlbumName(AudioFile audioFile) {
        FlacTag flacTag = (FlacTag) audioFile.getTag();

        TagField tagField = flacTag.getFirstField(FieldKey.ALBUM);
        if (null != tagField && StringUtils.isNotEmpty(tagField.toString()) && StringUtils.isNotBlank(tagField.toString())) {
            return tagField.toString();
        }

        return null;
    }

    @Override
    public String getAlbumArtist(AudioFile audioFile) {
        FlacTag flacTag = (FlacTag) audioFile.getTag();

        TagField tagField = flacTag.getFirstField(FieldKey.ALBUM_ARTIST);
        if (null != tagField && StringUtils.isNotEmpty(tagField.toString()) && StringUtils.isNotBlank(tagField.toString())) {
            return tagField.toString();
        }

        return null;
    }

    @Override
    public LocalDate getAlbumPublishDate(AudioFile audioFile) {
        FlacTag flacTag = (FlacTag) audioFile.getTag();

        TagField tagField = flacTag.getFirstField(FieldKey.YEAR);
        if (null != tagField && StringUtils.isNotEmpty(tagField.toString()) && StringUtils.isNotBlank(tagField.toString())) {
            //return DateTimeUtil.stringToLocalDate(tagField.toString());
            return null;
        }

        return null;
    }

    @Override
    public String getAlbumDescription(AudioFile audioFile) {
        FlacTag flacTag = (FlacTag) audioFile.getTag();

        TagField tagField = flacTag.getFirstField(FieldKey.COMMENT);
        if (null != tagField && StringUtils.isNotEmpty(tagField.toString()) && StringUtils.isNotBlank(tagField.toString())) {
            return tagField.toString();
        }

        return null;
    }

    @Override
    public String getAlbumLanguage(AudioFile audioFile) {
        FlacTag flacTag = (FlacTag) audioFile.getTag();

        TagField tagField = flacTag.getFirstField(FieldKey.LANGUAGE);
        if (null != tagField && StringUtils.isNotEmpty(tagField.toString()) && StringUtils.isNotBlank(tagField.toString())) {
            return tagField.toString();
        }

        return null;
    }

    @Override
    public String getAlbumCopyright(AudioFile audioFile) {
        FlacTag flacTag = (FlacTag) audioFile.getTag();

        TagField tagField = flacTag.getFirstField(FieldKey.PRODUCER);
        if (null != tagField && StringUtils.isNotEmpty(tagField.toString()) && StringUtils.isNotBlank(tagField.toString())) {
            return tagField.toString();
        }

        return null;
    }

    @Override
    public byte[] getAlbumPicture(AudioFile audioFile) {
        FlacTag flacTag = (FlacTag) audioFile.getTag();

        if (null != flacTag.getFirstArtwork() && flacTag.getFirstArtwork().getBinaryData().length > 0) {
            return flacTag.getFirstArtwork().getBinaryData();
        }

        return null;
    }
}
