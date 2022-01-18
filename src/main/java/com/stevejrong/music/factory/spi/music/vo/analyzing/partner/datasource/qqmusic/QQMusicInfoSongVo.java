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
import java.util.List;

public class QQMusicInfoSongVo implements Serializable {
    private static final long serialVersionUID = -5075105483046176264L;

    /**
     * QQ音乐内部标识的歌曲信息集合
     */
    private List<QQMusicInfoSongDetailVo> list;

    public List<QQMusicInfoSongDetailVo> getList() {
        return list;
    }

    public void setList(List<QQMusicInfoSongDetailVo> list) {
        this.list = list;
    }

    public QQMusicInfoSongVo() {
    }

    public QQMusicInfoSongVo(List<QQMusicInfoSongDetailVo> list) {
        this.list = list;
    }
}
