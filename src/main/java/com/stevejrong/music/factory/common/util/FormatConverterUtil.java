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

import com.stevejrong.music.factory.config.SystemConfig;
import com.stevejrong.music.factory.spi.service.music.formatConversion.IAudioFileConverter;

import java.util.List;

/**
 * 音频文件格式转换器工具类
 *
 * @author Steve Jrong
 * @since 1.0
 */
public final class FormatConverterUtil {

    /**
     * 根据音频文件格式转换器编号获取音频文件格式转换器对象
     *
     * @param converterNum 音频文件格式转换器编号
     * @param systemConfig 系统配置
     * @return 音频文件格式转换器对象
     */
    public static IAudioFileConverter getAudioFileConverterByConverterNum(int converterNum, SystemConfig systemConfig) {
        List<IAudioFileConverter> audioFileConverters = systemConfig.getAudioFileFormatConversionConfig().getAudioFileConverters();
        for (IAudioFileConverter audioFileConverter : audioFileConverters) {
            if (converterNum == audioFileConverter.converterNum()) {
                return audioFileConverter;
            }
        }

        return null;
    }

    /**
     * 根据源音频文件的文件后缀名获取音频文件格式转换器对象
     *
     * @param sourceFileSuffix 源音频文件的文件后缀名
     * @param systemConfig     系统配置
     * @return 音频文件格式转换器对象
     */
    public static IAudioFileConverter getAudioFileConverterBySourceFileSuffix(String sourceFileSuffix, SystemConfig systemConfig) {
        List<IAudioFileConverter> audioFileConverters = systemConfig.getAudioFileFormatConversionConfig().getAudioFileConverters();
        for (IAudioFileConverter audioFileConverter : audioFileConverters) {
            if (audioFileConverter.sourceFileSuffix().equals(sourceFileSuffix)) {
                return audioFileConverter;
            }
        }

        return null;
    }
}