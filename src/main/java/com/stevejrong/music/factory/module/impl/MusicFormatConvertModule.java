package com.stevejrong.music.factory.module.impl;

import com.stevejrong.music.factory.common.constants.BaseConstants;
import com.stevejrong.music.factory.common.enums.MusicFormatEnums;
import com.stevejrong.music.factory.common.exception.MusicConverterNotFoundException;
import com.stevejrong.music.factory.config.SystemConfig;
import com.stevejrong.music.factory.module.AbstractBusinessModule;
import com.stevejrong.music.factory.module.IBusinessModule;
import com.stevejrong.music.factory.module.bo.MusicFormatConvertModuleBo;
import com.stevejrong.music.factory.transcode.converter.IMusicFileConverter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * 音频文件格式转换
 * 作用：将音频文件转换为新的格式
 */
public class MusicFormatConvertModule extends AbstractBusinessModule implements IBusinessModule<List<MusicFormatConvertModuleBo>> {

    /**
     * 系统配置
     */
    private SystemConfig systemConfig;

    /**
     * 某一种受支持的音频格式，转换为FLAC音频格式，其音频转换器的使用规则
     * <map>标签中，元素的key表示原始音频文件的编码格式，此值对应MusicFormatEnums枚举中getEncodeFormat()方法中返回的编码格式值
     * 元素的value值表示原始音频文件转换为FLAC格式需要用到的音频转换器名称，此值对应音频转换器Bean的ID
     */
    private Map<String, String> rulesByMusicConverter;

    public SystemConfig getSystemConfig() {
        return systemConfig;
    }

    public void setSystemConfig(SystemConfig systemConfig) {
        this.systemConfig = systemConfig;
    }

    public Map<String, String> getRulesByMusicConverter() {
        return rulesByMusicConverter;
    }

    public void setRulesByMusicConverter(Map<String, String> rulesByMusicConverter) {
        this.rulesByMusicConverter = rulesByMusicConverter;
    }

    @Override
    public List<MusicFormatConvertModuleBo> doAction() {
        ApplicationContext context = new ClassPathXmlApplicationContext(systemConfig.getBaseConfig().getSpringConfigurationFileName());

        try {
            /**
             * 读取原始文件目录下的所有音频文件，依次进行转换
             * 读取时，排除文件后缀是FLAC的文件，以跳过转换
             */
            Files.newDirectoryStream(Paths.get(systemConfig.getAnalysisAndComplementsMusicInfoConfig().getMusicFileDirectoryOfOriginal()),
                    path -> path.toString().endsWith(BaseConstants.FILE_SUFFIX_M4A)
                            || path.toString().endsWith(BaseConstants.FILE_SUFFIX_WAV)
                            || path.toString().endsWith(BaseConstants.FILE_SUFFIX_APE))
                    .forEach(file -> {
                        // 遍历处理每个需要转换为FLAC格式的文件

                        // 根据音频文件后缀名查找到对应的编码格式
                        String encodeFormat = MusicFormatEnums.getEncodeFormatByFileSuffix(
                                file.getFileName().toString().substring(file.getFileName().toString().lastIndexOf(".")));

                        // 再根据编码格式查找到对应的音频转换器Bean名称
                        String sourceFormat;
                        IMusicFileConverter musicFileConverter = null;
                        for (Map.Entry<String, String> item : rulesByMusicConverter.entrySet()) {
                            sourceFormat = item.getKey();

                            if (sourceFormat.equals(encodeFormat)) {
                                musicFileConverter = (IMusicFileConverter) context.getBean(item.getValue());
                                break;
                            }
                        }

                        if (null == musicFileConverter) {
                            throw new MusicConverterNotFoundException();
                        }

                        // 验证环境
                        musicFileConverter.validateFFmpegCodecEnvironment();
                        // 执行转换
                        musicFileConverter.convert(systemConfig.getAnalysisAndComplementsMusicInfoConfig().getMusicFileDirectoryOfOriginal(),
                                systemConfig.getConvertMusicFileConfig().getMusicFileDirectoryOfConverted(),
                                systemConfig.getAnalysisAndComplementsMusicInfoConfig().getMusicFileDirectoryOfOriginal() + File.separator + file.getFileName().toString().substring(0, file.getFileName().toString().lastIndexOf(".")),
                                systemConfig.getConvertMusicFileConfig().getMusicFileDirectoryOfConverted() + File.separator + file.getFileName().toString().substring(0, file.getFileName().toString().lastIndexOf(".")),
                                file.getFileName().toString().substring(file.getFileName().toString().lastIndexOf(".")),
                                BaseConstants.FILE_SUFFIX_FLAC);
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
