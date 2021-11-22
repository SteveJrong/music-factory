package com.stevejrong.music.factory.spi.service.music.analyzing.partner.resolver;

import com.stevejrong.music.factory.spi.music.bo.analyzing.datasource.PartnerSongInfoBo;

/**
 * 第三方音乐信息解析器接口
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
