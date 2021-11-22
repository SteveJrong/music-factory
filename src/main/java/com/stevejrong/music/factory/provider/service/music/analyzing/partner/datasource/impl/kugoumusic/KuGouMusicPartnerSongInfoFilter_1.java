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
package com.stevejrong.music.factory.provider.service.music.analyzing.partner.datasource.impl.kugoumusic;

import com.stevejrong.music.factory.common.util.BeanMapperUtil;
import com.stevejrong.music.factory.common.util.GsonUtil;
import com.stevejrong.music.factory.common.util.HttpUtil;
import com.stevejrong.music.factory.spi.music.bean.partner.kugoumusic.KuGouMusicPartnerSongInfoFilterCriteriaBean;
import com.stevejrong.music.factory.spi.music.bean.partner.kugoumusic.filter1.KuGouMusicPartnerSongInfoFilter1Bean;
import com.stevejrong.music.factory.spi.music.bo.partner.filter1.KuGouMusicPartnerSongInfoFilter_1Bo;
import com.stevejrong.music.factory.spi.music.bo.validator.filtrated.FiltratedResultDataBo;
import com.stevejrong.music.factory.spi.music.vo.partner.filter1.KuGouMusicPartnerSongInfoFilter_1Vo;
import com.stevejrong.music.factory.spi.service.music.analyzing.partner.AbstractPartnerSongInfoFilter;

import java.util.HashMap;

/**
 * @author Steve Jrong
 * create date: 2021-11-17 1:46 AM
 * @since 1.0
 */
public class KuGouMusicPartnerSongInfoFilter_1
        extends AbstractPartnerSongInfoFilter<KuGouMusicPartnerSongInfoFilterCriteriaBean, FiltratedResultDataBo<KuGouMusicPartnerSongInfoFilter_1Bo>> {

    @Override
    public FiltratedResultDataBo<KuGouMusicPartnerSongInfoFilter_1Bo> filtrate(KuGouMusicPartnerSongInfoFilterCriteriaBean criteriaBean) {
        KuGouMusicPartnerSongInfoFilter1Bean filter1Bean = new KuGouMusicPartnerSongInfoFilter1Bean("json", criteriaBean.getSearchKeywords());
        String responseResult = HttpUtil.get(super.getRequestUrl(), BeanMapperUtil.map(filter1Bean, HashMap.class));

        KuGouMusicPartnerSongInfoFilter_1Vo filter1Vo = GsonUtil.jsonStringToBean(responseResult, KuGouMusicPartnerSongInfoFilter_1Vo.class);
        KuGouMusicPartnerSongInfoFilter_1Bo filter1Bo = BeanMapperUtil.copy(filter1Vo, KuGouMusicPartnerSongInfoFilter_1Bo.class);

        return new FiltratedResultDataBo
                .Builder<KuGouMusicPartnerSongInfoFilter_1Bo>(true)
                .data(filter1Bo)
                .build();
    }
}