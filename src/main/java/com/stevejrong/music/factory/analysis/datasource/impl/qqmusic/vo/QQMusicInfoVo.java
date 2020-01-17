package com.stevejrong.music.factory.analysis.datasource.impl.qqmusic.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class QQMusicInfoVo implements Serializable {
    private static final long serialVersionUID = 6097337163212410790L;

    /**
     * QQ音乐API返回的返回码
     */
    private Integer code;

    /**
     * QQ音乐API返回的数据信息
     */
    private QQMusicInfoDataVo data;
}
