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
package com.stevejrong.music.factory.bean;

/**
 * 任务载体类
 * 主要承载任务信息
 *
 * @author Steve Jrong
 * @since 1.0
 */
public class Task {

    // 自定义的任务ID
    private long taskId;

    // 自定义的任务名称
    private String taskName;

    /**
     * Task任务构造方法。
     * 以自定义的任务ID和自定义的任务名称，来创建一个Task任务。
     *
     * @param taskId   自定义的任务ID
     * @param taskName 自定义的任务名称
     */
    public Task(long taskId, String taskName) {
        this.taskId = taskId;
        this.taskName = taskName;
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
}
