package com.stevejrong.music.factory.provider.service.music.metadata.resolver.persist.impl;

import com.stevejrong.music.factory.spi.service.music.metadata.resolver.persist.IAudioFileMetadataPersistResolver;
import org.apache.commons.lang3.StringUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.tag.FieldDataInvalidException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagField;
import org.jaudiotagger.tag.flac.FlacTag;
import org.jaudiotagger.tag.images.Artwork;

/**
 * Flac音频文件的元数据存储器
 */
public class FlacMetadataPersistResolver implements IAudioFileMetadataPersistResolver {

    @Override
    public void setSongTitle(AudioFile audioFile, Tag tag, String songName) {
        FlacTag flacTag = (FlacTag) audioFile.getTag();

        TagField tagField = flacTag.getFirstField(FieldKey.TITLE);
        if (null == tagField || StringUtils.isEmpty(tagField.toString()) || StringUtils.isBlank(tagField.toString())) {
            // 音频文件元数据中没有歌曲名称，则将其设置进去
            try {
                tag.setField(FieldKey.TITLE, songName);
            } catch (FieldDataInvalidException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setSongArtist(AudioFile audioFile, Tag tag, String singerName) {
        FlacTag flacTag = (FlacTag) audioFile.getTag();

        TagField tagField = flacTag.getFirstField(FieldKey.ARTIST);
        if (null == tagField || StringUtils.isEmpty(tagField.toString()) || StringUtils.isBlank(tagField.toString())) {
            // 音频文件元数据中没有演唱者，则将其设置进去
            try {
                tag.setField(FieldKey.ARTIST, singerName);
            } catch (FieldDataInvalidException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setAlbumName(AudioFile audioFile, Tag tag, String albumName) {
        FlacTag flacTag = (FlacTag) audioFile.getTag();

        TagField tagField = flacTag.getFirstField(FieldKey.ALBUM);
        if (null == tagField || StringUtils.isEmpty(tagField.toString()) || StringUtils.isBlank(tagField.toString())) {
            // 音频文件元数据中没有专辑名称，则将其设置进去
            try {
                tag.setField(FieldKey.ALBUM, albumName);
            } catch (FieldDataInvalidException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setAlbumPicture(AudioFile audioFile, Tag tag, byte[] albumPicByteArray) {
        FlacTag flacTag = (FlacTag) audioFile.getTag();

        if (null == flacTag.getFirstArtwork() || null == flacTag.getFirstArtwork().getBinaryData()
                || flacTag.getFirstArtwork().getBinaryData().length <= 0) {
            // 音频文件元数据中没有专辑图片，则将其设置进去
            tag.deleteArtworkField();

            Artwork artwork = null;
            /*try {
                artwork = ArtworkFactory.createArtworkFromFile(FileUtil.byteArrayToFile(albumPicByteArray,
                        BaseConstants.ARTWORK_TEMP_DIRECTORY + File.separator
                                + StringUtil.replaceSpecialCharOfDirectoryByWindows(songName) + "-"
                                + StringUtil.replaceSpecialCharOfDirectoryByWindows(singerName) + ".jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }*/

            try {
                tag.setField(artwork);
                tag.addField(artwork);
            } catch (FieldDataInvalidException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void persistMetadata(AudioFile audioFile, Tag tag) {
        audioFile.setTag(tag);
        try {
            audioFile.commit();
        } catch (CannotWriteException e) {
            e.printStackTrace();
        }
    }
}
