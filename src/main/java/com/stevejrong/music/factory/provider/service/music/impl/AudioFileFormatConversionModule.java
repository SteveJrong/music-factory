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
package com.stevejrong.music.factory.provider.service.music.impl;

import com.stevejrong.music.factory.common.util.DateTimeUtil;
import com.stevejrong.music.factory.common.util.HardwareUtil;
import com.stevejrong.music.factory.common.util.LoggerUtil;
import com.stevejrong.music.factory.common.util.RandomUtil;
import com.stevejrong.music.factory.provider.service.music.formatConversion.parallel.FormatConvertMaster;
import com.stevejrong.music.factory.spi.music.bo.AudioFileFormatConversionModuleBo;
import com.stevejrong.music.factory.spi.music.bo.formatConversion.FormatConvertTaskBo;
import com.stevejrong.music.factory.spi.service.music.AbstractMusicFactoryModule;
import com.stevejrong.music.factory.spi.service.music.IMusicFactoryModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * 音频文件格式转换
 * <p>
 * 将音频文件转换为另一指定格式
 *
 * @author Steve Jrong
 * @since 1.0
 */
public class AudioFileFormatConversionModule extends AbstractMusicFactoryModule implements IMusicFactoryModule<List<AudioFileFormatConversionModuleBo>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AudioFileFormatConversionModule.class);

    @Override
    public List<AudioFileFormatConversionModuleBo> doAction() {
        // 创建Master类对象
        FormatConvertMaster formatConvertMaster = new FormatConvertMaster(HardwareUtil.getAllCoresCountByCpu() * 2);

        try {
            // 读取原始文件目录下的所有音频文件，依次进行转换
            Files.newDirectoryStream(Paths.get(super.getSystemConfig().getAnalysingAndComplementsForAudioFileConfig().getAudioFileDirectory()),
                            path -> path.toString().endsWith(super.getSystemConfig().getAudioFileFormatConversionConfig().getCurrentAudioFileConverter().sourceFileSuffix()))
                    .forEach(file -> {
                        String sourcePath = file.toAbsolutePath().toString();
                        String targetDirectory = super.getSystemConfig().getAudioFileFormatConversionConfig().getConvertedAudioFileDirectory();

                        FormatConvertTaskBo formatConvertTask = new FormatConvertTaskBo(
                                RandomUtil.getARandomNumeric(1, Integer.MAX_VALUE),
                                "转换 [" + file.getFileName().toString() + "] 使用 [" + super.getSystemConfig().getAudioFileFormatConversionConfig().getCurrentAudioFileConverter().getClass().getSimpleName() + "] 转换器",
                                sourcePath,
                                targetDirectory,
                                super.getSystemConfig().getAudioFileFormatConversionConfig().getCurrentAudioFileConverter());

                        // 向Master提交任务，以使得Worker执行任务
                        formatConvertMaster.submit(formatConvertTask);
                    });

            long start = System.currentTimeMillis();
            // 使Master开启任务。依次以多线程的方式启动Worker子任务集合中实现创建好的Worker子任务对象。
            formatConvertMaster.start();

            while (true) {
                if (formatConvertMaster.hasComplete()) {
                    LOGGER.info(LoggerUtil.builder().append("audioFileFormatConversionModule_doAction", "音频文件全部转换完成")
                            .append("executeTotalCount", formatConvertMaster.getSumResult())
                            .append("totalTime", DateTimeUtil.milliSecondToHHMMssString((System.currentTimeMillis() - start)))
                            .toString());
                    break;
                }
            }
        } catch (Exception e) {
            LOGGER.error(LoggerUtil.builder().append("audioFileFormatConversionModule_doAction", "音频文件转换任务异常")
                    .append("exception", e).append("exceptionMsg", e.getMessage()).toString());
        }

        return null;
    }
}
