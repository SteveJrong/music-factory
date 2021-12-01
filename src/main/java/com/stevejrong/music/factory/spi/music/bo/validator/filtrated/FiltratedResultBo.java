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
package com.stevejrong.music.factory.spi.music.bo.validator.filtrated;

import com.stevejrong.music.factory.spi.service.music.filter.AbstractFilter;

import java.io.Serializable;

/**
 * @author Steve Jrong
 * create date: 2021-11-14 11:39 AM
 * @since 1.0
 */
public class FiltratedResultBo implements Serializable {
    private static final long serialVersionUID = -1591136590926715426L;

    private Class<? extends AbstractFilter> filterClass;

    private FiltratedResultDataBo filtratedResult;

    public Class<? extends AbstractFilter> getFilterClass() {
        return filterClass;
    }

    public void setFilterClass(Class<? extends AbstractFilter> filterClass) {
        this.filterClass = filterClass;
    }

    public FiltratedResultDataBo getFiltratedResult() {
        return filtratedResult;
    }

    public void setFiltratedResult(FiltratedResultDataBo filtratedResult) {
        this.filtratedResult = filtratedResult;
    }

    public FiltratedResultBo(Class<? extends AbstractFilter> filterClass, FiltratedResultDataBo filtratedResult) {
        this.filterClass = filterClass;
        this.filtratedResult = filtratedResult;
    }

    public FiltratedResultBo() {
    }
}