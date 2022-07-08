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

import com.stevejrong.music.factory.common.util.DateTimeUtil;
import com.stevejrong.music.factory.common.util.LoggerUtil;
import com.stevejrong.music.factory.spi.music.bo.parallel.AbstractMultiThreadedTaskBo;
import com.stevejrong.music.factory.spi.service.music.parallel.IMultiThreadedTaskProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 多线程任务处理的处理者（Worker）类
 *
 * @author Steve Jrong
 * @since 1.0
 */
public final class MultiThreadedTaskProcessingWorker implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(MultiThreadedTaskProcessingWorker.class);

    /**
     * 自定义处理者（Worker）的ID
     */
    private long workerId;

    /**
     * 自定义处理者（Worker）的名称
     */
    private String workerName;

    /**
     * 实际要执行的任务队列
     * <p>
     * 存放实际要处理的具体任务。
     * 因取出任务这一动作具有并发风险，故此集合类型必须为线程安全的。
     */
    private ConcurrentLinkedQueue<AbstractMultiThreadedTaskBo> multiThreadedTaskProcessingQueue = new ConcurrentLinkedQueue<>();

    /**
     * 任务处理完成后的结果集合
     * <p>
     * 将实际处理的具体任务ID做为Key、将处理具体任务后的自定义结果做为Value，存入此Map集合中。
     * 因将键值对放入Map这一动作具有并发风险，故此集合类型必须为线程安全的。
     */
    private ConcurrentHashMap<Long, Object> parallelExecuteResults = new ConcurrentHashMap<>();

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

    public ConcurrentLinkedQueue<AbstractMultiThreadedTaskBo> getMultiThreadedTaskProcessingQueue() {
        return multiThreadedTaskProcessingQueue;
    }

    public void setMultiThreadedTaskProcessingQueue(ConcurrentLinkedQueue<AbstractMultiThreadedTaskBo> multiThreadedTaskProcessingQueue) {
        this.multiThreadedTaskProcessingQueue = multiThreadedTaskProcessingQueue;
    }

    public ConcurrentHashMap<Long, Object> getParallelExecuteResults() {
        return parallelExecuteResults;
    }

    public void setParallelExecuteResults(ConcurrentHashMap<Long, Object> parallelExecuteResults) {
        this.parallelExecuteResults = parallelExecuteResults;
    }

    public MultiThreadedTaskProcessingWorker(long workerId, String workerName) {
        this.workerId = workerId;
        this.workerName = workerName;
    }

    @Override
    public void run() {
        while (true) {
            // 从实际要执行的任务队列中，取出一个，并出队
            AbstractMultiThreadedTaskBo multiThreadedTaskProcessingBo = this.multiThreadedTaskProcessingQueue.poll();

            if (null == multiThreadedTaskProcessingBo) {
                // 当实际要执行的任务队列中已没有任何待处理的任务时，多线程处理任务执行结束，跳出循环
                break;
            }

            long start = System.currentTimeMillis();
            boolean result = execute(multiThreadedTaskProcessingBo);

            // 将处理后的结果数据存入结果集合中
            this.parallelExecuteResults.put(multiThreadedTaskProcessingBo.getTaskId(), result);

            LOGGER.info(LoggerUtil.builder().append("multiThreadedTaskProcessingWorker_run", "多线程任务处理者（Worker）")
                    .append("executeMsg", String.format("【%s】→【%s】成功！本次处理结果：%b，本次处理耗时：%s。", this.workerName, multiThreadedTaskProcessingBo.getTaskName(),
                            result, DateTimeUtil.milliSecondToHHMMssString((System.currentTimeMillis() - start)))).toString());
        }
    }

    /**
     * 执行并处理多线程任务
     *
     * @param multiThreadedTaskProcessingBo 多线程任务处理抽象类的子类Bo。此形参，携带了执行具体任务时的各种自定义参数。
     * @return 音频文件格式转换结果。true - 音频文件格式转换成功; false - 音频文件格式转换失败。
     */
    private boolean execute(AbstractMultiThreadedTaskBo multiThreadedTaskProcessingBo) {
        try {
            IMultiThreadedTaskProcessor multiThreadedTaskProcessor = multiThreadedTaskProcessingBo.getMultiThreadedTaskProcessor();
            multiThreadedTaskProcessor.execute(multiThreadedTaskProcessingBo);
        } catch (Exception e) {
            LOGGER.error(LoggerUtil.builder().append("multiThreadedTaskProcessingWorker_execute", "执行并处理多线程任务异常")
                    .append("exception", e).append("exceptionMsg", e.getMessage())
                    .append("multiThreadedTaskProcessingBo", multiThreadedTaskProcessingBo)
                    .toString());

            return false;
        }

        return true;
    }
}