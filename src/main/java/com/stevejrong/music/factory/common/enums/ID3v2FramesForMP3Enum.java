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
package com.stevejrong.music.factory.common.enums;

/**
 * Enum - MP3音频文件的ID3v2标签帧类型枚举
 *
 * @author Steve Jrong
 * @since 1.0
 */
public enum ID3v2FramesForMP3Enum implements AbstractEnum {
    TIT2 {
        @Override
        public String getValue() {
            return "TIT2";
        }

        @Override
        public String getDesc() {
            return "歌曲标题";
        }
    },

    TPE1 {
        @Override
        public String getValue() {
            return "TPE1";
        }

        @Override
        public String getDesc() {
            return "歌曲艺术家";
        }
    },

    USLT {
        @Override
        public String getValue() {
            return "USLT";
        }

        @Override
        public String getDesc() {
            return "歌曲内嵌歌词";
        }
    },

    TALB {
        @Override
        public String getValue() {
            return "TALB";
        }

        @Override
        public String getDesc() {
            return "歌曲所属的专辑名称";
        }
    },

    TPE2 {
        @Override
        public String getValue() {
            return "TPE2";
        }

        @Override
        public String getDesc() {
            return "歌曲所属专辑的艺术家";
        }
    },

    COMM {
        @Override
        public String getValue() {
            return "COMM";
        }

        @Override
        public String getDesc() {
            return "歌曲所属专辑的描述";
        }
    },

    TLAN {
        @Override
        public String getValue() {
            return "TLAN";
        }

        @Override
        public String getDesc() {
            return "歌曲所属专辑的语言类型";
        }
    },

    TCOP {
        @Override
        public String getValue() {
            return "TCOP";
        }

        @Override
        public String getDesc() {
            return "歌曲所属专辑的版权信息";
        }
    },

    APIC {
        @Override
        public String getValue() {
            return "APIC";
        }

        @Override
        public String getDesc() {
            return "歌曲所属的专辑封面";
        }
    },

    TYERTDAT {
        @Override
        public String getValue() {
            return "TYERTDAT";
        }

        @Override
        public String getDesc() {
            return "歌曲所属专辑的发布时间";
        }
    }
}