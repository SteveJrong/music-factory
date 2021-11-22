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
package com.stevejrong.music.factory.spi.music.bo.partner.filter3;

import java.io.Serializable;

/**
 * @author Steve Jrong
 * create date: 2021-11-20 10:43 AM
 * @since 1.0
 */
public class KuGouMusicPartnerSongInfoFilter_3AuthorsBo implements Serializable {
    private static final long serialVersionUID = -7465192983341832913L;

    /**
     * 歌曲所属专辑的艺术家
     */
    private String author_name;

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }
}