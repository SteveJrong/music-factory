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
package com.stevejrong.music.factory.spi.music.bo.parallel.formatConversion;

import com.stevejrong.music.factory.spi.music.bo.parallel.AbstractMultiThreadedTaskBo;
import com.stevejrong.music.factory.spi.service.music.parallel.IMultiThreadedTaskProcessor;

import java.io.Serializable;

/**
 * 多线程音频文件格式转换任务Bo
 *
 * @author Steve Jrong
 * @since 1.0
 */
public final class AudioFileFormatConversionTaskBo extends AbstractMultiThreadedTaskBo implements Serializable {
    private static final long serialVersionUID = 271312685529137822L;

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
     * 格式转换任务的构造方法
     * <p>
     * 以自定义的任务ID和自定义的任务名称，来创建一个Task任务。
     *
     * @param taskId                   自定义的任务ID
     * @param taskName                 自定义的任务名称
     * @param sourcePath
     * @param sourceFileName
     * @param targetDirectory
     */
    public AudioFileFormatConversionTaskBo(long taskId, String taskName, IMultiThreadedTaskProcessor<?> multiThreadedTaskProcessor, String sourcePath, String sourceFileName, String targetDirectory) {
        super(taskId, taskName, multiThreadedTaskProcessor);
        this.sourcePath = sourcePath;
        this.sourceFileName = sourceFileName;
        this.targetDirectory = targetDirectory;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getSourceFileName() {
        return sourceFileName;
    }

    public void setSourceFileName(String sourceFileName) {
        this.sourceFileName = sourceFileName;
    }

    public String getTargetDirectory() {
        return targetDirectory;
    }

    public void setTargetDirectory(String targetDirectory) {
        this.targetDirectory = targetDirectory;
    }

    @Override
    public String toString() {
        return "AudioFileFormatConversionTaskBo{" +
                "sourcePath='" + sourcePath + '\'' +
                ", sourceFileName='" + sourceFileName + '\'' +
                ", targetDirectory='" + targetDirectory + '\'' +
                '}';
    }
}