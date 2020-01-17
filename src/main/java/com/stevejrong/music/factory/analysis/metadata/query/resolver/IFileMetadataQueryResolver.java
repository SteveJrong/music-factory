package com.stevejrong.music.factory.analysis.metadata.query.resolver;

import org.jaudiotagger.audio.AudioFile;

/**
 * 文件元数据解析器接口
 * 针对不同的音频文件类型，去对应实现不同的解析器，查询元数据信息
 */
public interface IFileMetadataQueryResolver {

    /**
     * 获取音频文件元数据中的歌曲名称
     *
     * @param audioFile
     * @return
     */
    String getSongName(AudioFile audioFile);

    /**
     * 获取音频文件元数据中的歌曲演唱者
     *
     * @param audioFile
     * @return
     */
    String getSingerName(AudioFile audioFile);

    /**
     * 获取音频文件元数据中歌曲所属的专辑名称
     *
     * @param audioFile
     * @return
     */
    String getAlbumName(AudioFile audioFile);

    /**
     * 获取音频文件元数据中歌曲所属专辑的封面图片
     *
     * @param audioFile
     * @return
     */
    byte[] getAlbumPic(AudioFile audioFile);
}
