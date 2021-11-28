package com.stevejrong.music.factory.provider.service.music.metadata.resolver.persist.impl;

import com.stevejrong.music.factory.common.util.AlbumCoverUtil;
import com.stevejrong.music.factory.common.util.DateTimeUtil;
import com.stevejrong.music.factory.common.util.FileUtil;
import com.stevejrong.music.factory.spi.service.music.metadata.resolver.persist.AbstractAudioFileMetadataPersistResolver;
import com.stevejrong.music.factory.spi.service.music.metadata.resolver.persist.IAudioFileMetadataPersistResolver;
import com.stevejrong.music.factory.spi.service.music.metadata.resolver.query.IAudioFileMetadataQueryResolver;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.flac.FlacTag;
import org.jaudiotagger.tag.images.Artwork;
import org.jaudiotagger.tag.images.ArtworkFactory;

import java.time.LocalDate;

/**
 * Flac音频文件的元数据存储器
 */
public class FlacMetadataPersistResolver extends AbstractAudioFileMetadataPersistResolver implements IAudioFileMetadataPersistResolver {

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
        FlacTag flacTag = (FlacTag) audioFile.getTag();
        String originalSongTitle = super.metadataQueryResolver.getSongTitle(getAudioFile());

        if (StringUtils.isNotBlank(songTitle) && StringUtils.isBlank(originalSongTitle)) {
            // 当标签中没有歌曲名称时，需设置标签中的歌曲名称信息
            setFieldAndCommit(flacTag, FieldKey.TITLE, songTitle, getAudioFile());
        }
    }

    @Override
    public void setSongArtist(String songArtist) {
        FlacTag flacTag = (FlacTag) audioFile.getTag();
        String originalSongArtist = super.metadataQueryResolver.getSongArtist(getAudioFile());

        if (StringUtils.isNotBlank(songArtist) && StringUtils.isBlank(originalSongArtist)) {
            // 当标签中没有歌曲艺术家时，需设置标签中的歌曲艺术家信息
            setFieldAndCommit(flacTag, FieldKey.ARTIST, songArtist, getAudioFile());
        }
    }

    @Override
    public void setAlbumName(String albumName) {
        FlacTag flacTag = (FlacTag) audioFile.getTag();
        String originalAlbumName = super.metadataQueryResolver.getAlbumName(getAudioFile());

        if (StringUtils.isNotBlank(albumName) && StringUtils.isBlank(originalAlbumName)) {
            // 当标签中没有歌曲所属的专辑名称时，需设置标签中的歌曲所属的专辑名称信息
            setFieldAndCommit(flacTag, FieldKey.ALBUM, albumName, getAudioFile());
        }
    }

    @Override
    public void setAlbumPicture(byte[] albumPictureByteArray) {
        FlacTag flacTag = (FlacTag) audioFile.getTag();

        if (ArrayUtils.isEmpty(albumPictureByteArray)) {
            // 若第三方在线音乐服务平台中都没有查询到专辑图片，则使用默认专辑图片
            albumPictureByteArray = FileUtil.getDefaultAlbumPictureByteArray();
        }

        byte[] originalAlbumPictureByteArray = super.metadataQueryResolver.getAlbumPicture(getAudioFile());

        if (ArrayUtils.isEmpty(originalAlbumPictureByteArray)) {
            // 先删除专辑封面属性
            flacTag.deleteField(FieldKey.COVER_ART);

            // 再设置新的专辑封面图片
            Artwork artwork = ArtworkFactory.createArtworkFromMetadataBlockDataPicture(AlbumCoverUtil.buildMetadataBlockDataPicture(albumPictureByteArray));
            setFieldAndCommit(flacTag, artwork, getAudioFile());
        }
    }

    @Override
    public void setSongLyrics(String songLyrics) {
        FlacTag flacTag = (FlacTag) audioFile.getTag();
        String originalSongLyrics = super.metadataQueryResolver.getSongLyrics(getAudioFile());

        if (StringUtils.isNotBlank(songLyrics) && StringUtils.isBlank(originalSongLyrics)) {
            // 当标签中没有歌曲内嵌歌词时，需设置标签中的歌曲内嵌歌词信息
            setFieldAndCommit(flacTag, FieldKey.LYRICS, songLyrics, getAudioFile());
        }
    }

    @Override
    public void setAlbumArtist(String albumArtist) {
        FlacTag flacTag = (FlacTag) audioFile.getTag();
        String originalAlbumArtist = super.metadataQueryResolver.getAlbumArtist(getAudioFile());

        if (StringUtils.isNotBlank(albumArtist) && StringUtils.isBlank(originalAlbumArtist)) {
            // 当标签中没有歌曲所属专辑的艺术家时，需设置标签中的歌曲所属专辑的艺术家信息
            setFieldAndCommit(flacTag, FieldKey.ALBUM_ARTIST, albumArtist, getAudioFile());
        }
    }

    @Override
    public void setAlbumPublishDate(LocalDate albumPublishDate) {
        FlacTag flacTag = (FlacTag) audioFile.getTag();
        LocalDate originalAlbumPublishDate = super.metadataQueryResolver.getAlbumPublishDate(getAudioFile());

        if (null != albumPublishDate && null == originalAlbumPublishDate) {
            // 当标签中没有歌曲所属专辑的发布时间时，需设置标签中的歌曲所属专辑的发布时间信息
            setFieldAndCommit(flacTag, FieldKey.YEAR, DateTimeUtil.localDateToString(DateTimeUtil.DatePattern.YYYYMMDD_FORMAT.getValue(), albumPublishDate), getAudioFile());
        }
    }

    @Override
    public void setAlbumDescription(String albumDescription) {
        FlacTag flacTag = (FlacTag) audioFile.getTag();
        String originalAlbumDescription = super.metadataQueryResolver.getAlbumDescription(getAudioFile());

        if (StringUtils.isNotBlank(albumDescription) && StringUtils.isBlank(originalAlbumDescription)) {
            // 当标签中没有歌曲所属专辑的描述时，需设置标签中的歌曲所属专辑的描述信息
            setFieldAndCommit(flacTag, FieldKey.COMMENT, albumDescription, getAudioFile());
        }
    }

    @Override
    public void setAlbumLanguage(String albumLanguage) {
        FlacTag flacTag = (FlacTag) audioFile.getTag();
        String originalAlbumLanguage = super.metadataQueryResolver.getAlbumLanguage(getAudioFile());

        if (StringUtils.isNotBlank(albumLanguage) && StringUtils.isBlank(originalAlbumLanguage)) {
            // 当标签中没有歌曲所属专辑的语言类型时，需设置标签中的歌曲所属专辑的语言类型信息
            setFieldAndCommit(flacTag, FieldKey.LANGUAGE, albumLanguage, getAudioFile());
        }
    }

    @Override
    public void setAlbumCopyright(String albumCopyright) {
        FlacTag flacTag = (FlacTag) audioFile.getTag();
        String originalAlbumCopyright = super.metadataQueryResolver.getAlbumCopyright(getAudioFile());

        if (StringUtils.isNotBlank(albumCopyright) && StringUtils.isBlank(originalAlbumCopyright)) {
            // 当标签中没有歌曲所属专辑的语言类型时，需设置标签中的歌曲所属专辑的语言类型信息
            setFieldAndCommit(flacTag, FieldKey.COPYRIGHT, albumCopyright, getAudioFile());
        }
    }
}
