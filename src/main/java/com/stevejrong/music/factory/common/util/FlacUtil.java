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
import org.jaudiotagger.tag.flac.FlacTag;

public final class FlacUtil {

    /**
     * 检查FLAC音频文件中是否存在FlacTag标签。若不存在，则返回一个FlacTag对象
     *
     * @param flacFile FLAC音频文件对象
     * @return FlacTag对象
     */
    public static FlacTag checkFlacTag(AudioFile flacFile) {
        return null != flacFile.getTag() ? (FlacTag) flacFile.getTag() : new FlacTag();
    }
}
