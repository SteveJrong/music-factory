package com.stevejrong.music.factory.analysis.datasource.impl.qqmusic;

import com.google.common.collect.Maps;
import com.stevejrong.music.factory.analysis.datasource.AbstractPartnerDataSource;
import com.stevejrong.music.factory.analysis.datasource.IPartnerDataSource;
import com.stevejrong.music.factory.common.constants.BaseConstants;
import com.stevejrong.music.factory.common.enums.PartnerApiEnums;
import com.stevejrong.music.factory.util.HttpUtil;
import com.stevejrong.music.factory.util.StringUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

import static com.stevejrong.music.factory.util.HttpUtil.get;

public class QQMusicDataSourceImpl extends AbstractPartnerDataSource implements IPartnerDataSource {

    @Override
    public void setSearchSongUrl() {
        super.searchSongUrl = PartnerApiEnums.QQMusicApi.SEARCH_SONG_URL.getDesc();
    }

    @Override
    public void setSearchAlbumPicUrl() {
        super.searchAlbumPicUrl = PartnerApiEnums.QQMusicApi.SEARCH_ALBUM_PIC_URL.getDesc();
    }

    @Override
    public String searchSongByKeywords(String... keywords) {
        // https://c.y.qq.com/soso/fcgi-bin/client_search_cp?p=1&n=2&w=%e9%bb%98+%e9%82%a3%e8%8b%b1&format=json
        Map<String, String> headerParams = Maps.newHashMap();
        headerParams.put(BaseConstants.CONTENT_TYPE, BaseConstants.APPLICATION_JSON_UTF_8);

        Map<String, String> requestParams = Maps.newHashMap();
        requestParams.put("p", "1");
        requestParams.put("n", "2");

        StringBuffer sb = new StringBuffer();
        for (String keyword : keywords) {
            if (StringUtils.isEmpty(keyword) || StringUtils.isBlank(keyword)) {
                continue;
            }

            sb.append(keyword).append(" ");
        }
        requestParams.put("w", StringUtil.urlEncode(sb.toString().trim()));
        requestParams.put("format", "json");

        return (String) get(super.searchSongUrl, headerParams, requestParams);
    }

    @Override
    public byte[] searchAlbumPicByPartnerCredential(String partnerCredential) {
        String requestUrl = super.searchAlbumPicUrl + partnerCredential + ".jpg";
        byte[] bytes = HttpUtil.getOfBytes(requestUrl, null);
        return bytes;
    }
}
