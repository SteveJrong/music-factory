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

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * Java Bean工具类
 *
 * @author Steve Jrong
 * @since 1.0
 */
public final class BeanUtil<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BeanUtil.class);

    /**
     * 判断Java Bean中的属性值是否都为null值
     *
     * @param javaBean Java Bean对象
     * @return true - Java Bean中的属性值均为null值；false - Java Bean中的属性值不均为null值
     */
    public static <T> boolean checkAllFieldsIsNullValue(T javaBean) {
        if (ObjectUtils.isEmpty(javaBean)) {
            return true;
        }

        try {
            for (Field field : javaBean.getClass().getDeclaredFields()) {
                field.setAccessible(true);

                if (!"serialVersionUID".equals(field.getName())
                        && field.get(javaBean) != null && StringUtils.isNotBlank(field.get(javaBean).toString())) {
                    return false;
                }
            }
        } catch (Exception e) {
            LOGGER.error(LoggerUtil.builder().append("beanUtil_checkAllFieldsIsNullValue", "判断Java Bean中的属性值是否都为null值")
                    .append("exception", e).append("exceptionMsg", e.getMessage()).toString());
        }

        return true;
    }
}