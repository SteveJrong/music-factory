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

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Worker子任务类
 * 主要被Master调用，来处理任务
 *
 * @author Steve Jrong
 * @since 1.0
 */
public class Worker implements Runnable {

    // 自定义的Worker子任务Id
    private long workerId;
    // 自定义的Worker子任务名称
    private String workerName;

    // 任务队列集合
    private ConcurrentLinkedQueue<Task> workQueue;
    // 子任务执行完后的结果集合
    private ConcurrentHashMap<Long, Object> results;

    public void setWorkQueue(ConcurrentLinkedQueue<Task> workQueue) {
        this.workQueue = workQueue;
    }

    public void setResults(ConcurrentHashMap<Long, Object> results) {
        this.results = results;
    }

    public long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public Worker(long workerId, String workerName) {
        this.workerId = workerId;
        this.workerName = workerName;
    }

    @Override
    public void run() {
        while (true) {
            // 将工作队列中的一个对象出队（获取并删除）
            Task task = this.workQueue.poll();

            if (null == task) {
                break;
            }

            long start = System.currentTimeMillis();
            boolean result = execute(task);

            // 将处理后的结果数据存入结果集合中
            this.results.put(task.getTaskId(), result);

            System.out.println(String.format("【%s】处理【%s】成功！处理结果：%b，耗时：%d毫秒。", this.workerName, task.getTaskName(),
                    result, (System.currentTimeMillis() - start)));
        }
    }

    /**
     * Worker子任务中，处理耗时任务的主方法
     *
     * @param task
     * @return
     */
    private boolean execute(Task task) {
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            return false;
        }

        return true;
    }
}