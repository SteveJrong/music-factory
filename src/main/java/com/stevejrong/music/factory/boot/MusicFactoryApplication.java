package com.stevejrong.music.factory.boot;

import com.stevejrong.music.factory.common.util.SpringBeanUtil;
import com.stevejrong.music.factory.config.sub.BaseConfig;
import com.stevejrong.music.factory.provider.service.music.impl.ComplementsInfoForAudioFileModule;
import com.stevejrong.music.factory.spi.music.bo.AnalyzingForAudioFileModuleBo;
import com.stevejrong.music.factory.spi.music.bo.ComplementedMetadataAudioFileBo;
import com.stevejrong.music.factory.spi.service.music.IMusicFactoryModule;

import java.util.List;
import java.util.Scanner;

/**
 * 程序入口
 */
public class MusicFactoryApplication {
    public static void main(String[] args) {
        BaseConfig config = SpringBeanUtil.getBean("baseConfig");

        while (true) {
            System.out.println(config.getWelcomeMessage());
            Scanner scanner1 = new Scanner(System.in);
            String input1 = scanner1.nextLine();

            switch (input1) {
                case "1":
                    // 分析原始音频文件，看哪些文件需要进行信息补全
                    IMusicFactoryModule<List<AnalyzingForAudioFileModuleBo>> analyzingInfoForAudioFileModule = SpringBeanUtil.getBean("analyzingInfoForAudioFileModule");
                    List<AnalyzingForAudioFileModuleBo> needComplementsAudioFileList = analyzingInfoForAudioFileModule.doAction();

                    System.out.println(config.getAnalyzingCompletedForAudioFileMessage()
                            .replace("{{audioFileCountForNeedComplements}}", needComplementsAudioFileList.size() + ""));
                    Scanner scanner2 = new Scanner(System.in);
                    String input2 = scanner2.nextLine().toLowerCase();

                    switch (input2) {
                        case "y":
                            // 对缺失必要元数据信息的音频文件进行数据补全
                            ComplementsInfoForAudioFileModule complementsInfoForAudioFileModule = SpringBeanUtil.getBean("complementsInfoForAudioFileModule");
                            complementsInfoForAudioFileModule.setNeedComplementsAudioFileList(needComplementsAudioFileList);
                            List<ComplementedMetadataAudioFileBo> complementsResultsList = (List<ComplementedMetadataAudioFileBo>) complementsInfoForAudioFileModule.doAction();

                            int complementSuccessAudioFileCount = ((List<ComplementedMetadataAudioFileBo>) complementsResultsList.get(0)).size();
                            int complementFailureAudioFileCount = ((List<ComplementedMetadataAudioFileBo>) complementsResultsList.get(1)).size();

                            System.out.println(config.getMetadataInfoCompletedForAudioFileMessage()
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

                case "2":
                    System.out.println("功能即将开放，尽请期待！\n\n");
                    // MusicFormatConvertModule musicFormatConvertModule = (MusicFormatConvertModule) context.getBean("musicFormatConvertModule");
                    // musicFormatConvertModule.doAction();
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
}
