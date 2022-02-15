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

import com.stevejrong.music.factory.common.enums.SupportOSEnum;

import java.util.Map;

/**
 * 不同操作系统平台的资源文件通用配置
 *
 * @author Steve Jrong
 * @since 1.0
 */
public class ResourcesByOperatingSystemConfig {

    /**
     * 对应不同操作系统平台的资源文件，其在项目资源目录中的位置
     * <p>
     * Key - 操作系统类型；Value - 对应操作系统平台下资源文件在项目资源目录中的位置
     */
    private Map<SupportOSEnum, String> resourceFilePathsByOSType;

    public Map<SupportOSEnum, String> getResourceFilePathsByOSType() {
        return resourceFilePathsByOSType;
    }

    public void setResourceFilePathsByOSType(Map<SupportOSEnum, String> resourceFilePathsByOSType) {
        this.resourceFilePathsByOSType = resourceFilePathsByOSType;
    }
}