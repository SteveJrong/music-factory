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
package com.stevejrong.music.factory.spi.service.music.parallel.formatConversion;

import com.stevejrong.music.factory.config.SystemConfig;
import com.stevejrong.music.factory.spi.music.bo.formatConversion.FFmpegBuilderBo;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

/**
 * 抽象音频文件转换器类
 *
 * @author Steve Jrong
 * @since 1.0
 */
public abstract class AbstractAudioFileConverter implements IAudioFileConverter {
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
     * @param sourcePath 源音频文件位置
     * @return
     */
    protected FFmpegBuilder createDefaultFFmpegBuilder(String sourcePath) {
        FFmpegBuilder ffmpegBuilder = new FFmpegBuilder()
                .overrideOutputFiles(true)
                .setInput(sourcePath);

        return ffmpegBuilder;
    }

    /**
     * 设置并获取格式转换时的特殊个性化参数到FFmpegBuilder构建对象中
     *
     * @param ffmpegBuilderBo 构建FFmpegBuilder对象的Bo
     * @return 经过子类设置的FFmpegBuilder构建对象
     */
    public abstract FFmpegBuilder getFFmpegBuilder(FFmpegBuilderBo ffmpegBuilderBo);
}
