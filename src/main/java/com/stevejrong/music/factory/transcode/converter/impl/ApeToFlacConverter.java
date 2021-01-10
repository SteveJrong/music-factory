package com.stevejrong.music.factory.transcode.converter.impl;

import com.stevejrong.music.factory.config.SystemConfig;
import com.stevejrong.music.factory.transcode.converter.AbstractMusicFileConverter;
import com.stevejrong.music.factory.transcode.converter.IMusicFileConverter;
import com.stevejrong.music.factory.util.FFmpegUtil;

/**
 * APE音频格式转换为FLAC音频格式转换器
 */
public class ApeToFlacConverter extends AbstractMusicFileConverter implements IMusicFileConverter {

    /**
     * 系统配置
     */
    private SystemConfig systemConfig;

    public SystemConfig getSystemConfig() {
        return systemConfig;
    }

    public void setSystemConfig(SystemConfig systemConfig) {
        this.systemConfig = systemConfig;
    }

    @Override
    public String convert(String sourceDirectory, String targetDirectory, String sourceFileName, String targetFileName,
                          String sourceFileFormat, String targetFileFormat) {
        FFmpegUtil.convertToFlacMusicFile(sourceFileName + sourceFileFormat, targetFileName + targetFileFormat,
                systemConfig.getConvertMusicFileConfig());

        return null;
    }
}
