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
