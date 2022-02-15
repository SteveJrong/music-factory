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
package com.stevejrong.music.factory.provider.service.music.analyzing.filter.impl;

import com.stevejrong.music.factory.common.util.LoggerUtil;
import com.stevejrong.music.factory.spi.music.bean.MusicInfoByFilterBean;
import com.stevejrong.music.factory.spi.music.bo.validator.filtrated.FiltratedResultDataBo;
import com.stevejrong.music.factory.spi.service.music.filter.AbstractFilter;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * 音频文件信息中歌曲所属的专辑封面过滤器
 *
 * @author Steve Jrong
 * @since 1.0
 */
public class AlbumPictureFilter extends AbstractFilter<MusicInfoByFilterBean, FiltratedResultDataBo<Boolean>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AlbumPictureFilter.class);

    @Override
    public FiltratedResultDataBo<Boolean> filtrate(MusicInfoByFilterBean criteriaBean) {
        if (ArrayUtils.isEmpty(criteriaBean.getAlbumPicture())) {
            return new FiltratedResultDataBo.Builder<Boolean>(false).build();
        }

        BufferedImage image = null;
        try {
            image = ImageIO.read(new ByteArrayInputStream(criteriaBean.getAlbumPicture()));
        } catch (IOException e) {
            LOGGER.error(LoggerUtil.builder().append("albumPictureFilter_filtrate", "主过滤方法")
                    .append("exception", e).append("exceptionMsg", e.getMessage()).toString());
        }

        return new FiltratedResultDataBo.Builder<Boolean>(
                !(image.getWidth() < 500 || image.getHeight() < 500)).build();
    }
}