package com.stevejrong.music.factory.analysis.metadata.query.resolver.impl;

import com.stevejrong.music.factory.analysis.metadata.query.resolver.IFileMetadataQueryResolver;
import org.apache.commons.lang3.StringUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.id3.AbstractID3v2Frame;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.framebody.FrameBodyAPIC;

import java.util.ArrayList;
import java.util.Map;

/**
 * MP3音频文件的元数据解析器
 */
public class Mp3MetadataQueryResolver implements IFileMetadataQueryResolver {

    @Override
    public String getSongName(AudioFile audioFile) {
        MP3File mp3File = (MP3File) audioFile;

        Map frameMap = mp3File.getID3v2Tag().frameMap;
        if (null != frameMap && frameMap.size() > 0 && null != frameMap.get("TIT2")
                && StringUtils.isNotEmpty(frameMap.get("TIT2").toString()) && StringUtils.isNotBlank(frameMap.get("TIT2").toString())) {
            return frameMap.get("TIT2").toString().replace("Text=", "")
                    .replace("\"", "")
                    .replace(";", "").trim();
        }

        return null;
    }

    @Override
    public String getSingerName(AudioFile audioFile) {
        MP3File mp3File = (MP3File) audioFile;

        Map frameMap = mp3File.getID3v2Tag().frameMap;
        if (null != frameMap && frameMap.size() > 0 && null != frameMap.get("TPE1")
                && StringUtils.isNotEmpty(frameMap.get("TPE1").toString()) && StringUtils.isNotBlank(frameMap.get("TPE1").toString())) {
            return frameMap.get("TPE1").toString().replace("Text=", "")
                    .replace("\"", "")
                    .replace(";", "").trim();
        }

        return null;
    }

    @Override
    public String getAlbumName(AudioFile audioFile) {
        MP3File mp3File = (MP3File) audioFile;

        Map frameMap = mp3File.getID3v2Tag().frameMap;
        if (null != frameMap && frameMap.size() > 0 && null != frameMap.get("TALB")
                && StringUtils.isNotEmpty(frameMap.get("TALB").toString()) && StringUtils.isNotBlank(frameMap.get("TALB").toString())) {
            return frameMap.get("TALB").toString().replace("Text=", "")
                    .replace("\"", "")
                    .replace(";", "").trim();
        }

        return null;
    }

    @Override
    public byte[] getAlbumPic(AudioFile audioFile) {
        MP3File mp3File = (MP3File) audioFile;
        AbstractID3v2Tag tag = mp3File.getID3v2Tag();
        AbstractID3v2Frame frame;
        if (tag.getFrame("APIC") instanceof ArrayList) {
            frame = (AbstractID3v2Frame) ((ArrayList) tag.getFrame("APIC")).get(0);
        } else {
            frame = (AbstractID3v2Frame) tag.getFrame("APIC");
        }

        if (null != frame && null != frame.getBody() && null != ((FrameBodyAPIC) frame.getBody()).getImageData()
                && ((FrameBodyAPIC) frame.getBody()).getImageData().length > 0) {
            return ((FrameBodyAPIC) frame.getBody()).getImageData();
        }

        return null;
    }
}
