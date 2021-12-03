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
