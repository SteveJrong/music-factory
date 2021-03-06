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
package com.stevejrong.music.factory.spi.music.vo.partner.filter3;

import java.io.Serializable;
import java.util.List;

/**
 * @author Steve Jrong
 * @since 1.0
 */
public final class KuGouMusicPartnerSongInfoFilter_3Vo implements Serializable {
    private static final long serialVersionUID = 6642933774170485816L;

    private int status;

    private int error_code;

    private String err_msg;

    private List<KuGouMusicPartnerSongInfoFilter_3DataVo> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getErr_msg() {
        return err_msg;
    }

    public void setErr_msg(String err_msg) {
        this.err_msg = err_msg;
    }

    public List<KuGouMusicPartnerSongInfoFilter_3DataVo> getData() {
        return data;
    }

    public void setData(List<KuGouMusicPartnerSongInfoFilter_3DataVo> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "KuGouMusicPartnerSongInfoFilter_3Vo{" +
                "status=" + status +
                ", error_code=" + error_code +
                ", err_msg='" + err_msg + '\'' +
                '}';
    }
}