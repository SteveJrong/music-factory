/**
 * Copyright 2021 Steve Jrong
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stevejrong.music.factory.common.util;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.TagField;
import org.jaudiotagger.tag.id3.*;

import java.util.List;
import java.util.Optional;

/**
 * Util - MP3音频文件工具类
 *
 * @author Steve Jrong
 * create date: 2021-11-15 10:11 PM
 * @since 1.0
 */
public final class Mp3Util {

    /**
     * 在MP3的音频文件的ID3v2标签中，获取有用的内容
     *
     * @param frameName  MP3音频文件的帧名称
     * @param contentKey 要获取内容的Key值
     * @return 有用的内容。若没获取到，则返回null。
     */
    public static <T> T getContentByContentKeyAndMp3FrameNameInID3v2Tag(String frameName, String contentKey, AbstractID3v2Tag id3v2Tag) {
        List<TagField> tagFields = Optional.ofNullable(id3v2Tag.getFields(frameName)).orElse(Lists.newArrayList());
        if (CollectionUtils.isNotEmpty(tagFields)) {
            return (T) ((AbstractTagFrame) tagFields.get(0)).getBody().getObjectValue(contentKey);
        }

        return null;
    }

    /**
     * 检查MP3音频文件中是否存在ID3v1标签。若不存在，则返回一个ID3v11对象
     *
     * @param mp3File MP3音频文件对象
     * @return ID3v1对象
     */
    public static ID3v1Tag checkID3v1Tag(MP3File mp3File) {
        return mp3File.hasID3v1Tag() ? mp3File.getID3v1Tag() : new ID3v11Tag();
    }

    /**
     * 检查MP3音频文件中是否存在ID3v2标签。若不存在，则返回一个ID3v23对象
     *
     * @param mp3File MP3音频文件对象
     * @return ID3v2对象
     */
    public static AbstractID3v2Tag checkID3v2Tag(MP3File mp3File) {
        return mp3File.hasID3v2Tag() ? mp3File.getID3v2Tag() : new ID3v23Tag();
    }
}