package com.stevejrong.music.factory.config.sub;

import java.util.List;
import java.util.Map;

/**
 * 分析和补全音乐信息配置类
 */
public class AnalysingAndComplementsForAudioFileConfig {

    /**
     * 音频文件存放目录
     */
    private String audioFileDirectory;

    /**
     * 不同的音频文件，对应的元数据解析器和存储器接口集合（解析器和存储器共用，通过Key值和Value的值类型来唯一确定某一个音频文件对应解析器还是存储器）
     * <p>
     * Key - 音频文件编码类型（如mp3、flac等）；Value - 解析器
     */
    private Map<String, List> audioFileMetadataResolvers;

    public String getAudioFileDirectory() {
        return audioFileDirectory;
    }

    public void setAudioFileDirectory(String audioFileDirectory) {
        this.audioFileDirectory = audioFileDirectory;
    }

    public Map<String, List> getAudioFileMetadataResolvers() {
        return audioFileMetadataResolvers;
    }

    public void setAudioFileMetadataResolvers(Map<String, List> audioFileMetadataResolvers) {
        this.audioFileMetadataResolvers = audioFileMetadataResolvers;
    }
}
