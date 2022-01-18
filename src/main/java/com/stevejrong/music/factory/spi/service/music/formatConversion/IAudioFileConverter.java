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
     * 转换音频文件
     *
     * @param sourceDirectory  待转换文件的路径
     * @param targetDirectory  转换后保存文件的路径
     * @param sourceFileName   音频文件的原始名称
     * @param targetFileName   音频文件的目标名称
     * @param sourceFileFormat 音频文件的原始格式
     * @param targetFileFormat 音频文件的目标格式
     * @return
     */
    String convert(String sourceDirectory, String targetDirectory, String sourceFileName, String targetFileName,
                   String sourceFileFormat, String targetFileFormat);
}
