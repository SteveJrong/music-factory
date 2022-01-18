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

import com.stevejrong.music.factory.common.enums.SupportOSForFFmpegEnum;

public final class PlatformUtil {

    /**
     * 获取当前操作系统类型
     *
     * @return 受支持的操作系统类型枚举
     */
    public static SupportOSForFFmpegEnum getOperatingSystemType() {
        String osString = System.getProperty("os.name").toLowerCase();

        if (osString.contains("linux")) {
            return SupportOSForFFmpegEnum.LINUX;
        }

        if ((osString.indexOf("mac") >= 0 && osString.indexOf("os") > 0 && osString.indexOf("x") < 0)
                || (osString.indexOf("mac") >= 0 && osString.indexOf("os") > 0 && osString.indexOf("x") > 0)) {
            return SupportOSForFFmpegEnum.APPLE_MAC_OS;
        }

        if (osString.contains("windows")) {
            return SupportOSForFFmpegEnum.MICROSOFT_WINDOWS_NT;
        }

        return null;
    }
}
