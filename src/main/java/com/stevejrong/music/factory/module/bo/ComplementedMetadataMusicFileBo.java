package com.stevejrong.music.factory.module.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ComplementedMetadataMusicFileBo implements Serializable {
    private static final long serialVersionUID = -423525799905277471L;

    /**
     * 已完成信息补全的音频文件的绝对路径
     */
    private String fileAbsolutePath;

    /**
     * 已完成信息补全的音频文件的歌曲名称
     */
    private String songName;

    /**
     * 已完成信息补全的音频文件的演唱者
     */
    private String singerName;

    /**
     * 类型。0-在补全信息期间发生异常的音频文件信息Bo；1-已完成信息补全的音频文件信息Bo
     */
    private Integer type;
}
