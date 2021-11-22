/**
 * Copyright 2019 Hangzhou HuiQia Network Technology Co., Ltd.
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
package com.stevejrong.music.factory.common.util;

import com.google.common.collect.Lists;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Util - Bean映射转换工具类
 *
 * @author wangjing
 * create date: 2018年08月25日 上午11:19
 * @since 1.0
 */
public class BeanMapperUtil {
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

    public static <T> T copy(Object source, Class<T> targetClass) {
        T target = null;
        try {
            target = targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        dozer.map(source, target);

        return target;
    }

    public static Mapper getMapper() {
        return dozer;
    }
}
