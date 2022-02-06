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
package com.stevejrong.music.factory.config.sub;

import com.stevejrong.music.factory.spi.music.bean.partner.AbstractPartnerSongInfoFilterCriteriaBean;
import com.stevejrong.music.factory.spi.service.music.analyzing.partner.resolver.IPartnerSongInfoResolver;
import com.stevejrong.music.factory.spi.service.music.filter.AbstractFilter;

import java.util.List;

/**
 * 过滤器组配置类
 *
 * @author Steve Jrong
 * @since 1.0
 */
public class FilterGroupsConfig {

    /**
     * 过滤器组标识
     */
    private String filterGroupTag;

    /**
     * 适用于使用第三方音乐服务平台查询音乐元数据信息时的抽象查询Bean类的子实例
     */
    private AbstractPartnerSongInfoFilterCriteriaBean partnerSongInfoFilterCriteriaBean;

    /**
     * 是否要转发本次的执行结果到下一个过滤器中
     * <p>
     * 适用于顺序请求第三方音频服务平台API获取音频元数据信息的过滤器系列，有时一个API不能完全请求出全部的音频元数据信息，而是要分别请求多个API，最终合成。
     * <p>
     * true - 本次开启转发；false - 本次不转发。
     */
    private boolean redirectThisExecuteResultToNext;

    /**
     * 第三方音频服务平台的音频元数据解析器Bean
     * <p>
     * 仅适用于第三方音频服务平台的音频元数据解析器设置
     */
    private IPartnerSongInfoResolver partnerSongInfoResolverBean;

    /**
     * 过滤器组下的所有过滤器
     */
    private List<? extends AbstractFilter> filters;

    public String getFilterGroupTag() {
        return filterGroupTag;
    }

    public void setFilterGroupTag(String filterGroupTag) {
        this.filterGroupTag = filterGroupTag;
    }

    public AbstractPartnerSongInfoFilterCriteriaBean getPartnerSongInfoFilterCriteriaBean() {
        return partnerSongInfoFilterCriteriaBean;
    }

    public void setPartnerSongInfoFilterCriteriaBean(AbstractPartnerSongInfoFilterCriteriaBean partnerSongInfoFilterCriteriaBean) {
        this.partnerSongInfoFilterCriteriaBean = partnerSongInfoFilterCriteriaBean;
    }

    public boolean isRedirectThisExecuteResultToNext() {
        return redirectThisExecuteResultToNext;
    }

    public void setRedirectThisExecuteResultToNext(boolean redirectThisExecuteResultToNext) {
        this.redirectThisExecuteResultToNext = redirectThisExecuteResultToNext;
    }

    public IPartnerSongInfoResolver getPartnerSongInfoResolverBean() {
        return partnerSongInfoResolverBean;
    }

    public void setPartnerSongInfoResolverBean(IPartnerSongInfoResolver partnerSongInfoResolverBean) {
        this.partnerSongInfoResolverBean = partnerSongInfoResolverBean;
    }

    public List<? extends AbstractFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<? extends AbstractFilter> filters) {
        this.filters = filters;
    }
}