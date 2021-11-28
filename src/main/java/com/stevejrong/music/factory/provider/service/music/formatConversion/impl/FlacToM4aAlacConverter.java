package com.stevejrong.music.factory.provider.service.music.formatConversion.impl;

import com.stevejrong.music.factory.spi.service.music.formatConversion.AbstractMusicFileConverter;
import com.stevejrong.music.factory.spi.service.music.formatConversion.IMusicFileConverter;
import com.stevejrong.music.factory.common.util.CommandUtil;

import java.io.File;

/**
 * FLAC音频格式转换为M4A（Apple Lossless）音频格式转换器
 * 此转换器已弃用
 */
@Deprecated
public class FlacToM4aAlacConverter extends AbstractMusicFileConverter implements IMusicFileConverter {

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