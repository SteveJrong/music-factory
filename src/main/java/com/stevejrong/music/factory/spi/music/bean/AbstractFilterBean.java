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
package com.stevejrong.music.factory.spi.music.bean;

/**
 * @author Steve Jrong
 * create date: 2021-11-17 1:57 AM
 * @since 1.0
 */
public abstract class AbstractFilterBean {

    /**
     * 上一个过滤器转发来的数据对象
     */
    protected Object redirectDataOnPreview;

    protected AbstractFilterBean(Object redirectDataOnPreview) {
        this.redirectDataOnPreview = redirectDataOnPreview;
    }

    protected AbstractFilterBean() {
        this.redirectDataOnPreview = null;
    }

    public Object getRedirectDataOnPreview() {
        return redirectDataOnPreview;
    }

    public void setRedirectDataOnPreview(Object redirectDataOnPreview) {
        this.redirectDataOnPreview = redirectDataOnPreview;
    }
}