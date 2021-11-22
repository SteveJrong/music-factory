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
package com.stevejrong.music.factory.spi.music.bo.partner.filter2;

import java.io.Serializable;

/**
 * @author Steve Jrong
 * create date: 2021-11-20 12:25 AM
 * @since 1.0
 */
public class KuGouMusicPartnerSongInfoFilter_2Bo implements Serializable {
    private static final long serialVersionUID = 7065703717613945542L;

    private int status;

    private int err_code;

    private KuGouMusicPartnerSongInfoFilter_2DataBo data;

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

    public KuGouMusicPartnerSongInfoFilter_2DataBo getData() {
        return data;
    }

    public void setData(KuGouMusicPartnerSongInfoFilter_2DataBo data) {
        this.data = data;
    }
}