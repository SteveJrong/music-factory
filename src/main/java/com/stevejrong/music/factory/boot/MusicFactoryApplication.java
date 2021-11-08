package com.stevejrong.music.factory.boot;

import com.stevejrong.music.factory.module.IBusinessModule;
import com.stevejrong.music.factory.module.bo.AnalysisOriginalMusicFileModuleBo;
import com.stevejrong.music.factory.module.impl.ComplementsMusicInfoModule;
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
        IBusinessModule<List<AnalysisOriginalMusicFileModuleBo>> analysisOriginalMusicFileModule = (IBusinessModule<List<AnalysisOriginalMusicFileModuleBo>>) context.getBean("analysisOriginalMusicFileModule");
        List<AnalysisOriginalMusicFileModuleBo> needComplementsMusicList = analysisOriginalMusicFileModule.doAction();

        // Setup 2：对缺失必要元数据信息的音频文件进行数据补全
        ComplementsMusicInfoModule complementsMusicInfoModule = (ComplementsMusicInfoModule) context.getBean("complementsMusicInfoModule");
        complementsMusicInfoModule.setNeedComplementsMusicList(needComplementsMusicList);
        complementsMusicInfoModule.doAction();

        /*// Setup 3：音频文件格式转换
        MusicFormatConvertModule musicFormatConvertModule = (MusicFormatConvertModule) context.getBean("musicFormatConvertModule");
        musicFormatConvertModule.doAction();*/
    }
}
