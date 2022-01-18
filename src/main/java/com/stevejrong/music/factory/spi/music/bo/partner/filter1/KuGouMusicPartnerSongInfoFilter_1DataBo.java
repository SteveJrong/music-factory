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
package com.stevejrong.music.factory.spi.music.bo.partner.filter1;

import java.io.Serializable;
import java.util.List;

/**
 * @author Steve Jrong
 * create date: 2021-11-17 4:15 PM
 * @since 1.0
 */
public class KuGouMusicPartnerSongInfoFilter_1DataBo implements Serializable {
    private static final long serialVersionUID = 8976568556104769216L;

    private List<KuGouMusicPartnerSongInfoFilter_1InfoBo> info;

    public List<KuGouMusicPartnerSongInfoFilter_1InfoBo> getInfo() {
        return info;
    }

    public void setInfo(List<KuGouMusicPartnerSongInfoFilter_1InfoBo> info) {
        this.info = info;
    }
}