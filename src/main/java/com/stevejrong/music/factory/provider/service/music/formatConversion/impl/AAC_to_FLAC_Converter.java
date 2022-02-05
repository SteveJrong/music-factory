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
import com.stevejrong.music.factory.spi.service.music.formatConversion.AbstractMusicFileConverter;
import com.stevejrong.music.factory.spi.service.music.formatConversion.IAudioFileConverter;

/**
 * AAC音频编码格式（基于M4A容器） 转换为 FLAC音频编码格式 音频转换器
 */
public class AAC_to_FLAC_Converter extends AbstractMusicFileConverter implements IAudioFileConverter {

    @Override
    public int converterNum() {
        return 4;
    }

    @Override
    public String sourceEncodeName() {
        return BaseConstants.AUDIO_ENCODE_AAC;
    }

    @Override
    public String targetEncodeName() {
        return BaseConstants.AUDIO_ENCODE_FLAC;
    }

    @Override
    public String targetFileSuffix() {
        return BaseConstants.POINT_CHAR + BaseConstants.FILE_SUFFIX_FLAC;
    }

    @Override
    public boolean convert(String sourcePath, String targetPath) {
        return true;
    }
}
