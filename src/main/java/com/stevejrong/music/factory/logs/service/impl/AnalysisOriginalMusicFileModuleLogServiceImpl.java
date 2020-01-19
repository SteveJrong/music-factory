package com.stevejrong.music.factory.logs.service.impl;

import com.stevejrong.music.factory.common.constants.BaseConstants;
import com.stevejrong.music.factory.logs.service.IBusinessModuleLogService;
import com.stevejrong.music.factory.module.bo.AnalysisOriginalMusicFileModuleBo;
import com.stevejrong.music.factory.util.DateTimeUtil;
import com.stevejrong.music.factory.util.FileUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 分析原始音频文件的日志记录业务类
 */
public class AnalysisOriginalMusicFileModuleLogServiceImpl implements IBusinessModuleLogService {

    @Override
    public void afterReturnDoAction(Object... params) {
        List<AnalysisOriginalMusicFileModuleBo> needComplementsMusicList = (List<AnalysisOriginalMusicFileModuleBo>) params[0];

        StringBuffer sbLog = new StringBuffer();
        sbLog.append("\n\n");
        sbLog.append(" ------------------------ ".concat(DateTimeUtil.formatDate(DateTimeUtil.YYYYMMDD_HHMMSS_FORMAT, DateTimeUtil.getDateByNow()))
                .concat(" - AnalysisOriginalMusicFileModuleLog ------------------------\n"));
        sbLog.append("待补全信息的音频文件信息：\n");
        sbLog.append(" ------------------------------------------------------------------------\n");
        sbLog.append(" 序号\t\t\t歌曲名称\t艺术家\t文件路径\n");
        sbLog.append(" ------------------------------------------------------------------------\n");

        for (int i = 0; i < needComplementsMusicList.size(); i++) {
            sbLog.append(String.valueOf(i + 1).concat("\t\t")
                    .concat(StringUtils.isNotEmpty(needComplementsMusicList.get(i).getSongName()) && StringUtils.isNotBlank(needComplementsMusicList.get(i).getSongName()) ? needComplementsMusicList.get(i).getSongName() : "未知曲目").concat("\t")
                    .concat(StringUtils.isNotEmpty(needComplementsMusicList.get(i).getSingerName()) && StringUtils.isNotBlank(needComplementsMusicList.get(i).getSingerName()) ? needComplementsMusicList.get(i).getSingerName() : "未知艺术家").concat("\t")
                    .concat(needComplementsMusicList.get(i).getFileAbsolutePath()).concat("\n"));
        }

        FileUtil.writeStringContentToFile(sbLog.toString(), ".log", BaseConstants.ANALYSIS_ORIGINAL_MUSIC_FILE_MODULE_LOG_NAME,
                BaseConstants.MODULE_LOG_DIRECTORY);
    }

    @Override
    public void afterExceptionDoAction(Object... params) {

    }
}
