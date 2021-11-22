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