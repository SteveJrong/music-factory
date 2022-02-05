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
package com.stevejrong.music.factory.test;

import com.stevejrong.music.factory.bean.Master;
import com.stevejrong.music.factory.bean.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Master-Worker测试类
 *
 * @author Steve Jrong
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-bean.xml")
public class MasterWorkerTestCase {

    @Test
    public void masterWorkerTest() {
        // 指定Worker子任务数量的来创建Master对象
        // 这里传入几个数字，就代表有几个Worker子任务对象，就表示在处理实际任务时，有几个Worker子任务对象来处理任务。
        // 也就是说，如果实际的任务数 > Worker子任务对象时，Worker子任务对象会往复切换处理实际任务，其实就是Worker子任务对象不够用的情况，会导致处理任务耗时变长
        Master master = new Master(100);

        for (int i = 0; i < 10; i++) {
            // 循环创建10个Task任务
            Task task = new Task(i, "任务-" + (i + 1));

            // 然后像Master提交任务，以使得Worker执行任务
            master.submit(task);
        }

        long start = System.currentTimeMillis();
        // 使Master开启任务。依次以多线程的方式启动Worker子任务集合中实现创建好的Worker子任务对象。
        master.start();

        while (true) {
            if (master.hasComplete()) {
                System.out.println(String.format("执行结果合计：%d。耗时总计：%d毫秒。", master.getSumResult(),
                        (System.currentTimeMillis() - start)));
                break;
            }
        }
    }
}
