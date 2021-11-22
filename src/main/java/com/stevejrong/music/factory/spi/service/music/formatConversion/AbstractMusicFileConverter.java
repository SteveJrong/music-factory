package com.stevejrong.music.factory.spi.service.music.formatConversion;

import com.stevejrong.music.factory.common.exception.FFmpegNotInstallException;
import com.stevejrong.music.factory.common.util.CommandUtil;

public abstract class AbstractMusicFileConverter implements IMusicFileConverter {

    @Override
    public void validateFFmpegCodecEnvironment() {
        String executeResult = CommandUtil.execute("ffmpeg", "-version", "/c", "dir");

        if (!executeResult.contains("ffmpeg version")) {
            throw new FFmpegNotInstallException();
        }
    }
}
