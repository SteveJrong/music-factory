package com.stevejrong.music.factory.spi.service.music.filter;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.stevejrong.music.factory.common.util.SpringBeanUtil;
import com.stevejrong.music.factory.config.SystemConfig;
import com.stevejrong.music.factory.config.sub.FilterGroupsConfig;
import com.stevejrong.music.factory.spi.music.bean.AbstractFilterBean;
import com.stevejrong.music.factory.spi.music.bo.validator.filtrated.FiltratedResultBo;
import com.stevejrong.music.factory.spi.music.bo.validator.filtrated.FiltratedResultDataBo;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Chain - 音频文件歌曲信息验证过滤器链
 * <p>
 * 过滤器设计模式
 * <p>
 * Spring启动时加载所有校验器到Map里，运行时根据选择来决定要执行部分或全部的过滤器
 *
 * @author wangjing
 * create date: 2019-11-11 23:26
 * @since 1.0
 */
public class BaseFilterChain<T> {
    /**
     * 过滤器Map键值对集合
     * <p>
     * Key - 过滤器组标识；Value - 其下所有已启用的过滤器
     */
    private Map<String, List<? extends AbstractFilter>> filterGroupsMap = Maps.newHashMap();

    /**
     * 对外的过滤器过滤方法
     *
     * @param criteriaBean
     * @return
     */
    public List<FiltratedResultBo> filtrateParams(String filterGroupTag, AbstractFilterBean criteriaBean) {
        return filtrate(filterGroupTag, criteriaBean);
    }

    /**
     * 过滤器主方法
     *
     * @param criteriaBean
     * @return 验证结果<code>SongInfoValidateBo</code>类的集合
     */
    private List<FiltratedResultBo> filtrate(String filterGroupTag, AbstractFilterBean criteriaBean) {
        List<AbstractFilter> filters = getFiltersByFilterGroupTag(filterGroupTag);

        /*SystemConfig systemConfig = SpringBeanUtil.getBean("systemConfig");
        FilterGroupsConfig filterGroupConfig = systemConfig.getFilterGroupsConfig().stream().filter(config -> config.getFilterGroupTag().equals(filterGroupTag))
                .findAny().get();*/

        List<FiltratedResultBo> validateResultList = Lists.newArrayList();

        if (CollectionUtils.isNotEmpty(filters)) {
            for (AbstractFilter filter : filters) {
                FiltratedResultDataBo resultDataBo = (FiltratedResultDataBo) filter.filtrate(criteriaBean);
                criteriaBean.setRedirectDataOnPreview(Optional.ofNullable(resultDataBo.getData()).orElse(""));

                validateResultList.add(new FiltratedResultBo(filter.getClass(), resultDataBo));
            }
        }
        return validateResultList;
    }

    /**
     * 过滤器链构造方法
     * <p>
     * 过滤器链初始化时，将过滤器组加载到HashMap中
     */
    public BaseFilterChain() {
        SystemConfig systemConfig = SpringBeanUtil.getBean("systemConfig");

        for (FilterGroupsConfig config : systemConfig.getFilterGroupsConfig()) {
            this.filterGroupsMap.put(config.getFilterGroupTag(), config.getFilters());
        }
    }

    /**
     * 根据过滤器组标识，获取其所有已启用的过滤器
     *
     * @return 过滤器集合
     */
    private List<AbstractFilter> getFiltersByFilterGroupTag(String filterGroupTag) {
        for (Map.Entry item : this.filterGroupsMap.entrySet()) {
            if (filterGroupTag.equals(item.getKey())) {
                return ((List<AbstractFilter>) item.getValue())
                        .stream().filter(AbstractFilter::isStatus)
                        .sorted(Comparator.comparingInt(AbstractFilter::getOrder))
                        .collect(Collectors.toList());
            }
        }
        return null;
    }
}