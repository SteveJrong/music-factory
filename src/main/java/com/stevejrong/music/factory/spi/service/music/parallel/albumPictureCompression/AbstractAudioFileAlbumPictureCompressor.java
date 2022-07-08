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
package com.stevejrong.music.factory.spi.service.music.parallel.albumPictureCompression;

import com.stevejrong.music.factory.config.SystemConfig;

/**
 * 抽象音频文件专辑封面压缩器类
 *
 * @author Steve Jrong
 * @since 1.0
 */
public abstract class AbstractAudioFileAlbumPictureCompressor implements IAudioFileAlbumPictureCompressor {
    private SystemConfig systemConfig;

    public SystemConfig getSystemConfig() {
        return systemConfig;
    }

    public void setSystemConfig(SystemConfig systemConfig) {
        this.systemConfig = systemConfig;
    }

    @Override
    public int compressPixelValue() {
        return systemConfig.getAlbumPictureCompressionConfig().getCompressPixelValue();
    }
}