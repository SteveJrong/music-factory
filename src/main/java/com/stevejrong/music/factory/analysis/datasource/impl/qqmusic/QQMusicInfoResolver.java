package com.stevejrong.music.factory.analysis.datasource.impl.qqmusic;

import com.alibaba.fastjson.JSONObject;
import com.stevejrong.music.factory.analysis.datasource.IPartnerMusicInfoResolver;
import com.stevejrong.music.factory.analysis.datasource.bo.BaseMusicInfoBo;
import com.stevejrong.music.factory.analysis.datasource.impl.qqmusic.vo.QQMusicInfoVo;
import com.stevejrong.music.factory.common.exception.NoSearchResultOfSongException;
import org.springframework.util.CollectionUtils;

public class QQMusicInfoResolver implements IPartnerMusicInfoResolver<BaseMusicInfoBo> {

    @Override
    public BaseMusicInfoBo getMusicInfoBySearchSongResult(String searchSongResult) throws NoSearchResultOfSongException {
        QQMusicInfoVo qqMusicInfoVo = JSONObject.parseObject(searchSongResult, QQMusicInfoVo.class);

        if (null != qqMusicInfoVo && null != qqMusicInfoVo.getData() && null != qqMusicInfoVo.getData().getSong()
                && !CollectionUtils.isEmpty(qqMusicInfoVo.getData().getSong().getList())) {
            BaseMusicInfoBo baseMusicInfoBo = new BaseMusicInfoBo();
            baseMusicInfoBo.setSongName(qqMusicInfoVo.getData().getSong().getList().get(0).getSongname());
            baseMusicInfoBo.setSingerName(qqMusicInfoVo.getData().getSong().getList().get(0).getSinger().get(0).getName());
            baseMusicInfoBo.setAlbumName(qqMusicInfoVo.getData().getSong().getList().get(0).getAlbumname());
            baseMusicInfoBo.setPartnerCredentialBySearchAlbum(qqMusicInfoVo.getData().getSong().getList().get(0).getAlbummid());

            return baseMusicInfoBo;
        }

        throw new NoSearchResultOfSongException();
    }
}
