package com.stevejrong.music.factory.logs.service.impl;

import com.stevejrong.music.factory.common.constants.BaseConstants;
import com.stevejrong.music.factory.logs.service.IBusinessModuleLogService;
import com.stevejrong.music.factory.module.bo.ComplementedMetadataMusicFileBo;
import com.stevejrong.music.factory.util.DateTimeUtil;
import com.stevejrong.music.factory.util.FileUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 补全音乐元数据信息的日志记录业务类
 */
public class ComplementsMusicInfoModuleLogServiceImpl implements IBusinessModuleLogService {

    @Override
    public void afterReturnDoAction(Object... params) {
        List<ComplementedMetadataMusicFileBo> complementedSuccessMusicFileList = (List<ComplementedMetadataMusicFileBo>) ((List) params[0]).get(0);
        List<ComplementedMetadataMusicFileBo> complementedFailureMusicFileList = (List<ComplementedMetadataMusicFileBo>) ((List) params[0]).get(1);

        buildComplementsSuccessMusicFileLog(complementedSuccessMusicFileList);
        buildComplementsFailureMusicFileLog(complementedFailureMusicFileList);
    }

    /**
     * 构建已补全成功的音频文件信息的日志
     *
     * @param complementedSuccessMusicFileList
     */
    private void buildComplementsSuccessMusicFileLog(List<ComplementedMetadataMusicFileBo> complementedSuccessMusicFileList) {
        StringBuffer sbLog = new StringBuffer();
        sbLog.append("\n\n");
        sbLog.append(" ------------------------ ".concat(DateTimeUtil.formatDate(DateTimeUtil.YYYYMMDD_HHMMSS_FORMAT, DateTimeUtil.getDateByNow()))
                .concat(" - ComplementsMusicInfoModuleLog ------------------------\n"));
        sbLog.append("音频元数据已成功补全的音频文件信息：\n");
        sbLog.append(" ------------------------------------------------------------------------\n");
        sbLog.append(" 序号\t\t\t歌曲名称\t艺术家\t文件路径\n");
        sbLog.append(" ------------------------------------------------------------------------\n");

        for (int i = 0; i < complementedSuccessMusicFileList.size(); i++) {
            sbLog.append(String.valueOf(i + 1).concat("\t\t")
                    .concat(StringUtils.isNotEmpty(complementedSuccessMusicFileList.get(i).getSongName()) && StringUtils.isNotBlank(complementedSuccessMusicFileList.get(i).getSongName()) ? complementedSuccessMusicFileList.get(i).getSongName() : "未知曲目").concat("\t")
                    .concat(StringUtils.isNotEmpty(complementedSuccessMusicFileList.get(i).getSingerName()) && StringUtils.isNotBlank(complementedSuccessMusicFileList.get(i).getSingerName()) ? complementedSuccessMusicFileList.get(i).getSingerName() : "未知艺术家").concat("\t")
                    .concat(complementedSuccessMusicFileList.get(i).getFileAbsolutePath()).concat("\n"));
        }

        FileUtil.writeStringContentToFile(sbLog.toString(), ".log", BaseConstants.COMPLEMENTS_SUCCESS_MUSIC_INFO_MODULE_LOG_NAME,
                BaseConstants.MODULE_LOG_DIRECTORY);
    }

    /**
     * 构建已补全失败的音频文件信息的日志
     *
     * @param complementedFailureMusicFileList
     */
    private void buildComplementsFailureMusicFileLog(List<ComplementedMetadataMusicFileBo> complementedFailureMusicFileList) {
        StringBuffer sbLog = new StringBuffer();
        sbLog.append("\n\n");
        sbLog.append(" ------------------------ ".concat(DateTimeUtil.formatDate(DateTimeUtil.YYYYMMDD_HHMMSS_FORMAT, DateTimeUtil.getDateByNow()))
                .concat(" - ComplementsMusicInfoModuleLog ------------------------\n"));
        sbLog.append("音频元数据补全失败的音频文件信息：\n");
        sbLog.append(" ------------------------------------------------------------------------\n");
        sbLog.append(" 序号\t\t\t歌曲名称\t艺术家\t文件路径\n");
        sbLog.append(" ------------------------------------------------------------------------\n");

        for (int i = 0; i < complementedFailureMusicFileList.size(); i++) {
            sbLog.append(String.valueOf(i + 1).concat("\t\t")
                    .concat(StringUtils.isNotEmpty(complementedFailureMusicFileList.get(i).getSongName()) && StringUtils.isNotBlank(complementedFailureMusicFileList.get(i).getSongName()) ? complementedFailureMusicFileList.get(i).getSongName() : "未知曲目").concat("\t")
                    .concat(StringUtils.isNotEmpty(complementedFailureMusicFileList.get(i).getSingerName()) && StringUtils.isNotBlank(complementedFailureMusicFileList.get(i).getSingerName()) ? complementedFailureMusicFileList.get(i).getSingerName() : "未知艺术家").concat("\t")
                    .concat(complementedFailureMusicFileList.get(i).getFileAbsolutePath()).concat("\n"));
        }

        FileUtil.writeStringContentToFile(sbLog.toString(), ".log", BaseConstants.COMPLEMENTS_FAILURE_SUCCESS_MUSIC_INFO_MODULE_LOG_NAME,
                BaseConstants.MODULE_LOG_DIRECTORY);
    }

    @Override
    public void afterExceptionDoAction(Object... params) {
    }
}
