package com.stevejrong.music.factory.analysis.metadata.persist.resolver;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.tag.Tag;

/**
 * 文件元数据存储器接口
 * 针对不同的音频文件类型，去对应实现不同的元数据存储器，保存元数据信息
 */
public interface IFileMetadataPersistResolver {

    /**
     * 设置音频文件元数据中的歌曲名称
     *
     * @param audioFile 要持久化的音频文件
     * @param tag       要持久化保存进音频文件中的标签信息
     * @param songName  歌曲名称
     */
    void setSongName(AudioFile audioFile, Tag tag, String songName);

    /**
     * 设置音频文件元数据中的歌曲演唱者
     *
     * @param audioFile  要持久化的音频文件
     * @param tag        要持久化保存进音频文件中的标签信息
     * @param singerName
     */
    void setSingerName(AudioFile audioFile, Tag tag, String singerName);

    /**
     * 设置音频文件元数据中歌曲所属的专辑名称
     *
     * @param audioFile 要持久化的音频文件
     * @param tag       要持久化保存进音频文件中的标签信息
     * @param albumName
     */
    void setAlbumName(AudioFile audioFile, Tag tag, String albumName);

    /**
     * 设置音频文件元数据中歌曲所属专辑的封面图片
     *
     * @param audioFile         要持久化的音频文件
     * @param tag               要持久化保存进音频文件中的标签信息
     * @param albumPicByteArray
     * @param songName
     * @param singerName
     */
    void setAlbumPic(AudioFile audioFile, Tag tag, byte[] albumPicByteArray, String songName, String singerName);

    /**
     * 保存元数据到音频文件中
     *
     * @param audioFile
     * @param tag
     */
    void persistMetadata(AudioFile audioFile, Tag tag);
}
