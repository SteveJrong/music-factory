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
package com.stevejrong.music.factory.common.util;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.dsf.Dsf;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;

/**
 * DSF音频文件工具类
 *
 * @author Steve Jrong
 * @since 1.0
 */
public final class DsfUtil {

    /**
     * 检查Dsf音频文件中是否存在ID3v2标签。若不存在，则返回一个ID3v23对象
     *
     * @param dsfFile DSF音频文件对象
     * @return ID3v2对象
     */
    public static Tag checkID3v2Tag(AudioFile dsfFile) {
        AbstractID3v2Tag id3v2Tag = (AbstractID3v2Tag) dsfFile.getTag();
        return null != id3v2Tag ? id3v2Tag : Dsf.createDefaultTag();
    }
}