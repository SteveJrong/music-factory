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
package com.stevejrong.music.factory.spi.service.music.analyzing.partner;

import com.stevejrong.music.factory.spi.service.music.filter.AbstractFilter;

/**
 * @author Steve Jrong
 * create date: 2021-11-18 12:18 AM
 * @since 1.0
 */
public abstract class AbstractPartnerSongInfoFilter<T, E> extends AbstractFilter<T, E> {

    /**
     * 请求第三方音乐服务平台获取音频元数据时的API地址
     */
    private String requestUrl;

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }
}