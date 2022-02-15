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
package com.stevejrong.music.factory.common.util;

import com.google.common.collect.Lists;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Bean映射转换工具类
 *
 * @author wangjing
 * @since 1.0
 */
public final class BeanMapperUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(BeanMapperUtil.class);

    private static DozerBeanMapper dozer = new DozerBeanMapper();

    private BeanMapperUtil() {
    }

    public static <T> T map(Object source, Class<T> destinationClass) {
        return source == null ? null : dozer.map(source, destinationClass);
    }

    public static <T> List<T> mapList(Collection<?> sourceList, Class<T> destinationClass) {
        List<T> destinationList = Lists.newArrayList();
        if (sourceList != null && sourceList.size() != 0) {
            Iterator var3 = sourceList.iterator();

            while (var3.hasNext()) {
                Object sourceObject = var3.next();
                T destinationObject = dozer.map(sourceObject, destinationClass);
                destinationList.add(destinationObject);
            }

            return destinationList;
        } else {
            return destinationList;
        }
    }

    /**
     * 类复制
     *
     * @param source      源类的对象
     * @param targetClass 目标类的对象
     * @param <T>         目标类的类型
     * @return
     */
    public static <T> T copy(Object source, Class<T> targetClass) {
        T target = null;
        try {
            target = targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.error(LoggerUtil.builder().append("beanMapperUtil_copy", "类复制")
                    .append("exception", e).append("exceptionMsg", e.getMessage()).toString());
        }

        dozer.map(source, target);
        return target;
    }

    /**
     * 获取Dozer的实例
     *
     * @return
     */
    public static Mapper getMapper() {
        return dozer;
    }
}
