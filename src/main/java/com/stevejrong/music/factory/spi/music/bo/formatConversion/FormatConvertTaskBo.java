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
package com.stevejrong.music.factory.spi.music.bo.formatConversion;

import com.stevejrong.music.factory.common.util.FileUtil;
import com.stevejrong.music.factory.spi.service.music.formatConversion.IAudioFileConverter;

/**
 * 格式转换任务类
 *
 * @author Steve Jrong
 * @since 1.0
 */
public final class FormatConvertTaskBo {
    /**
     * 自定义的实际任务ID
     */
    private long taskId;

    /**
     * 自定义的实际任务名称
     */
    private String taskName;

    /**
     * 源音频文件位置
     */
    private String sourcePath;

    /**
     * 源音频文件文件名（不含后缀名）
     */
    private String sourceFileName;

    /**
     * 目标音频文件目录
     */
    private String targetDirectory;

    /**
     * 已选择的音频文件转换器
     */
    private IAudioFileConverter selectAudioFileConverter;

    /**
     * Task任务构造方法。
     * 以自定义的任务ID和自定义的任务名称，来创建一个Task任务。
     *
     * @param taskId   自定义的任务ID
     * @param taskName 自定义的任务名称
     */
    public FormatConvertTaskBo(long taskId, String taskName, String sourcePath, String targetDirectory, IAudioFileConverter selectAudioFileConverter) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.sourcePath = sourcePath;
        this.targetDirectory = targetDirectory;
        this.selectAudioFileConverter = selectAudioFileConverter;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getSourceFileName() {
        return FileUtil.getFileNameWithoutSuffix(sourcePath);
    }

    public String getTargetDirectory() {
        return targetDirectory;
    }

    public void setTargetDirectory(String targetDirectory) {
        this.targetDirectory = targetDirectory;
    }

    public IAudioFileConverter getSelectAudioFileConverter() {
        return selectAudioFileConverter;
    }

    public void setSelectAudioFileConverter(IAudioFileConverter selectAudioFileConverter) {
        this.selectAudioFileConverter = selectAudioFileConverter;
    }
}