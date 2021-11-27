package com.stevejrong.music.factory.common.util;

import com.stevejrong.music.factory.common.util.sub.LogBuilder;

import java.util.Collection;
import java.util.Map;

public final class LoggerUtil {
    public static String format(Map<String, ?> map) {
        return (new LogBuilder(map)).toString();
    }

    public static String format(String key, Collection<?> collection) {
        return (new LogBuilder(key, collection)).toString();
    }

    public static String format(String key, Object value) {
        return (new LogBuilder(key, value)).toString();
    }

    public static LogBuilder builder() {
        return new LogBuilder();
    }

    public static String toString(LogBuilder logBuilder) {
        return logBuilder.toString();
    }

}
