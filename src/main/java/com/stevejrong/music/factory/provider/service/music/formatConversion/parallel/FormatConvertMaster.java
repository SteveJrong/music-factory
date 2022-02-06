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
package com.stevejrong.music.factory.provider.service.music.formatConversion.parallel;

import com.google.common.collect.Maps;
import com.stevejrong.music.factory.spi.music.bo.formatConversion.FormatConvertTaskBo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 多线程格式转换的Master类
 *
 * @author Steve Jrong
 * @since 1.0
 */
public class FormatConvertMaster {
    /**
     * 实际要执行的任务队列。
     * <p>
     * 存放实际要处理的具体任务。
     * 因取出任务这一动作具有并发风险，故此集合类型必须为线程安全的。
     */
    private ConcurrentLinkedQueue<FormatConvertTaskBo> formatConvertTaskQueue = new ConcurrentLinkedQueue<>();

    /**
     * FormatConvertWorker子任务对象Map集合。
     * <p>
     * 存放处理任务的处理者（Worker）。实际的任务不是由Master直接去处理，而是要委派给Worker去处理。
     */
    private Map<Long, Thread> formatConvertWorkers = Maps.newHashMap();

    /**
     * 任务处理完成后的结果集合。
     * <p>
     * 将实际处理的具体任务ID做为Key、将处理具体任务后的自定义结果做为Value，存入此Map集合中。
     * 因将键值对放入Map这一动作具有并发风险，故此集合类型必须为线程安全的。
     */
    private ConcurrentHashMap<Long, Object> parallelExecuteResults = new ConcurrentHashMap<>();

    /**
     * FormatConvertMaster类的构造方法。
     * <p>
     * 创建Master对象时，指定创建处理任务的处理者（Worker）的数量。
     *
     * @param createWorkerCount 指定创建处理任务的处理者（Worker）的数量。
     *                          此参数值将决定创建任务处理者（Worker）的数量。即当多线程处理任务时，有几个线程同时并行执行处理。
     *                          建议设置为主机的核心数值（含超线程核心数）。
     */
    public FormatConvertMaster(int createWorkerCount) {
        for (int i = 0; i < createWorkerCount; i++) {

            // 创建Worker子任务。以便由Master分配Worker去进行多线程处理任务。
            FormatConvertWorker formatConvertWorker = new FormatConvertWorker(i, "处理者（Worker）-" + i);
            // 设置Worker子任务执行完后的结果集合对象。用于保存每个Worker执行完后的结果数据。
            formatConvertWorker.setParallelExecuteResults(this.parallelExecuteResults);
            // 设置任务队列集合对象
            formatConvertWorker.setFormatConvertTaskQueue(this.formatConvertTaskQueue);

            // 以Key为数字、Value为多线程的Worker子任务对象，将其放入Worker子任务集合中。
            this.formatConvertWorkers.put((long) i, new Thread(formatConvertWorker));
        }
    }

    /**
     * 提交任务方法
     *
     * @param formatConvertTask
     */
    public void submit(FormatConvertTaskBo formatConvertTask) {
        this.formatConvertTaskQueue.add(formatConvertTask);
    }

    /**
     * 开始任务方法
     */
    public void start() {
        for (Map.Entry<Long, Thread> item : this.formatConvertWorkers.entrySet()) {
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
        for (Map.Entry<Long, Thread> item : this.formatConvertWorkers.entrySet()) {
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

        for (Map.Entry<Long, Object> item : this.parallelExecuteResults.entrySet()) {
            // 当某个结果是true时，记作1，否则记作0
            value += ((boolean) item.getValue()) ? 1 : 0;
        }

        return value;
    }
}