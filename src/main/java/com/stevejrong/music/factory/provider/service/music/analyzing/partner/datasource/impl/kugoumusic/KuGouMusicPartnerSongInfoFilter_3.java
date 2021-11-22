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

import com.google.common.collect.Lists;
import com.stevejrong.music.factory.common.enums.BaseEnums;
import com.stevejrong.music.factory.common.util.BeanMapperUtil;
import com.stevejrong.music.factory.common.util.GsonUtil;
import com.stevejrong.music.factory.common.util.HttpUtil;
import com.stevejrong.music.factory.common.util.RandomUtil;
import com.stevejrong.music.factory.spi.music.bean.partner.kugoumusic.KuGouMusicPartnerSongInfoFilterCriteriaBean;
import com.stevejrong.music.factory.spi.music.bean.partner.kugoumusic.filter3.KuGouMusicPartnerSongInfoFilter3Bean;
import com.stevejrong.music.factory.spi.music.bean.partner.kugoumusic.filter3.KuGouMusicPartnerSongInfoFilter3DataBean;
import com.stevejrong.music.factory.spi.music.bo.partner.filter2.KuGouMusicPartnerSongInfoFilter_2Bo;
import com.stevejrong.music.factory.spi.music.bo.partner.filter3.KuGouMusicPartnerSongInfoFilter_3Bo;
import com.stevejrong.music.factory.spi.music.bo.validator.filtrated.FiltratedResultDataBo;
import com.stevejrong.music.factory.spi.music.vo.partner.filter3.KuGouMusicPartnerSongInfoFilter_3Vo;
import com.stevejrong.music.factory.spi.service.music.analyzing.partner.AbstractPartnerSongInfoFilter;

/**
 * @author Steve Jrong
 * create date: 2021-11-17 2:41 AM
 * @since 1.0
 */
public class KuGouMusicPartnerSongInfoFilter_3
        extends AbstractPartnerSongInfoFilter<KuGouMusicPartnerSongInfoFilterCriteriaBean, FiltratedResultDataBo<KuGouMusicPartnerSongInfoFilter_3Bo>> {

    @Override
    public FiltratedResultDataBo<KuGouMusicPartnerSongInfoFilter_3Bo> filtrate(KuGouMusicPartnerSongInfoFilterCriteriaBean criteriaBean) {
        KuGouMusicPartnerSongInfoFilter3Bean filter3Bean = new KuGouMusicPartnerSongInfoFilter3Bean(
                1,
                RandomUtil.getRandomStringWithNumeric(10),
                1,
                Lists.newArrayList(new KuGouMusicPartnerSongInfoFilter3DataBean(
                        ((KuGouMusicPartnerSongInfoFilter_2Bo) criteriaBean.getRedirectDataOnPreview()).getData().getAlbum_id()
                ))
        );

        String requestResult = HttpUtil.post(super.getRequestUrl(), BaseEnums.HttpRequestBodyDataType.APPLICATION_JSON, GsonUtil.beanToJsonString(filter3Bean));

        KuGouMusicPartnerSongInfoFilter_3Vo filter_3Vo = GsonUtil.jsonStringToBean(requestResult, KuGouMusicPartnerSongInfoFilter_3Vo.class);
        KuGouMusicPartnerSongInfoFilter_3Bo filter_3Bo = BeanMapperUtil.copy(filter_3Vo, KuGouMusicPartnerSongInfoFilter_3Bo.class);

        return new FiltratedResultDataBo
                .Builder<KuGouMusicPartnerSongInfoFilter_3Bo>(true)
                .data(filter_3Bo)
                .build();
    }
}