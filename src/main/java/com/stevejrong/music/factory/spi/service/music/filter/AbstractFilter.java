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
package com.stevejrong.music.factory.spi.service.music.filter;

/**
 * Abstract Class - 抽象的音频文件歌曲信息过滤器类
 * <p>
 * 用于规范过滤器的行为
 *
 * @author wangjing
 * create date: 2019-11-11 23:13
 * @since 1.0
 */
public abstract class AbstractFilter<T, E> {

    /**
     * 过滤器执行顺序
     */
    private int order;

    /**
     * 过滤器状态
     * <p>
     * true - 已启用；false - 已禁用
     */
    private boolean status;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * 主过滤方法
     * <p>
     * 由各个具体的过滤器做实现
     *
     * @param criteriaBean
     * @return
     */
    public abstract E filtrate(T criteriaBean);
}