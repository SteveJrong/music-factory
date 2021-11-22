package com.stevejrong.music.factory.spi.service.music.metadata.resolver.query;

import org.jaudiotagger.audio.AudioFile;

import java.time.LocalDate;

/**
 * Service Interface - 音频文件文件元数据解析器接口
 * <p>
 * 针对不同的音频文件类型，去对应实现不同的解析器，查询元数据信息
 */
public interface IAudioFileMetadataQueryResolver {

    /**
     * 获取音频文件元数据中的歌曲标题
     *
     * @param audioFile
     * @return
     */
    String getSongTitle(AudioFile audioFile);

    /**
     * 获取音频文件元数据中的歌曲艺术家
     *
     * @param audioFile
     * @return
     */
    String getSongArtist(AudioFile audioFile);

    /**
     * 获取音频文件元数据中的歌曲内嵌歌词
     *
     * @param audioFile
     * @return
     */
    String getSongLyrics(AudioFile audioFile);

    /**
     * 获取音频文件元数据中歌曲所属专辑的名称
     *
     * @param audioFile
     * @return
     */
    String getAlbumName(AudioFile audioFile);

    /**
     * 获取音频文件元数据中歌曲所属专辑的艺术家
     *
     * @param audioFile
     * @return
     */
    String getAlbumArtist(AudioFile audioFile);

    /**
     * 获取音频文件元数据中歌曲所属专辑的发布时间
     *
     * @param audioFile
     * @return
     */
    LocalDate getAlbumPublishDate(AudioFile audioFile);

    /**
     * 获取音频文件元数据中歌曲所属专辑的描述
     *
     * @param audioFile
     * @return
     */
    String getAlbumDescription(AudioFile audioFile);

    /**
     * 获取音频文件元数据中歌曲所属专辑的语言类型
     *
     * @param audioFile
     * @return
     */
    String getAlbumLanguage(AudioFile audioFile);

    /**
     * 获取音频文件元数据中歌曲所属专辑的版权信息
     *
     * @param audioFile
     * @return
     */
    String getAlbumCopyright(AudioFile audioFile);

    /**
     * 获取音频文件元数据中歌曲所属的专辑封面
     *
     * @param audioFile
     * @return
     */
    byte[] getAlbumPicture(AudioFile audioFile);
}
