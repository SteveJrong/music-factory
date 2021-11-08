package com.stevejrong.music.factory.analysis.metadata.query.resolver.impl;

import com.stevejrong.music.factory.analysis.metadata.query.resolver.IFileMetadataQueryResolver;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.id3.AbstractID3v2Frame;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.ID3v1Tag;
import org.jaudiotagger.tag.id3.framebody.FrameBodyAPIC;

import java.util.ArrayList;
import java.util.List;

/**
 * MP3音频文件的元数据解析器
 */
public class Mp3MetadataQueryResolver implements IFileMetadataQueryResolver {

    @Override
    public String getSongName(AudioFile audioFile) {
        MP3File mp3File = (MP3File) audioFile;

        ID3v1Tag id3v1Tag = mp3File.getID3v1Tag();
        AbstractID3v2Tag id3v2Tag = mp3File.getID3v2Tag();

        if (null != id3v2Tag) {
            return null != id3v2Tag.frameMap.get("TIT2")
                    ? id3v2Tag.frameMap.get("TIT2").toString()
                    .replace("Text=", "")
                    .replace("\"", "")
                    .replace(";", "").trim()
                    : null;
        } else if (null != id3v1Tag) {
            return id3v1Tag.getFirstTitle();
        }

        return null;
    }

    @Override
    public String getSingerName(AudioFile audioFile) {
        MP3File mp3File = (MP3File) audioFile;

        ID3v1Tag id3v1Tag = mp3File.getID3v1Tag();
        AbstractID3v2Tag id3v2Tag = mp3File.getID3v2Tag();

        if (null != id3v2Tag) {
            return null != id3v2Tag.frameMap.get("TPE1")
                    ? id3v2Tag.frameMap.get("TPE1").toString()
                    .replace("Text=", "")
                    .replace("\"", "")
                    .replace(";", "").trim()
                    : null;
        } else if (null != id3v1Tag) {
            return id3v1Tag.getFirstArtist();
        }

        return null;
    }

    @Override
    public String getAlbumName(AudioFile audioFile) {
        MP3File mp3File = (MP3File) audioFile;

        ID3v1Tag id3v1Tag = mp3File.getID3v1Tag();
        AbstractID3v2Tag id3v2Tag = mp3File.getID3v2Tag();

        if (null != id3v2Tag) {
            return null != id3v2Tag.frameMap.get("TALB")
                    ? id3v2Tag.frameMap.get("TALB").toString()
                    .replace("Text=", "")
                    .replace("\"", "")
                    .replace(";", "").trim()
                    : null;
        } else if (null != id3v1Tag) {
            return id3v1Tag.getFirstAlbum();
        }

        return null;
    }

    @Override
    public byte[] getAlbumPic(AudioFile audioFile) {
        MP3File mp3File = (MP3File) audioFile;
        AbstractID3v2Tag id3v2Tag = mp3File.getID3v2Tag();

        if (null == id3v2Tag) {
            return null;
        }

        AbstractID3v2Frame frame;
        if (id3v2Tag.getFrame("APIC") instanceof ArrayList) {
            frame = (AbstractID3v2Frame) ((List) id3v2Tag.getFrame("APIC")).get(0);
        } else {
            frame = (AbstractID3v2Frame) id3v2Tag.getFrame("APIC");
        }

        if (null != frame && null != frame.getBody() && null != ((FrameBodyAPIC) frame.getBody()).getImageData()
                && ((FrameBodyAPIC) frame.getBody()).getImageData().length > 0) {
            return ((FrameBodyAPIC) frame.getBody()).getImageData();
        }

        return null;
    }
}
