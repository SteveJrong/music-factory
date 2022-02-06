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
package com.stevejrong.music.factory.provider.service.music.formatConversion.impl;

import com.stevejrong.music.factory.common.constants.BaseConstants;
import com.stevejrong.music.factory.common.util.FFmpegUtil;
import com.stevejrong.music.factory.spi.service.music.formatConversion.AbstractAudioFileConverter;
import com.stevejrong.music.factory.spi.service.music.formatConversion.IAudioFileConverter;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.io.File;

/**
 * FLAC音频编码格式 转换为 Ogg Vorbis音频编码格式 音频转换器
 *
 * @author Steve Jrong
 * @since 1.0
 */
public class FLAC_to_OGG_VORBIS_Converter extends AbstractAudioFileConverter implements IAudioFileConverter {
    @Override
    public int converterNum() {
        return 1;
    }

    @Override
    public String sourceFileSuffix() {
        return BaseConstants.POINT_CHAR + BaseConstants.FILE_SUFFIX_FLAC;
    }

    @Override
    public String targetFileSuffix() {
        return BaseConstants.POINT_CHAR + BaseConstants.FILE_SUFFIX_OGG;
    }

    @Override
    public FFmpegBuilder setFFmpegBuilder(String targetDirectory, String targetFileName, FFmpegBuilder ffmpegBuilder) {
        String targetPath = targetDirectory + File.separatorChar + targetFileName + this.targetFileSuffix();

        return ffmpegBuilder.addOutput(targetPath)
                .disableVideo()
                .setAudioCodec(BaseConstants.AUDIO_ENCODE_OGG_VORBIS)
                .setAudioQuality(10.0)
                .setStrict(FFmpegBuilder.Strict.NORMAL)
                .done();
    }

    @Override
    public boolean convert(String sourcePath, String targetDirectory, String targetFileName) {
        try {
            FFmpegUtil.convert(this.setFFmpegBuilder(targetDirectory, targetFileName, super.createDefaultFFmpegBuilder(sourcePath)));
            copyAlbumPicture(sourcePath, targetDirectory + File.separatorChar + targetFileName + this.targetFileSuffix());
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
