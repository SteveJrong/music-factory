/*
 *             Copyright (C) 2022 Steve Jrong
 * 
 * 	   GitHub Homepage: https://www.github.com/SteveJrong
 *      Gitee Homepage: https://gitee.com/stevejrong1024
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stevejrong.music.factory.common.util.sub;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.stevejrong.music.factory.common.util.GsonUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public final class LogBuilder {
    private static final String SQUARE_BRACKETS_LEFT = "[";
    private static final String SQUARE_BRACKETS_RIGHT = "]";
    private static final String COLON = ":";
    private static final String SEPARATOR = " | ";
    private String key;
    private Object value;
    private Collection<?> collection;
    private StringBuilder logStringBuilder;
    private static final String BRACE_LEFT = "{";
    private static final String BRACE_RIGHT = "}";
    private static final String VERTICAL = "|";
    private static final String COMMA = ",";
    private static final String SPACE = " ";
    private static final String SEPARATOR_ALL = "] | [";

    public LogBuilder() {
        this.initJSONObject();
    }

    public LogBuilder(String key, Object value) {
        this.key = key;
        this.value = value;
        this.initJSONObject();
        this.append(key, value);
    }

    public LogBuilder(String key, Collection<?> collection) {
        this.key = key;
        this.collection = collection;
        this.initJSONObject();
        this.append(key, collection);
    }

    public LogBuilder(Map<String, ?> map) {
        this.initJSONObject();
        this.append(map);
    }

    public LogBuilder append(String key) {
        this.logStringBuilder.append("[").append(key).append("]")
                .append(" | ");
        return this;
    }

    public <T> LogBuilder append(String key, T value) {
        this.logStringBuilder.append("[").append(key).append(":")
                .append(GsonUtil.beanToJsonStringWithTypeAdapter(null != value ? value : ""))
                .append("]").append(" | ");
        return this;
    }

    public LogBuilder append(String key, Collection<?> collection) {
        this.logStringBuilder.append("[").append(key).append(":")
                .append(GsonUtil.beanToJsonStringWithTypeAdapter(CollectionUtils.isNotEmpty(collection) ? collection : Lists.newArrayList()))
                .append("]").append(" | ");
        return this;
    }

    public LogBuilder append(Map<String, ?> map) {
        Iterator var2 = map.entrySet().iterator();

        while (var2.hasNext()) {
            Map.Entry<String, ?> objectEntry = (Map.Entry) var2.next();
            this.append(objectEntry.getKey(), objectEntry.getValue());
        }

        return this;
    }

    @Override
    public String toString() {
        String logString = this.logStringBuilder.toString();
        return logString.contains(" | ") ? StringUtils.substringBeforeLast(logString, " | ") : logString;
    }

    private LogBuilder initJSONObject() {
        this.logStringBuilder = new StringBuilder();
        return this;
    }
}
