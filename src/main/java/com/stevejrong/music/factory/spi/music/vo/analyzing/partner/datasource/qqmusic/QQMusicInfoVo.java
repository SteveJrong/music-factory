/*
 *             Copyright (C) 2022 Steve Jrong
 * 
 * 	   GitHub Homepage: https://www.github.com/SteveJrong
 *      Gitee Homepage: https://gitee.com/stevejrong1024
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stevejrong.music.factory.spi.music.vo.analyzing.partner.datasource.qqmusic;

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
