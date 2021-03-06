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
package com.stevejrong.music.factory.spi.music.vo.partner.filter2;

import java.io.Serializable;

/**
 * @author Steve Jrong
 * @since 1.0
 */
public final class KuGouMusicPartnerSongInfoFilter_2Vo implements Serializable {
    private static final long serialVersionUID = 7065703717613945542L;

    private int status;

    private int err_code;

    private KuGouMusicPartnerSongInfoFilter_2DataVo data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getErr_code() {
        return err_code;
    }

    public void setErr_code(int err_code) {
        this.err_code = err_code;
    }

    public KuGouMusicPartnerSongInfoFilter_2DataVo getData() {
        return data;
    }

    public void setData(KuGouMusicPartnerSongInfoFilter_2DataVo data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "KuGouMusicPartnerSongInfoFilter_2Vo{" +
                "status=" + status +
                ", err_code=" + err_code +
                '}';
    }
}