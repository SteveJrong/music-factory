package com.stevejrong.music.factory.provider.service.music.metadata.resolver.persist.impl;

import com.google.common.collect.Lists;
import com.stevejrong.music.factory.common.enums.ID3v2FramesForMP3Enum;
import com.stevejrong.music.factory.common.util.AlbumCoverUtil;
import com.stevejrong.music.factory.common.util.DateTimeUtil;
import com.stevejrong.music.factory.common.util.FileUtil;
import com.stevejrong.music.factory.common.util.Mp3Util;
import com.stevejrong.music.factory.spi.service.music.metadata.resolver.persist.AbstractAudioFileMetadataPersistResolver;
import com.stevejrong.music.factory.spi.service.music.metadata.resolver.persist.IAudioFileMetadataPersistResolver;
import com.stevejrong.music.factory.spi.service.music.metadata.resolver.query.IAudioFileMetadataQueryResolver;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagField;
import org.jaudiotagger.tag.id3.*;
import org.jaudiotagger.tag.id3.framebody.FrameBodyTDAT;
import org.jaudiotagger.tag.id3.framebody.FrameBodyTYER;
import org.jaudiotagger.tag.images.Artwork;
import org.jaudiotagger.tag.images.ArtworkFactory;

import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * MP3音频文件的元数据存储器
 */
public class Mp3MetadataPersistResolver extends AbstractAudioFileMetadataPersistResolver implements IAudioFileMetadataPersistResolver {
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
        AbstractID3v2Tag id3v2Tag = Mp3Util.checkID3v2Tag((MP3File) getAudioFile());
        ID3v1Tag id3v1Tag = Mp3Util.checkID3v1Tag((MP3File) getAudioFile());

        // 先处理ID3v2标签
        String songTitleForID3v2Tag = Mp3Util.getContentByContentKeyAndMp3FrameNameInID3v2Tag(ID3v2FramesForMP3Enum.TIT2.getValue(), "Text", id3v2Tag);
        if (StringUtils.isNotBlank(songTitle) && StringUtils.isBlank(songTitleForID3v2Tag)) {
            // 当ID3v2标签中没有歌曲名称时，需设置ID3v2标签中的歌曲名称信息
            setFieldAndCommit(id3v2Tag, FieldKey.TITLE, songTitle, getAudioFile());
        }

        // 再处理ID3v1标签
        List<TagField> tagField = Optional.ofNullable(id3v1Tag.getFields(FieldKey.TITLE)).orElse(Lists.newArrayList());
        if (StringUtils.isNotBlank(songTitle) && (CollectionUtils.isEmpty(tagField) || StringUtils.isBlank(((ID3v1TagField) tagField.get(0)).getContent()))) {
            // 当ID3v1标签中没有歌曲名称时，需设置ID3v1标签中的歌曲名称信息
            setFieldAndCommit(id3v1Tag, FieldKey.TITLE, new String(songTitle.getBytes(), StandardCharsets.ISO_8859_1), getAudioFile());
        }
    }

    @Override
    public void setSongArtist(String songArtist) {
        AbstractID3v2Tag id3v2Tag = Mp3Util.checkID3v2Tag((MP3File) getAudioFile());
        ID3v1Tag id3v1Tag = Mp3Util.checkID3v1Tag((MP3File) getAudioFile());

        // 先处理ID3v2标签
        String songArtistForID3v2Tag = Mp3Util.getContentByContentKeyAndMp3FrameNameInID3v2Tag(ID3v2FramesForMP3Enum.TPE1.getValue(), "Text", id3v2Tag);
        if (StringUtils.isNotBlank(songArtist) && StringUtils.isBlank(songArtistForID3v2Tag)) {
            // 当ID3v2标签中没有歌曲艺术家时，需设置ID3v2标签中的歌曲艺术家信息
            setFieldAndCommit(id3v2Tag, FieldKey.ARTIST, songArtist, getAudioFile());
        }

        // 再处理ID3v1标签
        List<TagField> tagField = Optional.ofNullable(id3v1Tag.getFields(FieldKey.ARTIST)).orElse(Lists.newArrayList());
        if (StringUtils.isNotBlank(songArtist) && (CollectionUtils.isEmpty(tagField) || StringUtils.isBlank(((ID3v1TagField) tagField.get(0)).getContent()))) {
            // 当ID3v1标签中没有歌曲艺术家时，需设置ID3v1标签中的歌曲艺术家信息
            setFieldAndCommit(id3v1Tag, FieldKey.ARTIST, new String(songArtist.getBytes(), StandardCharsets.ISO_8859_1), getAudioFile());
        }
    }

    @Override
    public void setAlbumName(String albumName) {
        MP3File mp3File = (MP3File) getAudioFile();

        AbstractID3v2Tag id3v2Tag = Mp3Util.checkID3v2Tag((MP3File) getAudioFile());
        ID3v1Tag id3v1Tag = Mp3Util.checkID3v1Tag((MP3File) getAudioFile());

        // 先处理ID3v2标签
        String albumNameForID3v2Tag = Mp3Util.getContentByContentKeyAndMp3FrameNameInID3v2Tag(ID3v2FramesForMP3Enum.TALB.getValue(), "Text", id3v2Tag);
        if (StringUtils.isNotBlank(albumName) && StringUtils.isBlank(albumNameForID3v2Tag)) {
            // 当ID3v2标签中没有歌曲所属的专辑名称时，需设置ID3v2标签中的歌曲所属的专辑名称信息
            setFieldAndCommit(id3v2Tag, FieldKey.ALBUM, albumName, getAudioFile());
        }

        // 再处理ID3v1标签
        List<TagField> tagField = Optional.ofNullable(id3v1Tag.getFields(FieldKey.ALBUM)).orElse(Lists.newArrayList());
        if (StringUtils.isNotBlank(albumName) && (CollectionUtils.isEmpty(tagField) || StringUtils.isBlank(((ID3v1TagField) tagField.get(0)).getContent()))) {
            // 当ID3v1标签中没有歌曲所属的专辑名称时，需设置ID3v1标签中的歌曲所属的专辑名称信息
            setFieldAndCommit(id3v1Tag, FieldKey.ALBUM, new String(albumName.getBytes(), StandardCharsets.ISO_8859_1), getAudioFile());
        }
    }

    @Override
    public void setAlbumPicture(byte[] albumPictureByteArray) {
        // 专辑封面不支持ID3v1标签
        AbstractID3v2Tag id3v2Tag = Mp3Util.checkID3v2Tag((MP3File) getAudioFile());


        if (ArrayUtils.isEmpty(albumPictureByteArray)) {
            // 若第三方在线音乐服务平台中都没有查询到专辑图片，则使用默认专辑图片
            albumPictureByteArray = FileUtil.getDefaultAlbumPictureByteArray();
        }

        // 读取音频文件内的专辑封面图片
        byte[] originalAlbumPictureByteArray = Mp3Util.getContentByContentKeyAndMp3FrameNameInID3v2Tag(ID3v2FramesForMP3Enum.APIC.getValue(), "PictureData", id3v2Tag);

        BufferedImage originalAlbumPicture = null;
        if (ArrayUtils.isNotEmpty(originalAlbumPictureByteArray)) {
            originalAlbumPicture = FileUtil.getBufferedImageByPictureByteArray(originalAlbumPictureByteArray);
        }

        if (ArrayUtils.isEmpty(originalAlbumPictureByteArray)
                || (null != originalAlbumPicture && originalAlbumPicture.getWidth() * originalAlbumPicture.getHeight() < 500 * 500)) {
            // 先删除专辑封面属性
            id3v2Tag.deleteField(FieldKey.COVER_ART);

            // 再设置新的专辑封面图片
            Artwork artwork = ArtworkFactory.createArtworkFromMetadataBlockDataPicture(AlbumCoverUtil.buildMetadataBlockDataPicture(albumPictureByteArray));
            setFieldAndCommit(id3v2Tag, artwork, getAudioFile());
        }
    }

    @Override
    public void setSongLyrics(String songLyrics) {
        // 歌曲内嵌歌词不支持ID3v1标签
        AbstractID3v2Tag id3v2Tag = Mp3Util.checkID3v2Tag((MP3File) getAudioFile());

        String songLyricsForID3v2Tag = Mp3Util.getContentByContentKeyAndMp3FrameNameInID3v2Tag(ID3v2FramesForMP3Enum.USLT.getValue(), "Lyrics", id3v2Tag);
        if (StringUtils.isNotBlank(songLyrics) && StringUtils.isBlank(songLyricsForID3v2Tag)) {
            // 当ID3v2标签中没有歌曲内嵌歌词时，需设置ID3v2标签中的歌曲内嵌歌词信息
            setFieldAndCommit(id3v2Tag, FieldKey.LYRICS, songLyrics, getAudioFile());
        }
    }

    @Override
    public void setAlbumArtist(String albumArtist) {
        // 歌曲所属专辑的艺术家不支持ID3v1标签
        AbstractID3v2Tag id3v2Tag = Mp3Util.checkID3v2Tag((MP3File) getAudioFile());

        String albumArtistForID3v2Tag = Mp3Util.getContentByContentKeyAndMp3FrameNameInID3v2Tag(ID3v2FramesForMP3Enum.TPE2.getValue(), "Text", id3v2Tag);
        if (StringUtils.isNotBlank(albumArtist) && StringUtils.isBlank(albumArtistForID3v2Tag)) {
            // 当ID3v2标签中没有歌曲所属专辑的艺术家时，需设置ID3v2标签中的歌曲所属专辑的艺术家信息
            setFieldAndCommit(id3v2Tag, FieldKey.ALBUM_ARTIST, albumArtist, getAudioFile());
        }
    }

    @Override
    public void setAlbumPublishDate(LocalDate albumPublishDate) {
        MP3File mp3File = (MP3File) getAudioFile();

        AbstractID3v2Tag id3v2Tag = Mp3Util.checkID3v2Tag((MP3File) getAudioFile());
        ID3v1Tag id3v1Tag = Mp3Util.checkID3v1Tag((MP3File) getAudioFile());

        // 先处理ID3v2标签
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

        // 再处理ID3v1标签
        // // ID3v1标签中的发布时间，仅支持年份
        List<TagField> tagField = Optional.ofNullable(id3v1Tag.getFields(FieldKey.YEAR)).orElse(Lists.newArrayList());
        if (null != albumPublishDate && (CollectionUtils.isEmpty(tagField) || StringUtils.isBlank(((ID3v1TagField) tagField.get(0)).getContent()))) {
            // 当ID3v1标签中的歌曲所属专辑的发布年份缺失时，需设置ID3v1标签中的歌曲所属专辑的发布时间信息
            setFieldAndCommit(id3v1Tag, FieldKey.YEAR,
                    new String(DateTimeUtil.localDateToString(DateTimeUtil.DatePattern.YYYYMMDD_FORMAT.getValue(), albumPublishDate).getBytes(), StandardCharsets.ISO_8859_1),
                    getAudioFile());
        }
    }

    @Override
    public void setAlbumDescription(String albumDescription) {
        AbstractID3v2Tag id3v2Tag = Mp3Util.checkID3v2Tag((MP3File) getAudioFile());

        // 先处理ID3v2标签
        String albumNameForID3v2Tag = Mp3Util.getContentByContentKeyAndMp3FrameNameInID3v2Tag(ID3v2FramesForMP3Enum.COMM.getValue(), "Text", id3v2Tag);
        if (StringUtils.isNotBlank(albumDescription) && StringUtils.isBlank(albumNameForID3v2Tag)) {
            // 当ID3v2标签中没有歌曲所属专辑的描述时，需设置ID3v2标签中的歌曲所属专辑的描述信息
            setFieldAndCommit(id3v2Tag, FieldKey.COMMENT, albumDescription, getAudioFile());
        }

        // 再处理ID3v1标签
        // ID3v1标签仅支持ISO-8859-1编码，不支持中文存储，默认不设置
        /*List<TagField> tagField = Optional.ofNullable(id3v1Tag.getFields(FieldKey.COMMENT)).orElse(Lists.newArrayList());
        if (StringUtils.isNotBlank(albumDescription) && (CollectionUtils.isEmpty(tagField) || StringUtils.isBlank(((ID3v1TagField) tagField.get(0)).getContent()))) {
            // 当ID3v1标签中没有歌曲所属专辑的描述时，需设置ID3v1标签中的歌曲所属专辑的描述信息
            setFieldAndCommit(id3v1Tag, FieldKey.COMMENT, new String(albumDescription.getBytes(), StandardCharsets.ISO_8859_1), getAudioFile());
        }*/
    }

    @Override
    public void setAlbumLanguage(String albumLanguage) {
        // 歌曲所属专辑的语言类型不支持ID3v1标签
        AbstractID3v2Tag id3v2Tag = Mp3Util.checkID3v2Tag((MP3File) getAudioFile());

        String albumLanguageForID3v2Tag = Mp3Util.getContentByContentKeyAndMp3FrameNameInID3v2Tag(ID3v2FramesForMP3Enum.TLAN.getValue(), "Text", id3v2Tag);
        if (StringUtils.isNotBlank(albumLanguage) && StringUtils.isBlank(albumLanguageForID3v2Tag)) {
            // 当ID3v2标签中没有歌曲所属专辑的语言类型时，需设置ID3v2标签中的歌曲所属专辑的语言类型信息
            setFieldAndCommit(id3v2Tag, FieldKey.LANGUAGE, albumLanguage, getAudioFile());
        }
    }

    @Override
    public void setAlbumCopyright(String albumCopyright) {
        MP3File mp3File = (MP3File) getAudioFile();

        // 歌曲所属专辑的版权信息不支持ID3v1标签
        AbstractID3v2Tag id3v2Tag = Mp3Util.checkID3v2Tag((MP3File) getAudioFile());

        String albumCopyrightForID3v2Tag = Mp3Util.getContentByContentKeyAndMp3FrameNameInID3v2Tag(ID3v2FramesForMP3Enum.TCOP.getValue(), "Text", id3v2Tag);
        if (StringUtils.isNotBlank(albumCopyright) && StringUtils.isBlank(albumCopyrightForID3v2Tag)) {
            // 当ID3v2标签中没有歌曲所属专辑的版权信息时，需设置ID3v2标签中的歌曲所属专辑的版权信息信息
            setFieldAndCommit(id3v2Tag, FieldKey.COPYRIGHT, albumCopyright, getAudioFile());
        }
    }
}
