package com.stevejrong.music.factory.provider.service.music.analyzing.partner.resolver.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.stevejrong.music.factory.common.util.DateTimeUtil;
import com.stevejrong.music.factory.common.util.HttpUtil;
import com.stevejrong.music.factory.spi.music.bo.analyzing.datasource.PartnerSongInfoBo;
import com.stevejrong.music.factory.spi.music.bo.partner.filter1.KuGouMusicPartnerSongInfoFilter_1Bo;
import com.stevejrong.music.factory.spi.music.bo.partner.filter1.KuGouMusicPartnerSongInfoFilter_1DataBo;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        String songTitle = null != filter1InfoBo ? filter1InfoBo.getSongname() : null;

        return new KuGouMusicPartnerSongInfoFilter1Bo(songTitle);
    }

    /**
     * 处理酷狗音乐第三方服务提供商过滤器2返回的结果
     *
     * @param partnerSongInfo
     */
    private KuGouMusicPartnerSongInfoFilter2Bo executeWithKuGouMusicPartnerSongInfoFilter2(List<FiltratedResultBo> partnerSongInfo) {
        KuGouMusicPartnerSongInfoFilter_2DataBo filter2DataBo;

        FiltratedResultBo filtratedResultOfFilter2Bo = partnerSongInfo
                .stream()
                .filter(item -> item.getFiltratedResult().getData() instanceof KuGouMusicPartnerSongInfoFilter_2Bo)
                .findAny().get();

        filter2DataBo = Optional.ofNullable(
                ((KuGouMusicPartnerSongInfoFilter_2Bo) filtratedResultOfFilter2Bo
                        .getFiltratedResult()
                        .getData())
                        .getData()
        ).orElse(new KuGouMusicPartnerSongInfoFilter_2DataBo());

        // 歌曲内嵌歌词
        String songLyrics = filter2DataBo.getLyrics();

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

        FiltratedResultBo filtratedResultOfFilter3Bo = partnerSongInfo
                .stream()
                .filter(item -> item.getFiltratedResult().getData() instanceof KuGouMusicPartnerSongInfoFilter_3Bo)
                .findAny().get();

        List<KuGouMusicPartnerSongInfoFilter_3DataBo> filter3DataBoList = Optional.ofNullable(
                (((KuGouMusicPartnerSongInfoFilter_3Bo) filtratedResultOfFilter3Bo
                        .getFiltratedResult()
                        .getData())
                        .getData()))
                .orElse(Lists.newArrayList());
        if (CollectionUtils.isNotEmpty(filter3DataBoList)) {
            filter3DataBo = filter3DataBoList.get(0);
        }

        // 歌曲艺术家
        if (null != filter3DataBo && CollectionUtils.isNotEmpty(filter3DataBo.getAuthors())) {
            // 优先取Filter3接口返回的歌曲艺术家数据
            songArtist = filter3DataBo.getAuthors().stream().map(KuGouMusicPartnerSongInfoFilter_3AuthorsBo::getAuthor_name).collect(Collectors.joining("&"));
        } else {
            // 如果Filter3接口没有返回歌曲艺术家数据，则取Filter1接口返回的歌曲艺术家数据
            String singerName = ((KuGouMusicPartnerSongInfoFilter_1Bo) filtratedResultOfFilter1Bo
                    .getFiltratedResult().getData()).getData().getInfo().get(0).getSingername();
            if (StringUtils.isNotBlank(singerName)) {
                songArtist = singerName.replaceAll("、", "&");
            }
        }

        // 歌曲所属的专辑名称
        albumName = null != filter3DataBo ? filter3DataBo.getAlbum_name() : null;

        // 歌曲所属的专辑封面
        if (null != filter3DataBo && StringUtils.isNotBlank(filter3DataBo.getSizable_cover())) {
            albumPicture = HttpUtil.getImage(filter3DataBo.getSizable_cover(), null);
        }

        // 歌曲所属专辑的艺术家
        albumArtist = songArtist;

        // 歌曲所属专辑的发布时间
        if (null != filter3DataBo && StringUtils.isNotBlank(filter3DataBo.getPublish_date())
                && !"0000-00-00".equals(filter3DataBo.getPublish_date())) {
            albumPublishDate = DateTimeUtil.stringToLocalDate(DateTimeUtil.DatePattern.YYYYMMDD_FORMAT.getValue(), filter3DataBo.getPublish_date());
        }

        // 歌曲所属专辑的描述
        albumDescription = null != filter3DataBo ? filter3DataBo.getIntro() : null;

        // 歌曲所属专辑的语言类型
        albumLanguage = null != filter3DataBo ? filter3DataBo.getLanguage() : null;

        // 歌曲所属专辑的版权信息
        albumCopyright = null != filter3DataBo ? filter3DataBo.getPublish_company() : null;

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
