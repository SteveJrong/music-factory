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
package com.stevejrong.music.factory.provider.service.music.analyzing.filter.impl;

import com.stevejrong.music.factory.spi.music.bean.MusicInfoByFilterBean;
import com.stevejrong.music.factory.spi.music.bo.validator.filtrated.FiltratedResultDataBo;
import com.stevejrong.music.factory.spi.service.music.filter.AbstractFilter;
import org.apache.commons.lang3.StringUtils;

/**
 * Validator - 音频文件信息中歌曲所属专辑的版权信息过滤器
 *
 * @author Steve Jrong
 * create date: 2021-11-14 9:48 AM
 * @since 1.0
 */
public class AlbumCopyrightFilter extends AbstractFilter<MusicInfoByFilterBean, FiltratedResultDataBo<Boolean>> {

    @Override
    public FiltratedResultDataBo<Boolean> filtrate(MusicInfoByFilterBean criteriaBean) {
        return new FiltratedResultDataBo.Builder<Boolean>(
                !StringUtils.isBlank(criteriaBean.getAlbumCopyright())).build();
    }
}