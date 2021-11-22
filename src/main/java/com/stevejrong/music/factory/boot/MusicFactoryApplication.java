package com.stevejrong.music.factory.boot;

import com.stevejrong.music.factory.provider.service.music.impl.ComplementsInfoForAudioFileModule;
import com.stevejrong.music.factory.spi.music.bo.AnalyzingForAudioFileModuleBo;
import com.stevejrong.music.factory.spi.service.music.IMusicFactoryModule;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * 程序入口
 */
public class MusicFactoryApplication {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-bean.xml");

        // Setup 1：分析原始音频文件，看哪些文件需要进行信息补全
        IMusicFactoryModule<List<AnalyzingForAudioFileModuleBo>> analysisOriginalMusicFileModule = (IMusicFactoryModule<List<AnalyzingForAudioFileModuleBo>>) context.getBean("analyzingInfoForAudioFileModule");
        List<AnalyzingForAudioFileModuleBo> needComplementsMusicList = analysisOriginalMusicFileModule.doAction();

        // Setup 2：对缺失必要元数据信息的音频文件进行数据补全
        ComplementsInfoForAudioFileModule complementsInfoForAudioFileModule = (ComplementsInfoForAudioFileModule) context.getBean("complementsInfoForAudioFileModule");
        complementsInfoForAudioFileModule.setNeedComplementsMusicList(needComplementsMusicList);
        complementsInfoForAudioFileModule.doAction();

        /*// Setup 3：音频文件格式转换
        MusicFormatConvertModule musicFormatConvertModule = (MusicFormatConvertModule) context.getBean("musicFormatConvertModule");
        musicFormatConvertModule.doAction();*/
    }
}
