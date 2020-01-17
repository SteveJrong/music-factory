package com.stevejrong.music.factory.analysis.metadata.query.resolver.impl;

import com.stevejrong.music.factory.analysis.metadata.query.resolver.IFileMetadataQueryResolver;
import org.apache.commons.lang3.StringUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagField;
import org.jaudiotagger.tag.flac.FlacTag;

/**
 * Flac音频文件的元数据解析器
 */
public class FlacMetadataQueryResolver implements IFileMetadataQueryResolver {

    @Override
    public String getSongName(AudioFile audioFile) {
        FlacTag flacTag = (FlacTag) audioFile.getTag();

        TagField tagField = flacTag.getFirstField(FieldKey.TITLE);
        if (null != tagField && StringUtils.isNotEmpty(tagField.toString()) && StringUtils.isNotBlank(tagField.toString())) {
            return tagField.toString();
        }
        return null;
    }

    @Override
    public String getSingerName(AudioFile audioFile) {
        FlacTag flacTag = (FlacTag) audioFile.getTag();

        TagField tagField = flacTag.getFirstField(FieldKey.ARTIST);
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
    public byte[] getAlbumPic(AudioFile audioFile) {
        FlacTag flacTag = (FlacTag) audioFile.getTag();

        if (null != flacTag.getFirstArtwork() && flacTag.getFirstArtwork().getBinaryData().length > 0) {
            return flacTag.getFirstArtwork().getBinaryData();
        }

        return null;
    }
}
