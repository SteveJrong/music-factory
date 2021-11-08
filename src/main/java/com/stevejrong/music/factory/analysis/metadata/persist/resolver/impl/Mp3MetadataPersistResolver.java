package com.stevejrong.music.factory.analysis.metadata.persist.resolver.impl;

import com.stevejrong.music.factory.analysis.metadata.persist.resolver.IFileMetadataPersistResolver;
import com.stevejrong.music.factory.common.constants.BaseConstants;
import com.stevejrong.music.factory.util.FileUtil;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * MP3音频文件的元数据存储器
 */
public class Mp3MetadataPersistResolver implements IFileMetadataPersistResolver {

    @Override
    public void setSongName(AudioFile audioFile, Tag tag, String songName) {
        ID3v1Tag id3v1Tag = ((MP3File) audioFile).getID3v1Tag();
        AbstractID3v2Tag id3v2Tag = ((MP3File) audioFile).getID3v2Tag();

        if ((null == id3v1Tag || null == id3v2Tag)
                || (null != id3v2Tag.frameMap
                && id3v2Tag.frameMap.size() > 0
                && null != id3v2Tag.frameMap.get("TIT2")
                && StringUtils.isBlank(id3v2Tag.frameMap.get("TIT2").toString()))
                || (StringUtils.isBlank(id3v1Tag.getFirstTitle()))
        ) {
            // 音频文件元数据中没有歌曲名称，则将其设置进去
            try {
                tag.setField(FieldKey.TITLE, songName);
            } catch (FieldDataInvalidException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setSingerName(AudioFile audioFile, Tag tag, String singerName) {
        ID3v1Tag id3v1Tag = ((MP3File) audioFile).getID3v1Tag();
        AbstractID3v2Tag id3v2Tag = ((MP3File) audioFile).getID3v2Tag();

        if ((null == id3v1Tag || null == id3v2Tag)
                || (null != id3v2Tag.frameMap
                && id3v2Tag.frameMap.size() > 0
                && null != id3v2Tag.frameMap.get("TPE1")
                && StringUtils.isBlank(id3v2Tag.frameMap.get("TPE1").toString()))
                || (StringUtils.isBlank(id3v1Tag.getFirstArtist()))
        ) {
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
        ID3v1Tag id3v1Tag = ((MP3File) audioFile).getID3v1Tag();
        AbstractID3v2Tag id3v2Tag = ((MP3File) audioFile).getID3v2Tag();

        if ((null == id3v1Tag || null == id3v2Tag)
                || (null != id3v2Tag.frameMap
                && id3v2Tag.frameMap.size() > 0
                && null != id3v2Tag.frameMap.get("TALB")
                && StringUtils.isBlank(id3v2Tag.frameMap.get("TALB").toString()))
                || (StringUtils.isBlank(id3v1Tag.getFirstAlbum()))
        ) {
            // 音频文件元数据中没有专辑名称，则将其设置进去
            try {
                tag.setField(FieldKey.ALBUM, albumName);
            } catch (FieldDataInvalidException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setAlbumPic(AudioFile audioFile, Tag tag, byte[] albumPicByteArray, String songName, String singerName) {
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
                tag.deleteArtworkField();
            } catch (Exception e) {
                e.getStackTrace();
            }

            Artwork artwork = null;
            try {
                artwork = ArtworkFactory.createArtworkFromFile(FileUtil.byteArrayToFile(albumPicByteArray,
                        BaseConstants.ARTWORK_TEMP_DIRECTORY + File.separator + songName + "-" + singerName + ".jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }

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
        ((MP3File) audioFile).setID3v2Tag((AbstractID3v2Tag) tag);
        try {
            ((MP3File) audioFile).save();
        } catch (IOException | TagException e) {
            e.printStackTrace();
        }
    }
}
