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
package com.stevejrong.music.factory.spi.music.bo.validator.filtrated;

import java.io.Serializable;

/**
 * @author Steve Jrong
 * @since 1.0
 */
public class FiltratedResultDataBo<T> implements Serializable {
    private static final long serialVersionUID = -6122119187027696370L;

    /**
     * 结果标识符
     * <p>
     * true - 成功、存在；false - 失败、不存在
     */
    private final boolean result;

    /**
     * 结果携带数据
     */
    protected T data;

    /**
     * （当过滤器组中，已启用将本次过滤器的执行结果，转发到下一个过滤器中的配置后）
     */
    protected T previewData;

    public static class Builder<T> {
        /**
         * 结果标识符
         * <p>
         * true - 成功、存在；false - 失败、不存在
         */
        private final boolean result;

        /**
         * 结果携带数据
         */
        protected T data;

        public Builder(boolean result) {
            this.result = result;
        }

        public Builder<T> data(T val) {
            this.data = val;
            return this;
        }

        public FiltratedResultDataBo<T> build() {
            return new FiltratedResultDataBo<T>(this);
        }
    }

    private FiltratedResultDataBo(Builder<T> builder) {
        this.result = builder.result;
        this.data = builder.data;
    }

    public boolean isResult() {
        return result;
    }

    public T getData() {
        return data;
    }
}