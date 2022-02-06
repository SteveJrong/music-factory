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
package com.stevejrong.music.factory.spi.service.music.analyzing.partner.resolver;

import com.stevejrong.music.factory.spi.music.bo.analyzing.datasource.PartnerSongInfoBo;

/**
 * 第三方音乐信息解析器接口
 *
 * @author Steve Jrong
 * @since 1.0
 */
public interface IPartnerSongInfoResolver<T> {

    /**
     * 根据从第三方音乐服务提供商获取的音频文件源数据信息对象，设置正确的PartnerSongInfoBo对象信息
     * <p>
     * 用于后续音频文件元数据信息的保存
     *
     * @param partnerSongInfoBo
     * @return
     */
    PartnerSongInfoBo getSongInfoByPartnerSongInfo(T partnerSongInfoBo);
}