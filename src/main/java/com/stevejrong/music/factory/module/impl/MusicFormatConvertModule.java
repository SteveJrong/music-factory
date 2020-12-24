package com.stevejrong.music.factory.module.impl;

import com.stevejrong.music.factory.common.constants.BaseConstants;
import com.stevejrong.music.factory.common.enums.MusicConverterEnums;
import com.stevejrong.music.factory.common.enums.MusicFormatEnums;
import com.stevejrong.music.factory.module.AbstractBusinessModule;
import com.stevejrong.music.factory.module.IBusinessModule;
import com.stevejrong.music.factory.module.bo.MusicFormatConvertModuleBo;
import com.stevejrong.music.factory.transcode.converter.IMusicFileConverter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
     * 待转换的音乐文件存放目录
     */
    private String musicFileDirectory;

    /**
     * 转换后的音乐文件存放目录
     */
    private String convertedMusicFileDirectory;

    /**
     * 转换规则
     * Key表示待转换的原始音频文件类型，Value表示要转换为的目标音频文件格式
     */
    private Map<String, String> convertRules;

    public String getMusicFileDirectory() {
        return musicFileDirectory;
    }

    public void setMusicFileDirectory(String musicFileDirectory) {
        this.musicFileDirectory = musicFileDirectory;
    }

    public String getConvertedMusicFileDirectory() {
        return convertedMusicFileDirectory;
    }

    public void setConvertedMusicFileDirectory(String convertedMusicFileDirectory) {
        this.convertedMusicFileDirectory = convertedMusicFileDirectory;
    }

    public Map<String, String> getConvertRules() {
        return convertRules;
    }

    public void setConvertRules(Map<String, String> convertRules) {
        this.convertRules = convertRules;
    }

    @Override
    public List<MusicFormatConvertModuleBo> doAction() {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-bean.xml");

        try {
            Files.newDirectoryStream(Paths.get(musicFileDirectory),
                    path -> path.toString().endsWith(BaseConstants.FLAC_FILE_SUFFIX))
                    .forEach(file -> {
                        String sourceFormat = null, targetFormat = null;

                        IMusicFileConverter musicFileConverter = null;
                        for (Map.Entry<String, String> item : convertRules.entrySet()) {
                            sourceFormat = item.getKey();
                            targetFormat = item.getValue();

                            musicFileConverter = (IMusicFileConverter) context.getBean(
                                    MusicConverterEnums.getMusicConverterBeanNameBySourceFormatAndTargetFormat(sourceFormat, targetFormat)
                            );

                            break;
                        }

                        // 验证环境
                        musicFileConverter.validateFFmpegCodecEnvironment();
                        // 执行转换
                        musicFileConverter.convert(musicFileDirectory, convertedMusicFileDirectory, file.getFileName().toString().substring(0, file.getFileName().toString().lastIndexOf(".")),
                                file.getFileName().toString().substring(0, file.getFileName().toString().lastIndexOf(".")), MusicFormatEnums.getFileSuffixByDesc(sourceFormat),
                                MusicFormatEnums.getFileSuffixByDesc(targetFormat));
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
