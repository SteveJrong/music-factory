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

import com.stevejrong.music.factory.common.util.CommandUtil;
import com.stevejrong.music.factory.spi.service.music.formatConversion.AbstractMusicFileConverter;
import com.stevejrong.music.factory.spi.service.music.formatConversion.IAudioFileConverter;

import java.io.File;

/**
 * FLAC音频格式转换为M4A（Apple Lossless）音频格式转换器
 * 此转换器已弃用
 */
@Deprecated
public class FlacToM4aAlacConverter extends AbstractMusicFileConverter implements IAudioFileConverter {

    @Override
    public String convert(String sourceDirectory, String targetDirectory, String sourceFileName, String targetFileName, String sourceFileFormat, String targetFileFormat) {
        String sourceFilePath = "\"" + sourceDirectory + File.separator + sourceFileName + sourceFileFormat + "\"";
        String targetFilePath = "\"" + targetDirectory + File.separator + sourceFileName + targetFileFormat + "\"";

        CommandUtil.execute("ffmpeg -nostdin -i "
                + sourceFilePath
                + "-c:a alac -c:v copy "
                + targetFilePath);
        return null;
    }
}
