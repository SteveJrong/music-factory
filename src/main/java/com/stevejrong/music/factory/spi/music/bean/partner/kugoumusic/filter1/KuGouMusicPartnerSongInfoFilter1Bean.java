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
package com.stevejrong.music.factory.spi.music.bean.partner.kugoumusic.filter1;

import java.io.Serializable;

/**
 * @author Steve Jrong
 * @since 1.0
 */
public class KuGouMusicPartnerSongInfoFilter1Bean implements Serializable {
    private static final long serialVersionUID = 4570695505611684068L;

    /**
     * 请求的数据返回格式
     */
    private String format;

    /**
     * 搜索关键字
     */
    private String keyword;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public KuGouMusicPartnerSongInfoFilter1Bean(String format, String keyword) {
        this.format = format;
        this.keyword = keyword;
    }
}