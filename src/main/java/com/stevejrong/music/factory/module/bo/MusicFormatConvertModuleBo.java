package com.stevejrong.music.factory.module.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MusicFormatConvertModuleBo implements Serializable {
    private static final long serialVersionUID = -7561630163510737142L;

    /**
     * 需要机型格式转换的音频文件的绝对路径
     */
    private String fileAbsolutePath;

    /**
     * 需要机型格式转换的音频文件的歌曲名称
     */
    private String songName;

    /**
     * 需要机型格式转换的音频文件的演唱者
     */
    private String singerName;

    /**
     * 需要机型格式转换的音频文件的音频格式
     */
    private String encodingType;

    /**
     * 需要机型格式转换的音频文件的音频比特率（Kbps）
     */
    private String bitRate;

    /**
     * 需要机型格式转换的音频文件的音频编码器
     */
    private boolean encoder;
}
