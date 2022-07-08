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
package com.stevejrong.music.factory.spi.music.bo.parallel;

import com.stevejrong.music.factory.spi.service.music.parallel.IMultiThreadedTaskProcessor;

/**
 * 多线程任务处理抽象类Bo
 *
 * @author Steve Jrong
 * @since 1.0
 */
public abstract class AbstractMultiThreadedTaskBo {

    /**
     * 自定义的实际任务ID
     */
    protected long taskId;

    /**
     * 自定义的实际任务名称
     */
    protected String taskName;

    /**
     * 此任务所属的顶级接口（在系统中被称为XXX器接口）
     * <p>
     * 即继承了IMultiThreadedTaskProcessor接口的接口
     */
    protected IMultiThreadedTaskProcessor<?> multiThreadedTaskProcessor;

    protected AbstractMultiThreadedTaskBo(long taskId, String taskName, IMultiThreadedTaskProcessor<?> multiThreadedTaskProcessor) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.multiThreadedTaskProcessor = multiThreadedTaskProcessor;
    }

    public IMultiThreadedTaskProcessor<?> getMultiThreadedTaskProcessor() {
        return multiThreadedTaskProcessor;
    }

    public long getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }
}