package com.stevejrong.music.factory.config.sub;

import com.stevejrong.music.factory.common.enums.SupportOSForFFmpegEnum;

import java.util.Map;

public final class FfmpegComponentConfig {

    /**
     * ffmpeg文件对于不同操作系统，存储的文件位置
     * <p>
     * Key - 操作系统类型；Value - 对应操作系统的FFmpeg文件位置
     */
    private Map<SupportOSForFFmpegEnum, String> ffmpegPathsByOSType;

    /**
     * ffprobe文件对于不同操作系统，存储的文件位置
     * <p>
     * Key - 操作系统类型；Value - 对应操作系统的ffprobe文件位置
     */
    private Map<SupportOSForFFmpegEnum, String> ffmprobePathsByOSType;

    public Map<SupportOSForFFmpegEnum, String> getFfmpegPathsByOSType() {
        return ffmpegPathsByOSType;
    }

    public void setFfmpegPathsByOSType(Map<SupportOSForFFmpegEnum, String> ffmpegPathsByOSType) {
        this.ffmpegPathsByOSType = ffmpegPathsByOSType;
    }

    public Map<SupportOSForFFmpegEnum, String> getFfmprobePathsByOSType() {
        return ffmprobePathsByOSType;
    }

    public void setFfmprobePathsByOSType(Map<SupportOSForFFmpegEnum, String> ffmprobePathsByOSType) {
        this.ffmprobePathsByOSType = ffmprobePathsByOSType;
    }
}
