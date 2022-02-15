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
package com.stevejrong.music.factory.spi.service.music.formatConversion;

import com.stevejrong.music.factory.common.enums.SupportOSEnum;
import com.stevejrong.music.factory.common.util.FileUtil;
import com.stevejrong.music.factory.common.util.LoggerUtil;
import com.stevejrong.music.factory.common.util.PlatformUtil;
import com.stevejrong.music.factory.common.util.SpringBeanUtil;
import com.stevejrong.music.factory.config.SystemConfig;
import com.stevejrong.music.factory.spi.music.bo.formatConversion.FFmpegBuilderBo;
import com.sun.jna.Native;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Map;

/**
 * 适用于基于可执行UNIX管道命令的C-DLL库进行音频文件格式转换的音频格式转换器抽象类
 * <p>
 * 当音频文件格式转换器类，使用FFmpegJava包装类组件无法满足音频文件格式转换，或要执行的命令中含有UNIX管道特性时，需要继承此抽象类
 *
 * @author Steve Jrong
 * @since 1.0
 */
public abstract class AbstractAudioFileConverterUsePipeInDllFile extends AbstractAudioFileConverter implements IAudioFileConverter, IFormatConversionCommandExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractAudioFileConverterUsePipeInDllFile.class);

    /**
     * 要执行的FFmpeg音频转换命令
     */
    public String executeCommandString;

    public String getExecuteCommandString() {
        return executeCommandString;
    }

    public void setExecuteCommandString(String executeCommandString) {
        this.executeCommandString = executeCommandString;
    }

    @Override
    public FFmpegBuilder getFFmpegBuilder(FFmpegBuilderBo ffmpegBuilderBo) {
        return null;
    }

    /**
     * 为要执行音频文件转换的命令字符串，设置一个要替换的Map集合信息
     * <p>
     * 此Map集合的Key为要执行音频文件转换命令字符串中，待替换的字符串（以@符号开头），Value为要替换成的字符串
     *
     * @param ffmpegBuilderBo FFmpegBuilder的Bo对象
     * @return 要替换的Map集合信息
     */
    protected abstract Map<String, String> addReplaceStringsMapOfExecuteCommand(FFmpegBuilderBo ffmpegBuilderBo);

    @Override
    public String getDynamicLinkLibrariesFilePath() {
        SystemConfig systemConfig = SpringBeanUtil.getBean("systemConfig");
        Map<SupportOSEnum, String> formatConversionShellPathsByOSTypeMaps = systemConfig
                .getAudioFileFormatConversionConfig().getFormatConversionDllUtilsComponentConfig().getResourceFilePathsByOSType();
        SupportOSEnum operatingSystemEnum = PlatformUtil.getOperatingSystemType();

        File shellUtilDllFile = FileUtil.getResourceFile(formatConversionShellPathsByOSTypeMaps.get(operatingSystemEnum), ".so");
        return shellUtilDllFile.getAbsolutePath();
    }

    @Override
    public boolean convert(String sourcePath, String targetDirectory, String targetFileName) {
        FFmpegBuilderBo fFmpegBuilderBo = new FFmpegBuilderBo.Builder(sourcePath, targetDirectory, targetFileName, this.targetFileSuffix()).build();
        Map<String, String> replaceStringsMap = this.addReplaceStringsMapOfExecuteCommand(fFmpegBuilderBo);

        String currentExecuteCommandString = this.getExecuteCommandString();
        for (Map.Entry<String, String> item : replaceStringsMap.entrySet()) {
            currentExecuteCommandString = currentExecuteCommandString.replaceAll(item.getKey(), item.getValue());
        }

        String dynamicLinkLibrariesFilePath = this.getDynamicLinkLibrariesFilePath();
        IFormatConversionCommandExecutor formatConversionCommandExecutor = Native.loadLibrary(dynamicLinkLibrariesFilePath, IFormatConversionCommandExecutor.class);

        int result = formatConversionCommandExecutor.execute(currentExecuteCommandString);

        LOGGER.info(LoggerUtil.builder().append("abstractAudioFileConverterUsePipeInDllFile_convert", "成功地转换了音频文件")
                .append("result", result)
                .append("sourcePath", fFmpegBuilderBo.getSourcePath())
                .append("targetPath", fFmpegBuilderBo.getTargetDirectory() + File.separatorChar + fFmpegBuilderBo.getTargetFileName() + fFmpegBuilderBo.getTargetFileSuffix())
                .toString());

        return true;
    }

    @Override
    public int execute(String command) {
        return 0;
    }
}