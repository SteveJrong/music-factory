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
package com.stevejrong.music.factory.spi.service.music.formatConversion;

import com.stevejrong.music.factory.config.SystemConfig;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

public abstract class AbstractMusicFileConverter implements IAudioFileConverter {
    private SystemConfig systemConfig;

    public SystemConfig getSystemConfig() {
        return systemConfig;
    }

    public void setSystemConfig(SystemConfig systemConfig) {
        this.systemConfig = systemConfig;
    }

    /**
     * 创建默认的FFmpegBuilder对象
     *
     * @param sourcePath
     * @return
     */
    protected FFmpegBuilder createDefaultFFmpegBuilder(String sourcePath) {
        FFmpegBuilder ffmpegBuilder = new FFmpegBuilder()
                .overrideOutputFiles(true)
                .setInput(sourcePath);

        return ffmpegBuilder;
    }

    /**
     * 设置格式转换时的特殊个性化参数到FFmpegBuilder构建对象中
     * <p>
     * 只要进行格式转换，则FFmpeg组件的参数一定要设置，故必须强制自类重写此方法。
     *
     * @param targetDirectory
     * @param targetFileName
     * @param ffmpegBuilder
     * @return
     */
    public abstract FFmpegBuilder setFFmpegBuilder(String targetDirectory, String targetFileName, FFmpegBuilder ffmpegBuilder);
}
