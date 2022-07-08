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
package com.stevejrong.music.factory.spi.service.music.parallel.formatConversion;

import com.stevejrong.music.factory.spi.music.bo.parallel.formatConversion.AudioFileFormatConversionTaskBo;
import com.stevejrong.music.factory.spi.service.music.parallel.IMultiThreadedTaskProcessor;

/**
 * 音频文件格式转换器接口
 *
 * @author Steve Jrong
 * @since 1.0
 */
public interface IAudioFileConverter extends IMultiThreadedTaskProcessor<AudioFileFormatConversionTaskBo> {

    /**
     * 音频文件格式转换器序号
     *
     * @return 音频文件格式转换器序号
     */
    int converterNum();

    /**
     * 源音频文件的文件后缀名
     *
     * @return 源音频文件的文件后缀名
     */
    String sourceFileSuffix();

    /**
     * 目标音频文件的文件后缀名
     *
     * @return 目标音频文件的文件后缀名
     */
    String targetFileSuffix();

    @Override
    boolean execute(AudioFileFormatConversionTaskBo paramBo);
}
