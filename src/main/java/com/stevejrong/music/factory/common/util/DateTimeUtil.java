package com.stevejrong.music.factory.common.util;

import com.stevejrong.music.factory.common.constants.BaseConstants;
import com.stevejrong.music.factory.common.enums.AbstractEnum;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Util - 日期时间工具类
 */
public final class DateTimeUtil {
    public static final Pattern DATE_PATTERN_OF_YYYY_FORMAT = Pattern.compile(DatePattern.YYYY_FORMAT.getDesc());
    public static final Pattern DATE_PATTERN_OF_YYYYMMDD_FORMAT = Pattern.compile(DateTimeUtil.DatePattern.YYYYMMDD_FORMAT.getDesc());
    public static final Pattern DATE_PATTERN_OF_YYYYMMDD_FORMAT_WITHOUT_SYMBOL = Pattern.compile(DateTimeUtil.DatePattern.YYYYMMDD_FORMAT_WITHOUT_SYMBOL.getDesc());

    /**
     * 日期时间格式化定义枚举
     */
    public enum DatePattern implements AbstractEnum {
        YYYY_FORMAT {
            @Override
            public String getDesc() {
                return "\\d{4}";
            }

            @Override
            public String getValue() {
                return "yyyy";
            }
        },

        YYYYMM_FORMAT {
            @Override
            public String getValue() {
                return "yyyy-MM";
            }
        },

        YYYYMMDD_FORMAT {
            @Override
            public String getDesc() {
                return "\\d{4}[-]\\d{1,2}[-]\\d{1,2}";
            }

            @Override
            public String getValue() {
                return "yyyy-MM-dd";
            }
        },

        YYYYMMDD_FORMAT_WITHOUT_SYMBOL {
            @Override
            public String getDesc() {
                return "\\d{4}\\d{2}\\d{2}";
            }

            @Override
            public String getValue() {
                return "yyyyMMdd";
            }
        },

        YYYYMMDD_HHMM_FORMAT {
            @Override
            public String getValue() {
                return "yyyy-MM-dd HH:mm";
            }
        },

        YYYY_MMDD_HHMM_FORMAT_WITH_SLASH {
            @Override
            public String getValue() {
                return "yyyy/MM/dd HH:mm";
            }
        },

        YYYYMMDD_HHMMSS_FORMAT {
            @Override
            public String getValue() {
                return "yyyy-MM-dd HH:mm:ss";
            }
        },

        YYYYMMDD_HHMMSS_FORMAT_WITHOUT_SYMBOL {
            @Override
            public String getValue() {
                return "yyyyMMdd HHmmss";
            }
        },
    }

    /**
     * 获取当前时间
     *
     * @return LocalDateTime对象
     */
    private static LocalDateTime getNowDateTime() {
        return LocalDateTime.now();
    }

    /**
     * 获取当前日期
     *
     * @return LocalDate对象
     */
    public static LocalDate getNowDate() {
        return LocalDate.now();
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
        ZonedDateTime zonedDateTime = getNowDateTime().atZone(ZoneId.of(BaseConstants.UTC_GMT8_ZONE_ID));
        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * （以中国所在的东八区为准）获取当前时间的时间戳。单位毫秒
     *
     * @return Long类型的时间戳
     */
    public static Long getTimestampByNow() {
        Timestamp timestamp = Timestamp.valueOf(getNowDateTime());
        return timestamp.getTime();
    }

    /**
     * Date格式化为字符串
     *
     * @param datePattern 格式
     * @param date        Date对象
     * @return 时间字符串
     */
    public static String dateToString(String datePattern, Date date) {
        return DateTimeFormatter.ofPattern(datePattern).format(dateToLocalDateTime(date, BaseConstants.UTC_GMT8_ZONE_ID));
    }

    /**
     * LocalDate格式化为字符串
     *
     * @param datePattern 格式
     * @param localDate   LocalDate对象
     * @return 时间字符串
     */
    public static String localDateToString(String datePattern, LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern(datePattern));
    }

    /**
     * 字符串格式化为LocalDate
     *
     * @param datePattern com.stevejrong.music.factory.common.util.DateTimeUtil.DatePattern类型的日期时间格式化定义
     * @param date        字符串格式的时间
     * @return java.time.LocalDate类型的日期时间
     */
    public static LocalDate stringToLocalDate(String datePattern, String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(datePattern));
    }
}
