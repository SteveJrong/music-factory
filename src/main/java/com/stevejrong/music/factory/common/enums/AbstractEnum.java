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
package com.stevejrong.music.factory.common.enums;

/**
 * 公共抽象枚举类
 *
 * @author Steve Jrong
 * @since 1.0
 */
public interface AbstractEnum {

    /**
     * 获取枚举编号
     * <p>
     * 当枚举的ordinal()值无法满足实际需要，需要自行定义枚举编号时使用
     *
     * @return 枚举的自定义编号
     */
    default Integer getCode() {
        return null;
    }

    /**
     * 获取枚举值
     * <p>
     * 定义枚举携带的值
     *
     * @return 枚举携带的值
     */
    default String getValue() {
        return null;
    }

    /**
     * 获取枚举描述
     *
     * @return 枚举的描述
     */
    default String getDesc() {
        return null;
    }
}
