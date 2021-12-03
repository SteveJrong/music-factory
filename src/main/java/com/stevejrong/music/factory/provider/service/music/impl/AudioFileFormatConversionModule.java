package com.stevejrong.music.factory.provider.service.music.impl;

import com.stevejrong.music.factory.common.constants.BaseConstants;
import com.stevejrong.music.factory.common.util.FileUtil;
import com.stevejrong.music.factory.common.util.LoggerUtil;
import com.stevejrong.music.factory.spi.music.bo.MusicFormatConvertModuleBo;
import com.stevejrong.music.factory.spi.service.music.AbstractMusicFactoryModule;
import com.stevejrong.music.factory.spi.service.music.IMusicFactoryModule;
import com.stevejrong.music.factory.spi.service.music.formatConversion.IAudioFileConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * Service Implements - 音频文件格式转换
 * <p>
 * 作用：将音频文件转换为另一指定格式
 */
public class AudioFileFormatConversionModule extends AbstractMusicFactoryModule implements IMusicFactoryModule<List<MusicFormatConvertModuleBo>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AudioFileFormatConversionModule.class);

    /**
     * 某一种受支持的音频格式，转换为FLAC音频格式，其音频转换器的使用规则
     * <map>标签中，元素的key表示原始音频文件的编码格式，此值对应MusicFormatEnums枚举中getEncodeFormat()方法中返回的编码格式值
     * 元素的value值表示原始音频文件转换为FLAC格式需要用到的音频转换器名称，此值对应音频转换器Bean的ID
     */
    private Map<String, String> rulesByMusicConverter;

    public Map<String, String> getRulesByMusicConverter() {
        return rulesByMusicConverter;
    }

    public void setRulesByMusicConverter(Map<String, String> rulesByMusicConverter) {
        this.rulesByMusicConverter = rulesByMusicConverter;
    }

    @Override
    public List<MusicFormatConvertModuleBo> doAction() {
        try {
            /*
             * 读取原始文件目录下的所有音频文件，依次进行转换
             * 读取时，排除文件后缀是FLAC的文件，以跳过转换
             */
            Files.newDirectoryStream(Paths.get(getSystemConfig().getAnalysingAndComplementsForAudioFileConfig().getAudioFileDirectory()),
                    path -> path.toString().endsWith(BaseConstants.FILE_SUFFIX_M4A)
                            || path.toString().endsWith(BaseConstants.FILE_SUFFIX_WAV)
                            || path.toString().endsWith(BaseConstants.FILE_SUFFIX_APE))
                    .forEach(file -> {
                        // 遍历处理每个需要转换为FLAC格式的文件


                        // 根据音频文件后缀名查找到对应的编码格式
                        String audioFormat = FileUtil.getFileSuffix(file.toAbsolutePath().toString());

                        LOGGER.info(LoggerUtil.builder().append("audioFileFormatConversionModule_doAction", "开始音频转换")
                                .append("filePath", file.toAbsolutePath()).toString());

                        // 再根据编码格式查找到对应的音频转换器Bean名称
                        IAudioFileConverter audioFileConverter = getSystemConfig().getAudioFileFormatConversionConfig().getAudioFileConverters()
                                .get(audioFormat);

                        // 执行转换
                        audioFileConverter.convert(getSystemConfig().getAnalysingAndComplementsForAudioFileConfig().getAudioFileDirectory(),
                                getSystemConfig().getAnalysingAndComplementsForAudioFileConfig().getAudioFileDirectory(),
                                getSystemConfig().getAnalysingAndComplementsForAudioFileConfig().getAudioFileDirectory() + File.separator + file.getFileName().toString().substring(0, file.getFileName().toString().lastIndexOf(".")),
                                getSystemConfig().getAudioFileFormatConversionConfig().getConvertedAudioFileDirectory() + File.separator + file.getFileName().toString().substring(0, file.getFileName().toString().lastIndexOf(".")),
                                file.getFileName().toString().substring(file.getFileName().toString().lastIndexOf(".")),
                                BaseConstants.FILE_SUFFIX_FLAC);

                        LOGGER.info(LoggerUtil.builder().append("audioFileFormatConversionModule_doAction", "音频转换结束")
                                .append("filePath", file.toAbsolutePath()).toString());
                    });
        } catch (IOException e) {
            LOGGER.error(LoggerUtil.builder().append("audioFileFormatConversionModule_doAction", "音频转换")
                    .append("exception", e).append("exceptionMsg", e.getMessage()).toString());
        }

        return null;
    }
}
