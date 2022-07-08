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

/**
 * @author Steve Jrong
 * @since 1.0
 */
public final class QQMusicInfoSingerDetailVo implements Serializable {
    private static final long serialVersionUID = -5853483927704623621L;

    /**
     * QQ音乐内部标识的歌手名称
     */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public QQMusicInfoSingerDetailVo() {
    }

    public QQMusicInfoSingerDetailVo(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "QQMusicInfoSingerDetailVo{" +
                "name='" + name + '\'' +
                '}';
    }
}
