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

import com.stevejrong.music.factory.common.util.FileUtil;
import com.stevejrong.music.factory.common.util.SpringBeanUtil;
import com.stevejrong.music.factory.config.SystemConfig;
import com.stevejrong.music.factory.config.sub.FilterGroupsConfig;
import com.stevejrong.music.factory.provider.service.music.impl.AudioFileFormatConversionModule;
import com.stevejrong.music.factory.provider.service.music.impl.ComplementsInfoForAudioFileModule;
import com.stevejrong.music.factory.spi.music.bo.AnalyzingForAudioFileModuleBo;
import com.stevejrong.music.factory.spi.music.bo.ComplementedMetadataAudioFileBo;
import com.stevejrong.music.factory.spi.service.music.IMusicFactoryModule;
import com.stevejrong.music.factory.spi.service.music.filter.AbstractFilter;

import java.util.List;
import java.util.Scanner;

/**
 * 程序入口
 */
public class MusicFactoryApplication {
    public static void main(String[] args) {
        SystemConfig systemConfig = SpringBeanUtil.getBean("systemConfig");

        while (true) {
            System.out.println(systemConfig.getBaseConfig().getWelcomeMessage()
                    .replace("{{defaultAudioFileDirectory}}", systemConfig.getAnalysingAndComplementsForAudioFileConfig().getAudioFileDirectory())
                    .replace("{{currentCustomConfigInfo}}", buildCurrentCustomConfigInfo(systemConfig)));
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
                    AudioFileFormatConversionModule formatConversionModule = SpringBeanUtil.getBean("audioFileFormatConversionModule");
                    formatConversionModule.doAction();
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
     * 构建当前用户配置的提示信息
     *
     * @param systemConfig
     * @return
     */
    private static String buildCurrentCustomConfigInfo(SystemConfig systemConfig) {
        FilterGroupsConfig filterGroupsConfig = systemConfig.getFilterGroupsConfig().stream()
                .filter(filterGroup -> "analyzingInfoForAudioFileFilterConfig".equals(filterGroup.getFilterGroupTag()))
                .findAny().get();

        StringBuilder sb = new StringBuilder("* 原始音频文件存放目录:\t"
                + systemConfig.getAnalysingAndComplementsForAudioFileConfig().getAudioFileDirectory() + "\n");
        sb.append("* 转换格式后的音频文件存放目录:\t").append(systemConfig.getAudioFileFormatConversionConfig().getConvertedAudioFileDirectory()).append("\n");

        for (AbstractFilter filter : filterGroupsConfig.getFilters()) {
            sb.append("* ").append(SpringBeanUtil.getBeanNameByType(filter.getClass()))
                    .append(":\t").append(filter.isStatus() ? "已开启" : "已关闭").append("\n");
        }

        return sb.toString();
    }
}
