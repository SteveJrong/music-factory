/**
 * Copyright 2021 Steve Jrong
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stevejrong.music.factory.spi.music.vo.partner.filter1;

import java.io.Serializable;

/**
 * @author Steve Jrong
 * create date: 2021-11-17 4:13 PM
 * @since 1.0
 */
public class KuGouMusicPartnerSongInfoFilter_1Vo implements Serializable {
    private static final long serialVersionUID = 87979449232775757L;

    /**
     * 状态码
     */
    private int status;

    /**
     * 错误信息
     */
    private String error;

    /**
     * "data"数据节点
     */
    private KuGouMusicPartnerSongInfoFilter_1DataVo data;

    /**
     * 错误码
     */
    private int errcode;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public KuGouMusicPartnerSongInfoFilter_1DataVo getData() {
        return data;
    }

    public void setData(KuGouMusicPartnerSongInfoFilter_1DataVo data) {
        this.data = data;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }
}