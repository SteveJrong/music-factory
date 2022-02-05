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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Master主管类
 * 主要分配一定数量的Worker子任务类，去多线程处理任务
 *
 * @author Steve Jrong
 * @since 1.0
 */
public class Master {
    // 任务队列集合
    private ConcurrentLinkedQueue<Task> workQueue = new ConcurrentLinkedQueue<>();

    // Worker子任务集合
    private Map<Long, Thread> workers = Maps.newHashMap();

    // 子任务执行完后的结果集合
    private ConcurrentHashMap<Long, Object> results = new ConcurrentHashMap<>();

    /**
     * Master构造方法。创建Master对象时，指定创建子任务的数量。
     *
     * @param num 指定创建子任务的数量
     */
    public Master(int num) {
        for (int i = 0; i < num; i++) {

            // 创建Worker子任务。以便由Master分配Worker去进行多线程处理任务。
            Worker worker = new Worker(i, "Worker子任务-" + i);
            // 设置Worker子任务执行完后的结果集合对象。用于保存每个Worker执行完后的结果数据。
            worker.setResults(results);
            // 设置任务队列集合对象
            worker.setWorkQueue(workQueue);

            // 以Key为阿拉伯数字、Value为多线程的Worker子任务对象，将其放入Worker子任务集合中。
            this.workers.put((long) i, new Thread(worker));
        }
    }

    /**
     * 提交任务方法
     *
     * @param task
     */
    public void submit(Task task) {
        this.workQueue.add(task);
    }

    /**
     * 开始任务方法
     */
    public void start() {
        for (Map.Entry<Long, Thread> item : this.workers.entrySet()) {
            // 依次启动Master构造方法中，向Worker子任务集合中放入的多线程Worker子任务
            item.getValue().start();
        }
    }

    /**
     * 判断所有线程是否都执行完毕的方法
     *
     * @return true - 全部执行完毕；false - 未执行完毕
     */
    public boolean hasComplete() {
        for (Map.Entry<Long, Thread> item : this.workers.entrySet()) {
            if (item.getValue().getState() != Thread.State.TERMINATED) {
                // 当某个线程执行完毕后，它的状态应为terminated。若不是，则表明还未执行完毕
                return false;
            }
        }

        return true;
    }

    /**
     * 获取任务执行总结果的方法
     *
     * @return 实际的结果值合计
     */
    public long getSumResult() {
        long value = 0;

        for (Map.Entry<Long, Object> item : this.results.entrySet()) {
            // 当某个结果是true时，记作1，否则记作0
            value += ((boolean) item.getValue()) ? 1 : 0;
        }

        return value;
    }
}
