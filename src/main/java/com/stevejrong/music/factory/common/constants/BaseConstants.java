package com.stevejrong.music.factory.common.constants;

import com.stevejrong.music.factory.util.DateTimeUtil;
import com.stevejrong.music.factory.util.FileUtil;

import java.io.File;

public class BaseConstants {

    /**
     * 东八区时区
     */
    public static final String UTC_GMT8 = "+8";

    /**
     * Java8中，东八区的时区ID
     */
    public static final String UTC_GMT8_ZONE_ID = "Asia/Shanghai";

    /**
     * JSON格式的提交数据方式
     */
    public static final String APPLICATION_JSON = "application/json";

    /**
     * XML格式的提交数据方式
     */
    public static final String APPLICATION_XML = "application/xml";

    public static final String CONTENT_TYPE = "Content-Type";

    public static final String APPLICATION_JSON_UTF_8 = APPLICATION_JSON.concat(";").concat("charset=utf-8");

    public static final String FLAC_FILE_SUFFIX = ".flac";

    public static final String MP3_FILE_SUFFIX = ".mp3";

    public static final String ARTWORK_TEMP_DIRECTORY = FileUtil.getProjectAbsolutePath() + File.separator + "temp" + File.separator + "artwork";

    public static final String MODULE_LOG_DIRECTORY = FileUtil.getProjectAbsolutePath() + File.separator + "log";

    public static final String ANALYSIS_ORIGINAL_MUSIC_FILE_MODULE_LOG_NAME = "analysis_original_music_file_module_log_"
            .concat(DateTimeUtil.formatDate(DateTimeUtil.YYYYMMDD_HHMMSS_FORMAT_WITHOUT_SYMBOL, DateTimeUtil.getDateByNow()));

    public static final String COMPLEMENTS_SUCCESS_MUSIC_INFO_MODULE_LOG_NAME = "complements_success_music_info_module_log_"
            .concat(DateTimeUtil.formatDate(DateTimeUtil.YYYYMMDD_HHMMSS_FORMAT_WITHOUT_SYMBOL, DateTimeUtil.getDateByNow()));

    public static final String COMPLEMENTS_FAILURE_SUCCESS_MUSIC_INFO_MODULE_LOG_NAME = "complements_failure_music_info_module_log_"
            .concat(DateTimeUtil.formatDate(DateTimeUtil.YYYYMMDD_HHMMSS_FORMAT_WITHOUT_SYMBOL, DateTimeUtil.getDateByNow()));
}
