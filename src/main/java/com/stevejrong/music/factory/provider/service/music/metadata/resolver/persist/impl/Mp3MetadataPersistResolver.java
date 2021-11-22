package com.stevejrong.music.factory.provider.service.music.metadata.resolver.persist.impl;

import com.stevejrong.music.factory.common.enums.ID3v2FramesForMP3Enum;
import com.stevejrong.music.factory.common.util.AlbumCoverUtil;
import com.stevejrong.music.factory.common.util.Mp3Util;
import com.stevejrong.music.factory.spi.service.music.metadata.resolver.persist.IAudioFileMetadataPersistResolver;
import org.apache.commons.lang3.StringUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldDataInvalidException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.AbstractID3v2Frame;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.ID3v1Tag;
import org.jaudiotagger.tag.id3.framebody.FrameBodyAPIC;
import org.jaudiotagger.tag.images.Artwork;
import org.jaudiotagger.tag.images.ArtworkFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * MP3音频文件的元数据存储器
 */
public class Mp3MetadataPersistResolver implements IAudioFileMetadataPersistResolver {

    @Override
    public void setSongTitle(AudioFile audioFile, Tag tag, String songTitle) {
        MP3File mp3File = (MP3File) audioFile;

        AbstractID3v2Tag id3v2Tag = mp3File.getID3v2Tag();
        ID3v1Tag id3v1Tag = mp3File.getID3v1Tag();

        if ((null != id3v2Tag
                && StringUtils.isBlank(Mp3Util.getContentByContentKeyAndMp3FrameNameInID3v2Tag(ID3v2FramesForMP3Enum.TIT2.getValue(), "Text", id3v2Tag)))
                || (null != id3v1Tag && StringUtils.isBlank(id3v1Tag.getFirst(FieldKey.TITLE))))
            // 当ID3v2或ID3v1标签中没有歌曲名称时，需设置歌曲名称信息
            try {
                tag.setField(FieldKey.TITLE, songTitle);
            } catch (FieldDataInvalidException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void setSongArtist(AudioFile audioFile, Tag tag, String songArtist) {
        MP3File mp3File = (MP3File) audioFile;

        AbstractID3v2Tag id3v2Tag = mp3File.getID3v2Tag();
        ID3v1Tag id3v1Tag = mp3File.getID3v1Tag();

        if ((null != id3v2Tag
                && StringUtils.isBlank(Mp3Util.getContentByContentKeyAndMp3FrameNameInID3v2Tag(ID3v2FramesForMP3Enum.TPE1.getValue(), "Text", id3v2Tag)))
                || (null != id3v1Tag && StringUtils.isBlank(id3v1Tag.getFirst(FieldKey.ARTIST))))
            // 当ID3v2或ID3v1标签中没有歌曲艺术家时，需设置歌曲名称信息
            try {
                tag.setField(FieldKey.ARTIST, songArtist);
            } catch (FieldDataInvalidException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void setAlbumName(AudioFile audioFile, Tag tag, String albumName) {
        MP3File mp3File = (MP3File) audioFile;

        AbstractID3v2Tag id3v2Tag = mp3File.getID3v2Tag();
        ID3v1Tag id3v1Tag = mp3File.getID3v1Tag();

        if ((null != id3v2Tag
                && StringUtils.isBlank(Mp3Util.getContentByContentKeyAndMp3FrameNameInID3v2Tag(ID3v2FramesForMP3Enum.TALB.getValue(), "Text", id3v2Tag)))
                || (null != id3v1Tag && StringUtils.isBlank(id3v1Tag.getFirst(FieldKey.ALBUM))))
            // 当ID3v2或ID3v1标签中没有歌曲所属的专辑名称时，需设置歌曲名称信息
            try {
                tag.setField(FieldKey.ALBUM, albumName);
            } catch (FieldDataInvalidException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void setAlbumPicture(AudioFile audioFile, Tag tag, byte[] albumPictureByteArray) {
        AbstractID3v2Tag id3v2Tag = ((MP3File) audioFile).getID3v2Tag();
        AbstractID3v2Frame frame = null;

        if (id3v2Tag.getFrame("APIC") instanceof ArrayList) {
            frame = (AbstractID3v2Frame) ((List) id3v2Tag.getFrame("APIC")).get(0);
        } else if (id3v2Tag.getFrame("APIC") instanceof AbstractID3v2Frame) {
            frame = (AbstractID3v2Frame) id3v2Tag.getFrame("APIC");
        }

        if (null == frame || null == frame.getBody() || null == ((FrameBodyAPIC) frame.getBody()).getImageData()
                || ((FrameBodyAPIC) frame.getBody()).getImageData().length <= 0) {
            // 音频文件元数据中没有专辑图片，则将其设置进去
            try {
                tag.deleteField(FieldKey.COVER_ART);
            } catch (Exception e) {
                e.getStackTrace();
            }

            Artwork artwork = ArtworkFactory.createArtworkFromMetadataBlockDataPicture(AlbumCoverUtil.buildMetadataBlockDataPicture(albumPictureByteArray));
            setFieldAndCommit(tag, artwork, audioFile);
        }
    }

    @Override
    public void persistMetadata(AudioFile audioFile, Tag tag) {
        ((MP3File) audioFile).setID3v2Tag((AbstractID3v2Tag) tag);
        try {
            ((MP3File) audioFile).save();
        } catch (IOException | TagException e) {
            e.printStackTrace();
        }
    }
}
