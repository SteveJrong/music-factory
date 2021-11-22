/**
 * Copyright 2021 Steve Jrong
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
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
 * create date: 2021-11-19 6:15 PM
 * @since 1.0
 */
public class TestBean3 implements Serializable {
    private static final long serialVersionUID = 4744170027064577262L;

    private String name;

    private int serialNum;

    public String getName() {
        return name;
    }

    public int getSerialNum() {
        return serialNum;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSerialNum(int serialNum) {
        this.serialNum = serialNum;
    }
}