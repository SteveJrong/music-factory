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
package com.stevejrong.music.factory.boot;

import com.google.common.collect.Lists;
import com.stevejrong.music.factory.common.util.FileUtil;
import com.stevejrong.music.factory.common.util.FormatConverterUtil;
import com.stevejrong.music.factory.common.util.ReflectionUtil;
import com.stevejrong.music.factory.common.util.SpringBeanUtil;
import com.stevejrong.music.factory.config.SystemConfig;
import com.stevejrong.music.factory.config.sub.FilterGroupsConfig;
import com.stevejrong.music.factory.provider.service.music.impl.AlbumPictureCompressionModule;
import com.stevejrong.music.factory.provider.service.music.impl.AudioFileFormatConversionModule;
import com.stevejrong.music.factory.provider.service.music.impl.ComplementsInfoForAudioFileModule;
import com.stevejrong.music.factory.spi.music.bo.AnalyzingForAudioFileModuleBo;
import com.stevejrong.music.factory.spi.music.bo.ComplementedMetadataAudioFileBo;
import com.stevejrong.music.factory.spi.service.music.IMusicFactoryModule;
import com.stevejrong.music.factory.spi.service.music.filter.AbstractFilter;
import com.stevejrong.music.factory.spi.service.music.parallel.formatConversion.IAudioFileConverter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Scanner;

/**
 * 程序入口
 *
 * @author Steve Jrong
 * @since 1.0
 */
public class MusicFactoryApplication {
    public static void main(String[] args) {
        SystemConfig systemConfig = SpringBeanUtil.getBean("systemConfig");

        while (true) {
            FilterGroupsConfig filterGroupsConfig = systemConfig.getFilterGroupsConfig().stream()
                    .filter(filterGroup -> "analyzingInfoForAudioFileFilterConfig".equals(filterGroup.getFilterGroupTag()))
                    .findAny().get();

            System.out.println(systemConfig.getBaseConfig().getWelcomeMessage()
                    .replace("{{defaultAudioFileDirectory}}", systemConfig.getAnalysingAndComplementsForAudioFileConfig().getAudioFileDirectory())
                    .replace("{{currentDirectoryConfigInfo}}", buildCurrentDirectoryConfigInfo(systemConfig, filterGroupsConfig))
                    .replace("{{currentAnalysingAndComplementsFilterConfigInfo}}", buildCurrentAnalysingAndComplementsFilterConfigInfo(filterGroupsConfig))
                    .replace("{{currentFormatConverterConfigInfo}}", buildCurrentFormatConverterConfigInfo(systemConfig))
                    .replace("{{supportConvertFormatsInfoByFormatConversion}}", buildSupportConvertFormatsInfoByFormatConversion(systemConfig)));
            Scanner scanner1 = new Scanner(System.in);
            String input1 = scanner1.next();

            switch (input1) {
                case "1":
                    String defaultAudioFileDirectory = systemConfig.getAnalysingAndComplementsForAudioFileConfig().getAudioFileDirectory();
                    System.out.println(systemConfig.getBaseConfig().getCustomAudioFileDirectoryMessage()
                            .replace("{{defaultAudioFileDirectory}}", defaultAudioFileDirectory));

                    Scanner scanner2 = new Scanner(System.in);
                    String input2 = scanner2.next();

                    if (FileUtil.checkIsDirectory(input2)) {
                        systemConfig.getAnalysingAndComplementsForAudioFileConfig().setAudioFileDirectory(input2);

                        String newAudioFileDirectory = systemConfig.getAnalysingAndComplementsForAudioFileConfig().getAudioFileDirectory();
                        System.out.println(systemConfig.getBaseConfig().getCustomAudioFileDirectorySuccessMessage()
                                .replace("{{oldAudioFileDirectory}}", defaultAudioFileDirectory)
                                .replace("{{newAudioFileDirectory}}", newAudioFileDirectory));
                    } else {
                        System.err.println("设置原始音频文件目录位置失败！请填写存在且正确的原始音频文件目录位置。\n\n");
                    }
                    break;

                case "2":
                    // 分析原始音频文件，看哪些文件需要进行信息补全
                    IMusicFactoryModule<List<AnalyzingForAudioFileModuleBo>> analyzingInfoForAudioFileModule = SpringBeanUtil.getBean("analyzingInfoForAudioFileModule");
                    List<AnalyzingForAudioFileModuleBo> needComplementsAudioFileList = analyzingInfoForAudioFileModule.doAction();

                    System.out.println(systemConfig.getBaseConfig().getAnalyzingCompletedForAudioFileMessage()
                            .replace("{{audioFileCountForNeedComplements}}", needComplementsAudioFileList.size() + ""));
                    Scanner scanner3 = new Scanner(System.in);
                    String input3 = scanner3.next().toLowerCase();

                    switch (input3) {
                        case "y":
                            // 对缺失必要元数据信息的音频文件进行数据补全
                            ComplementsInfoForAudioFileModule complementsInfoForAudioFileModule = SpringBeanUtil.getBean("complementsInfoForAudioFileModule");
                            complementsInfoForAudioFileModule.setNeedComplementsAudioFileList(needComplementsAudioFileList);
                            List<ComplementedMetadataAudioFileBo> complementsResultsList = (List<ComplementedMetadataAudioFileBo>) complementsInfoForAudioFileModule.doAction();

                            int complementSuccessAudioFileCount = ((List<ComplementedMetadataAudioFileBo>) complementsResultsList.get(0)).size();
                            int complementFailureAudioFileCount = ((List<ComplementedMetadataAudioFileBo>) complementsResultsList.get(1)).size();

                            System.out.println(systemConfig.getBaseConfig().getMetadataInfoCompletedForAudioFileMessage()
                                    .replace("{{audioFileTotalCountForMetadataInfoCompleted}}",
                                            complementSuccessAudioFileCount + complementFailureAudioFileCount + "")
                                    .replace("{{audioFileCountForMetadataInfoCompletedSuccess}}", complementSuccessAudioFileCount + "")
                                    .replace("{{audioFileCountForMetadataInfoCompletedFailure}}", complementFailureAudioFileCount + "")
                            );
                            break;

                        case "n":
                            System.out.println("结束操作。\n\n");
                            break;
                    }

                    break;

                case "3":
                    String defaultConvertedAudioFileDirectory = systemConfig.getAudioFileFormatConversionConfig().getConvertedAudioFileDirectory();
                    System.out.println(systemConfig.getBaseConfig().getCustomConvertedAudioFileDirectoryMessage()
                            .replace("{{defaultConvertedAudioFileDirectory}}", defaultConvertedAudioFileDirectory));

                    Scanner scanner4 = new Scanner(System.in);
                    String input4 = scanner4.next();

                    if (FileUtil.checkIsDirectory(input4)) {
                        systemConfig.getAudioFileFormatConversionConfig().setConvertedAudioFileDirectory(input4);

                        String newConvertedAudioFileDirectory = systemConfig.getAudioFileFormatConversionConfig().getConvertedAudioFileDirectory();
                        System.out.println(systemConfig.getBaseConfig().getCustomConvertedAudioFileDirectorySuccessMessage()
                                .replace("{{oldConvertedAudioFileDirectory}}", defaultConvertedAudioFileDirectory)
                                .replace("{{newConvertedAudioFileDirectory}}", newConvertedAudioFileDirectory));
                    } else {
                        System.err.println("设置原始音频文件目录位置失败！请填写存在且正确的原始音频文件目录位置。\n\n");
                    }
                    break;

                case "4":
                    System.out.println(systemConfig.getBaseConfig().getSelectFormatConvertersMessage());

                    while (true) {
                        Scanner scanner5 = new Scanner(System.in);
                        String input5 = scanner5.next().toLowerCase().trim();

                        if ("n".equals(input5) || "y".equals(input5)) {
                            break;
                        }

                        if (!StringUtils.isNumeric(input5)) {
                            System.err.println("您输入的音频格式转换器编号格式非法，请重新输入！");
                            continue;
                        }

                        IAudioFileConverter selectAudioFileConverter = FormatConverterUtil.getAudioFileConverterByConverterNum(Integer.parseInt(input5), systemConfig);
                        if (null == selectAudioFileConverter) {
                            System.err.println("您选择的音频格式转换器不存在，请重新输入！");
                            continue;
                        }

                        if (CollectionUtils.isNotEmpty(systemConfig.getAudioFileFormatConversionConfig().getSelectedAudioFileConverters())
                                && checkExistsAudioFileConverterAtSelectedAudioFileConverters(selectAudioFileConverter, systemConfig.getAudioFileFormatConversionConfig().getSelectedAudioFileConverters())) {
                            System.err.println("您选择的音频格式转换器已存在，请重新输入！");
                            continue;
                        }

                        if (CollectionUtils.isEmpty(systemConfig.getAudioFileFormatConversionConfig().getSelectedAudioFileConverters())) {
                            systemConfig.getAudioFileFormatConversionConfig().setSelectedAudioFileConverters(Lists.newArrayList(selectAudioFileConverter));
                        } else {
                            systemConfig.getAudioFileFormatConversionConfig().getSelectedAudioFileConverters().add(selectAudioFileConverter);
                        }

                        System.out.println(systemConfig.getBaseConfig().getSelectFormatConvertersMessage()
                                .replace("{{selectFormatConverterSuccessMessage}}", selectAudioFileConverter.getClass().getSimpleName()));
                    }

                    break;

                case "5":
                    AudioFileFormatConversionModule formatConversionModule = SpringBeanUtil.getBean("audioFileFormatConversionModule");
                    formatConversionModule.doAction();
                    break;

                case "6":
                    String defaultCompressedAudioFileDirectory = systemConfig.getAlbumPictureCompressionConfig().getCompressedAudioFileDirectory();
                    System.out.println(systemConfig.getBaseConfig().getCustomCompressedAudioFileDirectoryMessage()
                            .replace("{{defaultCompressedAudioFileDirectory}}", defaultCompressedAudioFileDirectory));

                    Scanner scanner6 = new Scanner(System.in);
                    String input6 = scanner6.next();

                    if (FileUtil.checkIsDirectory(input6)) {
                        systemConfig.getAlbumPictureCompressionConfig().setCompressedAudioFileDirectory(input6);

                        String newCompressedAudioFileDirectory = systemConfig.getAnalysingAndComplementsForAudioFileConfig().getAudioFileDirectory();
                        System.out.println(systemConfig.getBaseConfig().getCustomCompressedAudioFileDirectorySuccessMessage()
                                .replace("{{oldCompressedAudioFileDirectory}}", defaultCompressedAudioFileDirectory)
                                .replace("{{newCompressedAudioFileDirectory}}", newCompressedAudioFileDirectory));
                    } else {
                        System.err.println("设置专辑封面压缩后的音频文件存放目录位置失败！请填写存在且正确的文件目录位置。\n\n");
                    }
                    break;

                case "7":
                    AlbumPictureCompressionModule albumPictureCompressionModule = SpringBeanUtil.getBean("albumPictureCompressionModule");
                    albumPictureCompressionModule.doAction();
                    break;

                case "0":
                    System.out.println("感谢您的使用！\n\n");
                    System.exit(0);

                default:
                    System.err.println("您输入的数字有误，请重新输入！\n");
                    break;
            }
        }
    }

    /**
     * 构建当前当前目录配置提示信息
     *
     * @param systemConfig
     * @return
     */
    private static String buildCurrentDirectoryConfigInfo(SystemConfig systemConfig, FilterGroupsConfig filterGroupsConfig) {
        StringBuilder sb = new StringBuilder("● 原始音频文件存放目录:\t"
                + systemConfig.getAnalysingAndComplementsForAudioFileConfig().getAudioFileDirectory() + "\n");
        sb.append("● 转换格式后的音频文件存放目录:\t").append(systemConfig.getAudioFileFormatConversionConfig().getConvertedAudioFileDirectory()).append("\n");
        sb.append("● 专辑封面压缩后的音频文件存放目录:\t").append(systemConfig.getAlbumPictureCompressionConfig().getCompressedAudioFileDirectory()).append("\n");

        return sb.toString();
    }

    /**
     * 构建分析和补全音乐信息过滤器配置提示信息
     *
     * @param filterGroupsConfig
     * @return
     */
    private static String buildCurrentAnalysingAndComplementsFilterConfigInfo(FilterGroupsConfig filterGroupsConfig) {
        StringBuilder sb = new StringBuilder();
        for (AbstractFilter filter : filterGroupsConfig.getFilters()) {
            sb.append("● ").append(SpringBeanUtil.getBeanNameByType(filter.getClass()))
                    .append(":\t").append(filter.isStatus() ? "已开启" : "已关闭").append("\n");
        }

        return sb.toString();
    }

    private static String buildCurrentFormatConverterConfigInfo(SystemConfig systemConfig) {
        StringBuilder sb = new StringBuilder("当前已选择的音频文件格式转换器：");

        List<IAudioFileConverter> selectedAudioFileConverters = systemConfig.getAudioFileFormatConversionConfig().getSelectedAudioFileConverters();

        if (CollectionUtils.isNotEmpty(selectedAudioFileConverters)) {
            for (IAudioFileConverter currentAudioFileConverter : selectedAudioFileConverters) {
                sb.append("● ")
                        .append(currentAudioFileConverter.converterNum())
                        .append(". ")
                        .append("[")
                        .append(currentAudioFileConverter.getClass().getSimpleName())
                        .append("]");
            }
        } else {
            sb.append("< 未选择音频文件格式转换器！请输入对应功能编号来设置！ >");
        }

        return sb.toString();
    }

    /**
     * 构建受支持的音频格式转换器提示信息
     *
     * @return
     */
    private static String buildSupportConvertFormatsInfoByFormatConversion(SystemConfig systemConfig) {
        List<IAudioFileConverter> audioFileConverters = systemConfig.getAudioFileFormatConversionConfig().getAudioFileConverters();
        StringBuilder sb = new StringBuilder();

        for (IAudioFileConverter audioFileConverter : audioFileConverters) {
            int converterNum = ReflectionUtil.getMethodValueByReflect("converterNum", audioFileConverter);

            sb.append("● ")
                    .append(converterNum)
                    .append(". [")
                    .append(audioFileConverter.getClass().getSimpleName())
                    .append("]")
                    .append("\n");
        }

        return sb.toString();
    }

    /**
     * 判断用户选择的音频文件转换器是否存在于用户已选择的音频文件转换器集合中
     *
     * @param selectAudioFileConverter    用户选择的音频转换器对象
     * @param selectedAudioFileConverters 用户已选择的音频转换器集合对象
     * @return true - 用户选择的音频文件转换器已存在于用户已选择的音频转换器集合中；false - 用户选择的音频文件转换器在用户已选择的音频转换器集合中不存在
     */
    private static boolean checkExistsAudioFileConverterAtSelectedAudioFileConverters(IAudioFileConverter selectAudioFileConverter, List<IAudioFileConverter> selectedAudioFileConverters) {
        for (IAudioFileConverter audioFileConverter : selectedAudioFileConverters) {
            if (selectAudioFileConverter.converterNum() == audioFileConverter.converterNum()) {
                return true;
            }
        }

        return false;
    }
}
