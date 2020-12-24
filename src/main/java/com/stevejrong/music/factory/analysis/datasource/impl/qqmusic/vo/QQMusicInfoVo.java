package com.stevejrong.music.factory.analysis.datasource.impl.qqmusic.vo;

import java.io.Serializable;

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

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public QQMusicInfoDataVo getData() {
        return data;
    }

    public void setData(QQMusicInfoDataVo data) {
        this.data = data;
    }

    public QQMusicInfoVo() {
    }

    public QQMusicInfoVo(Integer code, QQMusicInfoDataVo data) {
        this.code = code;
        this.data = data;
    }
}
