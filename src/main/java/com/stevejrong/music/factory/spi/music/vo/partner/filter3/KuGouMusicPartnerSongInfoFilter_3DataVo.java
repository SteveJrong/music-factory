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
package com.stevejrong.music.factory.spi.music.vo.partner.filter3;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author Steve Jrong
 * @since 1.0
 */
public final class KuGouMusicPartnerSongInfoFilter_3DataVo implements Serializable {
    private static final long serialVersionUID = -6202916653439729991L;

    /**
     * 歌曲所属的专辑名称
     */
    private String album_name;

    /**
     * 歌曲所属的专辑封面
     */
    private String sizable_cover;

    /**
     * 歌曲所属专辑的艺术家集合
     */
    private List<KuGouMusicPartnerSongInfoFilter_3AuthorsVo> authors;

    /**
     * 歌曲所属专辑的发布时间
     */
    private String publish_date;

    /**
     * 歌曲所属专辑的描述
     */
    private String intro;

    /**
     * 歌曲所属专辑的语言类型
     */
    private String language;

    /**
     * 歌曲所属专辑的版权信息
     */
    private String publish_company;

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public String getSizable_cover() {
        return StringUtils.isNotBlank(this.sizable_cover) ? sizable_cover.replaceAll("/\\{size\\}/", "/") : null;
    }

    public void setSizable_cover(String sizable_cover) {
        this.sizable_cover = sizable_cover;
    }

    public List<KuGouMusicPartnerSongInfoFilter_3AuthorsVo> getAuthors() {
        return authors;
    }

    public void setAuthors(List<KuGouMusicPartnerSongInfoFilter_3AuthorsVo> authors) {
        this.authors = authors;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPublish_company() {
        return publish_company;
    }

    public void setPublish_company(String publish_company) {
        this.publish_company = publish_company;
    }

    @Override
    public String toString() {
        return "KuGouMusicPartnerSongInfoFilter_3DataVo{" +
                "album_name='" + album_name + '\'' +
                ", sizable_cover='" + sizable_cover + '\'' +
                ", publish_date='" + publish_date + '\'' +
                ", intro='" + intro + '\'' +
                ", language='" + language + '\'' +
                ", publish_company='" + publish_company + '\'' +
                '}';
    }
}