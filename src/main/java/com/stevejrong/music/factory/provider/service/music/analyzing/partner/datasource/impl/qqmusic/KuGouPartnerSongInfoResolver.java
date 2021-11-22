package com.stevejrong.music.factory.provider.service.music.analyzing.partner.datasource.impl.qqmusic;

import com.stevejrong.music.factory.common.util.DateTimeUtil;
import com.stevejrong.music.factory.common.util.HttpUtil;
import com.stevejrong.music.factory.spi.music.bo.analyzing.datasource.PartnerSongInfoBo;
import com.stevejrong.music.factory.spi.music.bo.partner.filter1.KuGouMusicPartnerSongInfoFilter_1Bo;
import com.stevejrong.music.factory.spi.music.bo.partner.filter2.KuGouMusicPartnerSongInfoFilter_2Bo;
import com.stevejrong.music.factory.spi.music.bo.partner.filter3.KuGouMusicPartnerSongInfoFilter_3AuthorsBo;
import com.stevejrong.music.factory.spi.music.bo.partner.filter3.KuGouMusicPartnerSongInfoFilter_3Bo;
import com.stevejrong.music.factory.spi.music.bo.validator.filtrated.FiltratedResultBo;
import com.stevejrong.music.factory.spi.service.music.analyzing.partner.resolver.IPartnerSongInfoResolver;

import java.time.LocalDate;
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
        FiltratedResultBo filtratedResultOfFilter2Bo = partnerSongInfo
                .stream()
                .filter(item -> item.getFiltratedResult().getData() instanceof KuGouMusicPartnerSongInfoFilter_2Bo)
                .findAny().get();
        FiltratedResultBo filtratedResultOfFilter3Bo = partnerSongInfo
                .stream()
                .filter(item -> item.getFiltratedResult().getData() instanceof KuGouMusicPartnerSongInfoFilter_3Bo)
                .findAny().get();

        //  歌曲标题
        String songTitle = ((KuGouMusicPartnerSongInfoFilter_1Bo) filtratedResultOfFilter1Bo
                .getFiltratedResult().getData()).getData().getInfo().get(0).getSongname();

        // 歌曲内嵌歌词
        String songLyrics = ((KuGouMusicPartnerSongInfoFilter_2Bo) filtratedResultOfFilter2Bo
                .getFiltratedResult().getData()).getData().getLyrics();

        // 歌曲艺术家
        String songArtist = ((KuGouMusicPartnerSongInfoFilter_3Bo) filtratedResultOfFilter3Bo
                .getFiltratedResult().getData()).getData().get(0).getAuthors()
                .stream().map(KuGouMusicPartnerSongInfoFilter_3AuthorsBo::getAuthor_name).collect(Collectors.joining("&"));

        // 歌曲所属的专辑名称
        String albumName = ((KuGouMusicPartnerSongInfoFilter_3Bo) filtratedResultOfFilter3Bo
                .getFiltratedResult().getData()).getData().get(0).getAlbum_name();

        // 歌曲所属的专辑封面
        byte[] albumPicture = HttpUtil.getImage(((KuGouMusicPartnerSongInfoFilter_3Bo) filtratedResultOfFilter3Bo
                .getFiltratedResult().getData()).getData().get(0).getSizable_cover(), null);

        // 歌曲所属专辑的艺术家
        String albumArtist = ((KuGouMusicPartnerSongInfoFilter_3Bo) filtratedResultOfFilter3Bo
                .getFiltratedResult().getData()).getData().get(0).getAuthors()
                .stream().map(KuGouMusicPartnerSongInfoFilter_3AuthorsBo::getAuthor_name).collect(Collectors.joining("&"));

        // 歌曲所属专辑的发布时间
        LocalDate albumPublishDate = Optional.ofNullable(
                DateTimeUtil.stringToLocalDate(
                        DateTimeUtil.DatePattern.YYYYMMDD_FORMAT.getValue(),
                        ((KuGouMusicPartnerSongInfoFilter_3Bo) filtratedResultOfFilter3Bo
                                .getFiltratedResult().getData()).getData().get(0).getPublish_date())
        ).orElse(DateTimeUtil.getNowDate());

        // 歌曲所属专辑的描述
        String albumDescription = ((KuGouMusicPartnerSongInfoFilter_3Bo) filtratedResultOfFilter3Bo
                .getFiltratedResult().getData()).getData().get(0).getIntro();

        // 歌曲所属专辑的语言类型
        String albumLanguage = ((KuGouMusicPartnerSongInfoFilter_3Bo) filtratedResultOfFilter3Bo
                .getFiltratedResult().getData()).getData().get(0).getLanguage();

        // 歌曲所属专辑的版权信息

        String albumCopyright = ((KuGouMusicPartnerSongInfoFilter_3Bo) filtratedResultOfFilter3Bo
                .getFiltratedResult().getData()).getData().get(0).getPublish_company();

        return new PartnerSongInfoBo.Builder(songTitle, songArtist, albumName, albumPicture)
                .albumArtist(albumArtist)
                .albumCopyright(albumCopyright)
                .albumDescription(albumDescription)
                .albumLanguage(albumLanguage)
                .albumPublishDate(albumPublishDate)
                .songLyrics(songLyrics)
                .build();
    }
}
