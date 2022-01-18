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

import java.io.Serializable;

/**
 * @author Steve Jrong
 * create date: 2021-11-19 5:39 PM
 * @since 1.0
 */
public class TestBean1 implements Serializable {
    private static final long serialVersionUID = -4025277714583296694L;

    private String name;

    private int serialNum;

    private TestBean2 bean2;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(int serialNum) {
        this.serialNum = serialNum;
    }

    public TestBean2 getBean2() {
        return bean2;
    }

    public void setBean2(TestBean2 bean2) {
        this.bean2 = bean2;
    }
}