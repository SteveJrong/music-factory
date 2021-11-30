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
