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
package com.stevejrong.music.factory.provider.service.music.analyzing.partner.resolver.impl;

import com.google.common.collect.Lists;
import com.stevejrong.music.factory.common.util.DateTimeUtil;
import com.stevejrong.music.factory.common.util.HttpUtil;
import com.stevejrong.music.factory.spi.music.bo.analyzing.datasource.PartnerSongInfoBo;
import com.stevejrong.music.factory.spi.music.bo.partner.filter1.KuGouMusicPartnerSongInfoFilter_1Bo;
import com.stevejrong.music.factory.spi.music.bo.partner.filter1.KuGouMusicPartnerSongInfoFilter_1InfoBo;
import com.stevejrong.music.factory.spi.music.bo.partner.filter2.KuGouMusicPartnerSongInfoFilter_2Bo;
import com.stevejrong.music.factory.spi.music.bo.partner.filter2.KuGouMusicPartnerSongInfoFilter_2DataBo;
import com.stevejrong.music.factory.spi.music.bo.partner.filter3.KuGouMusicPartnerSongInfoFilter_3AuthorsBo;
import com.stevejrong.music.factory.spi.music.bo.partner.filter3.KuGouMusicPartnerSongInfoFilter_3Bo;
import com.stevejrong.music.factory.spi.music.bo.partner.filter3.KuGouMusicPartnerSongInfoFilter_3DataBo;
import com.stevejrong.music.factory.spi.music.bo.validator.filtrated.FiltratedResultBo;
import com.stevejrong.music.factory.spi.service.music.analyzing.partner.resolver.IPartnerSongInfoResolver;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 第三方音乐服务提供商（酷狗）解析器
 *
 * @author Steve Jrong
 * @since 1.0
 */
public class KuGouPartnerSongInfoResolver implements IPartnerSongInfoResolver<List<FiltratedResultBo>> {

    @Override
    public PartnerSongInfoBo getSongInfoByPartnerSongInfo(List<FiltratedResultBo> partnerSongInfo) {
        FiltratedResultBo filtratedResultOfFilter1Bo = partnerSongInfo
                .stream()
                .filter(item -> item.getFiltratedResult().getData() instanceof KuGouMusicPartnerSongInfoFilter_1Bo)
                .findAny().get();


        KuGouMusicPartnerSongInfoFilter1Bo filter1Bo = executeWithKuGouMusicPartnerSongInfoFilter1(partnerSongInfo, filtratedResultOfFilter1Bo);
        KuGouMusicPartnerSongInfoFilter2Bo filter2Bo = executeWithKuGouMusicPartnerSongInfoFilter2(partnerSongInfo);
        KuGouMusicPartnerSongInfoFilter3Bo filter3Bo = executeWithKuGouMusicPartnerSongInfoFilter3(partnerSongInfo, filtratedResultOfFilter1Bo);

        return new PartnerSongInfoBo.Builder(filter1Bo.getSongTitle(), filter3Bo.getSongArtist(), filter3Bo.getAlbumName(), filter3Bo.getAlbumPicture())
                .albumArtist(filter3Bo.getAlbumArtist())
                .albumCopyright(filter3Bo.getAlbumCopyright())
                .albumDescription(filter3Bo.getAlbumDescription())
                .albumLanguage(filter3Bo.getAlbumLanguage())
                .albumPublishDate(filter3Bo.getAlbumPublishDate())
                .songLyrics(filter2Bo.getSongLyrics())
                .build();
    }

    /**
     * 处理酷狗音乐第三方服务提供商过滤器1返回的结果
     *
     * @param partnerSongInfo
     * @param filtratedResultOfFilter1Bo
     */
    private KuGouMusicPartnerSongInfoFilter1Bo executeWithKuGouMusicPartnerSongInfoFilter1(List<FiltratedResultBo> partnerSongInfo,
                                                                                           FiltratedResultBo filtratedResultOfFilter1Bo) {
        KuGouMusicPartnerSongInfoFilter_1InfoBo filter1InfoBo = null;

        List<KuGouMusicPartnerSongInfoFilter_1InfoBo> filter1InfoBoList = Optional.ofNullable(
                ((KuGouMusicPartnerSongInfoFilter_1Bo) filtratedResultOfFilter1Bo
                        .getFiltratedResult()
                        .getData())
                        .getData()
                        .getInfo())
                .orElse(Lists.newArrayList());

        if (CollectionUtils.isNotEmpty(filter1InfoBoList)) {
            filter1InfoBo = filter1InfoBoList.get(0);
        }

        //  歌曲标题
        String songTitle = Optional.ofNullable(filter1InfoBo).orElse(new KuGouMusicPartnerSongInfoFilter_1InfoBo()).getSongname();

        return new KuGouMusicPartnerSongInfoFilter1Bo(songTitle);
    }

    /**
     * 处理酷狗音乐第三方服务提供商过滤器2返回的结果
     *
     * @param partnerSongInfo
     */
    private KuGouMusicPartnerSongInfoFilter2Bo executeWithKuGouMusicPartnerSongInfoFilter2(List<FiltratedResultBo> partnerSongInfo) {
        KuGouMusicPartnerSongInfoFilter_2DataBo filter2DataBo;

        Optional<FiltratedResultBo> filtratedResultOfFilter2Bo = partnerSongInfo
                .stream()
                .filter(item -> item.getFiltratedResult().getData() instanceof KuGouMusicPartnerSongInfoFilter_2Bo)
                .findAny();

        filter2DataBo = filtratedResultOfFilter2Bo.isPresent() ?
                Optional.ofNullable(((KuGouMusicPartnerSongInfoFilter_2Bo) filtratedResultOfFilter2Bo
                        .get()
                        .getFiltratedResult()
                        .getData())
                        .getData()
                ).orElse(new KuGouMusicPartnerSongInfoFilter_2DataBo())
                : new KuGouMusicPartnerSongInfoFilter_2DataBo();

        // 歌曲内嵌歌词
        String songLyrics = Optional.of(filter2DataBo).orElse(new KuGouMusicPartnerSongInfoFilter_2DataBo()).getLyrics();

        return new KuGouMusicPartnerSongInfoFilter2Bo(songLyrics);
    }

    /**
     * 处理酷狗音乐第三方服务提供商过滤器3返回的结果
     *
     * @param partnerSongInfo
     * @param filtratedResultOfFilter1Bo
     */
    private KuGouMusicPartnerSongInfoFilter3Bo executeWithKuGouMusicPartnerSongInfoFilter3(List<FiltratedResultBo> partnerSongInfo,
                                                                                           FiltratedResultBo filtratedResultOfFilter1Bo
    ) {
        String songArtist = null, albumName, albumArtist, albumDescription, albumLanguage, albumCopyright;
        byte[] albumPicture = null;
        LocalDate albumPublishDate = null;
        KuGouMusicPartnerSongInfoFilter_3DataBo filter3DataBo = null;

        Optional<FiltratedResultBo> filtratedResultOfFilter3Bo = partnerSongInfo
                .stream()
                .filter(item -> item.getFiltratedResult().getData() instanceof KuGouMusicPartnerSongInfoFilter_3Bo)
                .findAny();

        List<KuGouMusicPartnerSongInfoFilter_3DataBo> filter3DataBoList = filtratedResultOfFilter3Bo.isPresent()
                ? Optional.ofNullable((((KuGouMusicPartnerSongInfoFilter_3Bo) filtratedResultOfFilter3Bo.get()
                .getFiltratedResult()
                .getData())
                .getData()))
                .orElse(Lists.newArrayList())
                : Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(filter3DataBoList)) {
            filter3DataBo = filter3DataBoList.get(0);
        }

        // 歌曲艺术家
        List<KuGouMusicPartnerSongInfoFilter_3AuthorsBo> filter3AuthorsBoList = Optional.ofNullable(filter3DataBo)
                .orElse(new KuGouMusicPartnerSongInfoFilter_3DataBo())
                .getAuthors();
        if (CollectionUtils.isNotEmpty(filter3AuthorsBoList)) {
            // 优先取Filter3接口返回的歌曲艺术家数据
            songArtist = filter3AuthorsBoList
                    .stream()
                    .map(KuGouMusicPartnerSongInfoFilter_3AuthorsBo::getAuthor_name)
                    .collect(Collectors.joining("&"));
        } else {
            // 如果Filter3接口没有返回歌曲艺术家数据，则取Filter1接口返回的歌曲艺术家数据
            List<KuGouMusicPartnerSongInfoFilter_1InfoBo> filter1InfoBoList = Optional.ofNullable(
                    ((KuGouMusicPartnerSongInfoFilter_1Bo) filtratedResultOfFilter1Bo
                            .getFiltratedResult()
                            .getData())
                            .getData()
                            .getInfo())
                    .orElse(Lists.newArrayList());

            KuGouMusicPartnerSongInfoFilter_1InfoBo filter1InfoBo = null;
            if (CollectionUtils.isNotEmpty(filter1InfoBoList)) {
                filter1InfoBo = filter1InfoBoList.get(0);
            }

            String singerName = Optional.ofNullable(filter1InfoBo).orElse(new KuGouMusicPartnerSongInfoFilter_1InfoBo()).getSingername();
            if (StringUtils.isNotBlank(singerName)) {
                songArtist = singerName.replaceAll("、", "&");
            }
        }

        // 歌曲所属的专辑名称
        albumName = Optional.ofNullable(filter3DataBo).orElse(new KuGouMusicPartnerSongInfoFilter_3DataBo()).getAlbum_name();

        // 歌曲所属的专辑封面
        if (null != filter3DataBo && StringUtils.isNotBlank(filter3DataBo.getSizable_cover())) {
            albumPicture = HttpUtil.getImage(filter3DataBo.getSizable_cover(), null);
        }

        // 歌曲所属专辑的艺术家
        albumArtist = songArtist;

        // 歌曲所属专辑的发布时间
        String publishDate = Optional.ofNullable(filter3DataBo).orElse(new KuGouMusicPartnerSongInfoFilter_3DataBo())
                .getPublish_date();
        if (StringUtils.isNotBlank(publishDate) && !"0000-00-00".equals(publishDate)) {
            albumPublishDate = DateTimeUtil.stringToLocalDate(DateTimeUtil.DatePattern.YYYYMMDD_FORMAT.getValue(), publishDate);
        }

        // 歌曲所属专辑的描述
        albumDescription = Optional.ofNullable(filter3DataBo).orElse(new KuGouMusicPartnerSongInfoFilter_3DataBo()).getIntro();

        // 歌曲所属专辑的语言类型
        albumLanguage = Optional.ofNullable(filter3DataBo).orElse(new KuGouMusicPartnerSongInfoFilter_3DataBo()).getLanguage();

        // 歌曲所属专辑的版权信息
        albumCopyright = Optional.ofNullable(filter3DataBo).orElse(new KuGouMusicPartnerSongInfoFilter_3DataBo()).getPublish_company();

        return new KuGouMusicPartnerSongInfoFilter3Bo(songArtist, albumName, albumArtist, albumDescription, albumLanguage,
                albumCopyright, albumPicture, albumPublishDate);
    }

    static class KuGouMusicPartnerSongInfoFilter1Bo {
        String songTitle;

        public String getSongTitle() {
            return songTitle;
        }

        public void setSongTitle(String songTitle) {
            this.songTitle = songTitle;
        }

        public KuGouMusicPartnerSongInfoFilter1Bo(String songTitle) {
            this.songTitle = songTitle;
        }
    }

    static class KuGouMusicPartnerSongInfoFilter2Bo {
        String songLyrics;

        public String getSongLyrics() {
            return songLyrics;
        }

        public void setSongLyrics(String songLyrics) {
            this.songLyrics = songLyrics;
        }

        public KuGouMusicPartnerSongInfoFilter2Bo(String songLyrics) {
            this.songLyrics = songLyrics;
        }
    }

    static class KuGouMusicPartnerSongInfoFilter3Bo {
        private String songArtist;

        private String albumName;

        private String albumArtist;

        private String albumDescription;

        private String albumLanguage;

        private String albumCopyright;

        private byte[] albumPicture;

        private LocalDate albumPublishDate;

        public String getSongArtist() {
            return songArtist;
        }

        public void setSongArtist(String songArtist) {
            this.songArtist = songArtist;
        }

        public String getAlbumName() {
            return albumName;
        }

        public void setAlbumName(String albumName) {
            this.albumName = albumName;
        }

        public String getAlbumArtist() {
            return albumArtist;
        }

        public void setAlbumArtist(String albumArtist) {
            this.albumArtist = albumArtist;
        }

        public String getAlbumDescription() {
            return albumDescription;
        }

        public void setAlbumDescription(String albumDescription) {
            this.albumDescription = albumDescription;
        }

        public String getAlbumLanguage() {
            return albumLanguage;
        }

        public void setAlbumLanguage(String albumLanguage) {
            this.albumLanguage = albumLanguage;
        }

        public String getAlbumCopyright() {
            return albumCopyright;
        }

        public void setAlbumCopyright(String albumCopyright) {
            this.albumCopyright = albumCopyright;
        }

        public byte[] getAlbumPicture() {
            return albumPicture;
        }

        public void setAlbumPicture(byte[] albumPicture) {
            this.albumPicture = albumPicture;
        }

        public LocalDate getAlbumPublishDate() {
            return albumPublishDate;
        }

        public void setAlbumPublishDate(LocalDate albumPublishDate) {
            this.albumPublishDate = albumPublishDate;
        }

        public KuGouMusicPartnerSongInfoFilter3Bo(String songArtist, String albumName, String albumArtist, String albumDescription,
                                                  String albumLanguage, String albumCopyright, byte[] albumPicture,
                                                  LocalDate albumPublishDate) {
            this.songArtist = songArtist;
            this.albumName = albumName;
            this.albumArtist = albumArtist;
            this.albumDescription = albumDescription;
            this.albumLanguage = albumLanguage;
            this.albumCopyright = albumCopyright;
            this.albumPicture = albumPicture;
            this.albumPublishDate = albumPublishDate;
        }
    }
}
