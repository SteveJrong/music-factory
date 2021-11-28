package com.stevejrong.music.factory.common.util;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.ResolvableType;

/**
 * Util - Spring Bean工具类
 *
 * @author Steve Jrong
 * create date: 2021-05-15 8:23 PM
 * @since 1.0
 */
public final class SpringBeanUtil {
    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-bean.xml");

    /**
     * 从静态变量applicationContext中得到Bean, 自动转型为所赋值对象的类型.
     */
    public static <T> T getBean(String name) {
        if (name == null || applicationContext == null) {
            return null;
        }

        return (T) applicationContext.getBean(name);
    }

    /**
     * 根据类型获取Spring Bean对象
     *
     * @param clazz 类型
     * @param <T>
     * @return Spring Bean对象
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * 根据Spring Bean对象获取Spring Bean的名称
     *
     * @param clazz 由Spring管理的Bean对象
     * @param <T>
     * @return 由Spring管理的Bean对象名称
     */
    public static <T> String getBeanNameByType(Class<T> clazz) {
        String[] beanNames = applicationContext.getBeanNamesForType(clazz);
        if (ArrayUtils.isNotEmpty(beanNames)) {
            return beanNames[0].trim();
        }

        return null;
    }
}
