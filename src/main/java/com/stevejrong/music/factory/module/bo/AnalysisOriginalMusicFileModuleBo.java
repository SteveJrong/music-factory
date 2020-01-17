package com.stevejrong.music.factory.module.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AnalysisOriginalMusicFileModuleBo implements Serializable {
    private static final long serialVersionUID = -1591136590926715426L;

    /**
     * 需要做信息补全的音频文件的绝对路径
     */
    private String fileAbsolutePath;

    /**
     * 需要做信息补全的音频文件的文件名，不含后缀名
     */
    private String fileName;

    /**
     * 需要做信息补全的音频文件的歌曲名称
     */
    private String songName;

    /**
     * 需要做信息补全的音频文件的演唱者
     */
    private String singerName;
}
