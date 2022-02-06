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
package com.stevejrong.music.factory.spi.service.music.formatConversion;

public interface IAudioFileConverter {

    /**
     * 音频文件格式转换器序号
     *
     * @return 音频文件格式转换器序号
     */
    int converterNum();

    /**
     * 源音频文件的文件后缀名
     *
     * @return 源音频文件的文件后缀名
     */
    String sourceFileSuffix();

    /**
     * 目标音频文件的文件后缀名
     *
     * @return 目标音频文件的文件后缀名
     */
    String targetFileSuffix();

    /**
     * 音频文件格式转换
     *
     * @param sourcePath      源文件位置
     * @param targetDirectory 目标文件目录
     * @param targetFileName  目标文件名称
     * @return 音频文件格式转换结果。true - 音频文件格式转换成功; false - 音频文件格式转换失败。
     */
    boolean convert(String sourcePath, String targetDirectory, String targetFileName);
}
