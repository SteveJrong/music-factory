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
package com.stevejrong.music.factory.common.constants;


/**
 * 公共常量类
 *
 * @author Steve Jrong
 * @since 1.0
 */
public final class BaseConstants {

    /**
     * 符号
     */
    public static final String COMMA_CHAR = ",";
    public static final String SEMICOLON_CHAR = ";";
    public static final String POINT_CHAR = ".";
    public static final String SPACE_CHAR = " ";

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

    public static final String FILE_SUFFIX_FLAC = "flac";
    public static final String FILE_SUFFIX_MP3 = "mp3";
    public static final String FILE_SUFFIX_M4A = "m4a";
    public static final String FILE_SUFFIX_APE = "ape";
    public static final String FILE_SUFFIX_WAV = "wav";
    public static final String FILE_SUFFIX_OGG = "ogg";

    public static final String AUDIO_ENCODE_FLAC = "flac";
    public static final String AUDIO_ENCODE_MPEG_LAYER_3 = "mp3";
    public static final String AUDIO_ENCODE_ALAC = "alac";
    public static final String AUDIO_ENCODE_AAC = "aac";
    public static final String AUDIO_ENCODE_APE = "ape";
    public static final String AUDIO_ENCODE_WAV = "wav";
    public static final String AUDIO_ENCODE_OGG_VORBIS = "libvorbis";
}
