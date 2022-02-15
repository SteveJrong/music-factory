/*
 *             Copyright (C) 2022 Steve Jrong
 *
 * 	   GitHub Homepage: https://www.github.com/SteveJrong
 *      Gitee Homepage: https://gitee.com/stevejrong1024
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stevejrong.music.factory.spi.service.music.metadata.resolver.query;

import org.jaudiotagger.audio.AudioFile;

import java.time.LocalDate;

/**
 * 音频文件文件元数据解析器接口
 * <p>
 * 针对不同的音频文件类型，去对应实现不同的解析器，查询元数据信息
 *
 * @author Steve Jrong
 * @since 1.0
 */
public interface IAudioFileMetadataQueryResolver {

    /**
     * 设置音频文件对象
     *
     * @param audioFile 音频文件对象
     */
    void setAudioFile(AudioFile audioFile);

    /**
     * 获取音频文件元数据中的歌曲标题
     *
     * @return
     */
    String getSongTitle();

    /**
     * 获取音频文件元数据中的歌曲艺术家
     *
     * @return
     */
    String getSongArtist();

    /**
     * 获取音频文件元数据中的歌曲内嵌歌词
     *
     * @return
     */
    String getSongLyrics();

    /**
     * 获取音频文件元数据中歌曲所属专辑的名称
     *
     * @return
     */
    String getAlbumName();

    /**
     * 获取音频文件元数据中歌曲所属专辑的艺术家
     *
     * @return
     */
    String getAlbumArtist();

    /**
     * 获取音频文件元数据中歌曲所属专辑的发布时间
     *
     * @return
     */
    LocalDate getAlbumPublishDate();

    /**
     * 获取音频文件元数据中歌曲所属专辑的描述
     *
     * @return
     */
    String getAlbumDescription();

    /**
     * 获取音频文件元数据中歌曲所属专辑的语言类型
     *
     * @return
     */
    String getAlbumLanguage();

    /**
     * 获取音频文件元数据中歌曲所属专辑的版权信息
     *
     * @return
     */
    String getAlbumCopyright();

    /**
     * 获取音频文件元数据中歌曲所属的专辑封面
     *
     * @param sizeLimit 是否开启专辑封面尺寸限定。true - 开启专辑封面尺寸限定；false - 关闭专辑封面尺寸限定。
     * @return
     */
    byte[] getAlbumPicture(boolean sizeLimit);
}
