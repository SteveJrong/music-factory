package com.stevejrong.music.factory.provider.service.music.formatConversion.impl;

import com.stevejrong.music.factory.common.util.FFmpegUtil;
import com.stevejrong.music.factory.spi.service.music.formatConversion.AbstractMusicFileConverter;
import com.stevejrong.music.factory.spi.service.music.formatConversion.IAudioFileConverter;

/**
 * APE音频格式转换为FLAC音频格式转换器
 */
public class ApeToFlacConverter extends AbstractMusicFileConverter implements IAudioFileConverter {

    @Override
    public String convert(String sourceDirectory, String targetDirectory, String sourceFileName, String targetFileName,
                          String sourceFileFormat, String targetFileFormat) {
        FFmpegUtil.convertToFlac(sourceFileName + sourceFileFormat, targetFileName + targetFileFormat);
        return null;
    }
}
