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
package com.stevejrong.music.factory.spi.service.music.parallel.formatConversion;


import com.sun.jna.Library;

/**
 * 音频文件转换器转换时，所需调用的动态链接库接口
 *
 * @author Steve Jrong
 * @since 1.0
 */
public interface IFormatConversionCommandExecutor extends Library {

    /**
     * 获取音频文件转换器转换所需动态链接库文件的文件位置
     *
     * @return 动态链接库文件的文件位置
     */
    String getDynamicLinkLibrariesFilePath();

    /**
     * 调用执行C动态链接库文件中名为<code>execute(char *command)</code>的函数
     *
     * @param command 要执行的命令
     * @return 执行结果。
     */
    int execute(String command);
}