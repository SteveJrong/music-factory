package com.stevejrong.music.factory.transcode.converter.impl;

import com.stevejrong.music.factory.transcode.converter.AbstractMusicFileConverter;
import com.stevejrong.music.factory.transcode.converter.IMusicFileConverter;
import com.stevejrong.music.factory.util.CommandUtil;

import java.io.File;

public class FlacToM4aAlacConverter extends AbstractMusicFileConverter implements IMusicFileConverter {

    @Override
    public String convert(String sourceDirectory, String targetDirectory, String sourceFileName, String targetFileName, String sourceFileFormat, String targetFileFormat) {
        String sourceFilePath = "\"" + sourceDirectory + File.separator + sourceFileName + sourceFileFormat + "\" ";
        String targetFilePath = "\"" + targetDirectory + File.separator + sourceFileName + targetFileFormat + "\"";

        CommandUtil.execute("cmd /c start ffmpeg -nostdin -i "
                + sourceFilePath
                + "-c:a alac -c:v copy "
                + targetFilePath);

        return null;
    }
}
