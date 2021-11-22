package com.stevejrong.music.factory.spi.service.music.metadata.resolver.persist;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.tag.FieldDataInvalidException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.images.Artwork;

/**
 * Service Interface - 文件元数据存储器接口
 * <p>
 * 针对不同的音频文件类型，去对应实现不同的元数据存储器，保存元数据信息
 */
public interface IAudioFileMetadataPersistResolver {

    /**
     * 设置音频文件元数据中的歌曲标题
     *
     * @param audioFile 要持久化的音频文件
     * @param tag       要持久化保存进音频文件中的标签信息
     * @param songTitle 歌曲标题
     */
    void setSongTitle(AudioFile audioFile, Tag tag, String songTitle);

    /**
     * 设置音频文件元数据中的歌曲艺术家
     *
     * @param audioFile  要持久化的音频文件
     * @param tag        要持久化保存进音频文件中的标签信息
     * @param songArtist 歌曲艺术家
     */
    void setSongArtist(AudioFile audioFile, Tag tag, String songArtist);

    /**
     * 设置音频文件元数据中歌曲所属的专辑名称
     *
     * @param audioFile 要持久化的音频文件
     * @param tag       要持久化保存进音频文件中的标签信息
     * @param albumName 歌曲所属的专辑名称
     */
    void setAlbumName(AudioFile audioFile, Tag tag, String albumName);

    /**
     * 设置音频文件元数据中歌曲所属专辑的封面
     *
     * @param audioFile             要持久化的音频文件
     * @param tag                   要持久化保存进音频文件中的标签信息
     * @param albumPictureByteArray 歌曲所属专辑的封面
     */
    void setAlbumPicture(AudioFile audioFile, Tag tag, byte[] albumPictureByteArray);

    /**
     * 保存元数据到音频文件中
     *
     * @param audioFile
     * @param tag
     */
    void persistMetadata(AudioFile audioFile, Tag tag);

    /**
     * 设置专辑封面并应用
     *
     * @param tag 音频文件标签对象
     * @param artwork 专辑封面对象
     * @param audioFile 音频文件对象
     */
    default void setFieldAndCommit(Tag tag, Artwork artwork, AudioFile audioFile) {
        try {
            tag.setField(artwork);
            AudioFileIO.write(audioFile);
        } catch (FieldDataInvalidException | CannotWriteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置属性并应用
     *
     * @param tag 音频文件标签对象
     * @param fieldKey 属性标签对象
     * @param audioFile 音频文件对象
     */
    default void setFieldAndCommit(Tag tag, FieldKey fieldKey, AudioFile audioFile) {
        try {
            tag.setField(fieldKey);
            AudioFileIO.write(audioFile);
        } catch (FieldDataInvalidException | CannotWriteException e) {
            e.printStackTrace();
        }
    }
}
