package com.stevejrong.music.factory.util;

import com.stevejrong.music.factory.common.constants.BaseConstants;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Util - 日期时间工具类
 */
public final class DateTimeUtil {

    public static final String YYYYMMDD_HHMMSS_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYYMMDD_HHMMSS_FORMAT_WITHOUT_SYMBOL = "yyyyMMdd HHmmss";
    public static final String YYYYMMDD_HHMMSS_FORMAT_WITH_SLASH = "yyyy/MM/dd HH:mm:ss";
    public static final String YYYYMMDD_HHMM_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MMDD_HHMM_FORMAT_WITH_SLASH = "yyyy/MM/dd HH:mm";

    /**
     * 获取当前时间
     *
     * @return LocalDateTime对象
     */
    private static LocalDateTime getNow() {
        return LocalDateTime.now();
    }

    /**
     * Date转LocalDateTime，带时区
     *
     * @param date
     * @return
     */
    private static LocalDateTime dateToLocalDateTime(Date date, String zoneId) {
        Instant instant = date.toInstant();
        return instant.atZone(ZoneId.of(zoneId)).toLocalDateTime();
    }

    /**
     * （以中国所在的东八区为准）获取当前时间的 java.util.Date 对象
     *
     * @return java.util.Date 对象
     */
    public static Date getDateByNow() {
        ZonedDateTime zonedDateTime = getNow().atZone(ZoneId.of(BaseConstants.UTC_GMT8_ZONE_ID));
        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * （以中国所在的东八区为准）获取当前时间的时间戳。单位毫秒
     *
     * @return Long类型的时间戳
     */
    public static Long getTimestampByNow() {
        Timestamp timestamp = Timestamp.valueOf(getNow());
        return timestamp.getTime();
    }

    /**
     * Date格式化为字符串
     *
     * @param datePattern 格式
     * @param date        Date对象
     * @return 时间字符串
     */
    public static String formatDate(String datePattern, Date date) {
        return DateTimeFormatter.ofPattern(datePattern).format(dateToLocalDateTime(date, BaseConstants.UTC_GMT8_ZONE_ID));
    }
}
