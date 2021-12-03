package com.stevejrong.music.factory.spi.service.music.formatConversion;

public interface IAudioFileConverter {

    /**
     * 转换音频文件
     *
     * @param sourceDirectory  待转换文件的路径
     * @param targetDirectory  转换后保存文件的路径
     * @param sourceFileName   音频文件的原始名称
     * @param targetFileName   音频文件的目标名称
     * @param sourceFileFormat 音频文件的原始格式
     * @param targetFileFormat 音频文件的目标格式
     * @return
     */
    String convert(String sourceDirectory, String targetDirectory, String sourceFileName, String targetFileName,
                   String sourceFileFormat, String targetFileFormat);
}
