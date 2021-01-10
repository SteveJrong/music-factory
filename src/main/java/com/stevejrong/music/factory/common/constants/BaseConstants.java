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

    public static final String FILE_SUFFIX_FLAC = ".flac";
    public static final String FILE_SUFFIX_MP3 = ".mp3";
    public static final String FILE_SUFFIX_M4A = ".m4a";
    public static final String FILE_SUFFIX_APE = ".ape";
    public static final String FILE_SUFFIX_WAV = ".wav";

    public static final String MUSIC_ENCODE_FLAC = "flac";
    public static final String MUSIC_ENCODE_M4A_AAC = "m4a_aac";
    public static final String MUSIC_ENCODE_APE = "ape";
    public static final String MUSIC_ENCODE_WAV = "wav";

    public static final String ARTWORK_TEMP_DIRECTORY = FileUtil.getProjectAbsolutePath() + File.separator + "temp" + File.separator + "artwork";

    public static final String MODULE_LOG_DIRECTORY = FileUtil.getProjectAbsolutePath() + File.separator + "log";

    public static final String ANALYSIS_ORIGINAL_MUSIC_FILE_MODULE_LOG_NAME = "analysis_original_music_file_module_log_"
            .concat(DateTimeUtil.formatDate(DateTimeUtil.YYYYMMDD_HHMMSS_FORMAT_WITHOUT_SYMBOL, DateTimeUtil.getDateByNow()));

    public static final String COMPLEMENTS_SUCCESS_MUSIC_INFO_MODULE_LOG_NAME = "complements_success_music_info_module_log_"
            .concat(DateTimeUtil.formatDate(DateTimeUtil.YYYYMMDD_HHMMSS_FORMAT_WITHOUT_SYMBOL, DateTimeUtil.getDateByNow()));

    public static final String COMPLEMENTS_FAILURE_SUCCESS_MUSIC_INFO_MODULE_LOG_NAME = "complements_failure_music_info_module_log_"
            .concat(DateTimeUtil.formatDate(DateTimeUtil.YYYYMMDD_HHMMSS_FORMAT_WITHOUT_SYMBOL, DateTimeUtil.getDateByNow()));

    public static final String DEFAULT_ALBUM_PIC_FILE_PATH = FileUtil.getProjectAbsolutePath().concat(File.separator)
            .concat("src").concat(File.separator)
            .concat("main").concat(File.separator)
            .concat("resources").concat(File.separator)
            .concat("img").concat(File.separator)
            .concat("default_album_pic.jpg");
}
