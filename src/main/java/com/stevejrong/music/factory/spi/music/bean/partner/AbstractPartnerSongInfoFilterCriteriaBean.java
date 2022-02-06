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
package com.stevejrong.music.factory.spi.music.bean.partner;

import com.stevejrong.music.factory.spi.music.bean.AbstractFilterBean;

/**
 * @author Steve Jrong
 * @since 1.0
 */
public class AbstractPartnerSongInfoFilterCriteriaBean extends AbstractFilterBean {
    /**
     * 搜索歌曲关键字
     */
    protected String searchKeywords;

    public String getSearchKeywords() {
        return searchKeywords;
    }

    public void setSearchKeywords(String searchKeywords) {
        this.searchKeywords = searchKeywords;
    }

    public AbstractPartnerSongInfoFilterCriteriaBean() {
        super();
    }
}