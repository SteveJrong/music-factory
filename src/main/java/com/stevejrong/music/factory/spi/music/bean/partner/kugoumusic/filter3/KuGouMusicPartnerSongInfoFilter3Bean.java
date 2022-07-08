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
package com.stevejrong.music.factory.spi.music.bean.partner.kugoumusic.filter3;

import java.io.Serializable;
import java.util.List;

/**
 * @author Steve Jrong
 * @since 1.0
 */
public final class KuGouMusicPartnerSongInfoFilter3Bean implements Serializable {
    private static final long serialVersionUID = -9212758646108895684L;

    private int appid;

    private String clienttime;

    private int clientver;

    private List<KuGouMusicPartnerSongInfoFilter3DataBean> data;

    private String key = "";

    private String mid = "";

    public int getAppid() {
        return appid;
    }

    public void setAppid(int appid) {
        this.appid = appid;
    }

    public String getClienttime() {
        return clienttime;
    }

    public void setClienttime(String clienttime) {
        this.clienttime = clienttime;
    }

    public List<KuGouMusicPartnerSongInfoFilter3DataBean> getData() {
        return data;
    }

    public void setData(List<KuGouMusicPartnerSongInfoFilter3DataBean> data) {
        this.data = data;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public int getClientver() {
        return clientver;
    }

    public void setClientver(int clientver) {
        this.clientver = clientver;
    }

    public KuGouMusicPartnerSongInfoFilter3Bean(int appid, String clienttime, int clientver, List<KuGouMusicPartnerSongInfoFilter3DataBean> data) {
        this.appid = appid;
        this.clienttime = clienttime;
        this.clientver = clientver;
        this.data = data;
    }

    @Override
    public String toString() {
        return "KuGouMusicPartnerSongInfoFilter3Bean{" +
                "appid=" + appid +
                ", clienttime='" + clienttime + '\'' +
                ", clientver=" + clientver +
                ", data=" + data +
                ", key='" + key + '\'' +
                ", mid='" + mid + '\'' +
                '}';
    }
}