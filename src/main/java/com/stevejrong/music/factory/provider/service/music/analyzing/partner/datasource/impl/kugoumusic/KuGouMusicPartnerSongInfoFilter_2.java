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
package com.stevejrong.music.factory.provider.service.music.analyzing.partner.datasource.impl.kugoumusic;

import com.stevejrong.music.factory.common.util.BeanMapperUtil;
import com.stevejrong.music.factory.common.util.GsonUtil;
import com.stevejrong.music.factory.common.util.HttpUtil;
import com.stevejrong.music.factory.common.util.LoggerUtil;
import com.stevejrong.music.factory.spi.music.bean.partner.kugoumusic.KuGouMusicPartnerSongInfoFilterCriteriaBean;
import com.stevejrong.music.factory.spi.music.bean.partner.kugoumusic.filter2.KuGouMusicPartnerSongInfoFilter2Bean;
import com.stevejrong.music.factory.spi.music.bo.partner.filter1.KuGouMusicPartnerSongInfoFilter_1Bo;
import com.stevejrong.music.factory.spi.music.bo.partner.filter1.KuGouMusicPartnerSongInfoFilter_1InfoBo;
import com.stevejrong.music.factory.spi.music.bo.partner.filter2.KuGouMusicPartnerSongInfoFilter_2Bo;
import com.stevejrong.music.factory.spi.music.bo.validator.filtrated.FiltratedResultDataBo;
import com.stevejrong.music.factory.spi.music.vo.partner.filter2.KuGouMusicPartnerSongInfoFilter_2Vo;
import com.stevejrong.music.factory.spi.service.music.analyzing.partner.AbstractPartnerSongInfoFilter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

/**
 * ???????????????????????????????????????????????????2
 *
 * @author Steve Jrong
 * @since 1.0
 */
public class KuGouMusicPartnerSongInfoFilter_2
        extends AbstractPartnerSongInfoFilter<KuGouMusicPartnerSongInfoFilterCriteriaBean, FiltratedResultDataBo<KuGouMusicPartnerSongInfoFilter_2Bo>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(KuGouMusicPartnerSongInfoFilter_2.class);

    @Override
    public FiltratedResultDataBo<KuGouMusicPartnerSongInfoFilter_2Bo> filtrate(KuGouMusicPartnerSongInfoFilterCriteriaBean criteriaBean) {
        List<KuGouMusicPartnerSongInfoFilter_1InfoBo> filter1BoList = ((KuGouMusicPartnerSongInfoFilter_1Bo) criteriaBean.getRedirectDataOnPreview()).getData().getInfo();

        if (CollectionUtils.isNotEmpty(filter1BoList)) {
            String albumId = filter1BoList.get(0).getAlbum_id();
            if (StringUtils.isBlank(albumId)) {
                albumId = filter1BoList.get(0).getAlbum_audio_id();
            }

            KuGouMusicPartnerSongInfoFilter2Bean filter2Bean = new KuGouMusicPartnerSongInfoFilter2Bean(
                    "play/getdata",
                    ((KuGouMusicPartnerSongInfoFilter_1Bo) criteriaBean.getRedirectDataOnPreview()).getData().getInfo().get(0).getHash(),
                    1, albumId);
            LOGGER.info(LoggerUtil.builder().append("kuGouMusicPartnerSongInfoFilter_2_filtrate", "???????????????????????????????????????????????????2")
                    .append("criteriaBean", criteriaBean).append("filter2Bean", filter2Bean).toString());

            String response = HttpUtil.get(super.getRequestUrl(), BeanMapperUtil.map(filter2Bean, HashMap.class));
            LOGGER.info(LoggerUtil.builder().append("kuGouMusicPartnerSongInfoFilter_2_filtrate", "???????????????????????????????????????????????????2??????")
                    .append("response", response).append("criteriaBean", criteriaBean).append("filter2Bean", filter2Bean)
                    .toString());

            KuGouMusicPartnerSongInfoFilter_2Vo filter_2Vo = GsonUtil.jsonStringToBean(response, KuGouMusicPartnerSongInfoFilter_2Vo.class);
            KuGouMusicPartnerSongInfoFilter_2Bo filter_2Bo = BeanMapperUtil.copy(filter_2Vo, KuGouMusicPartnerSongInfoFilter_2Bo.class);

            return new FiltratedResultDataBo
                    .Builder<KuGouMusicPartnerSongInfoFilter_2Bo>(true)
                    .data(filter_2Bo)
                    .build();
        } else {
            return new FiltratedResultDataBo
                    .Builder<KuGouMusicPartnerSongInfoFilter_2Bo>(false)
                    .build();
        }
    }
}