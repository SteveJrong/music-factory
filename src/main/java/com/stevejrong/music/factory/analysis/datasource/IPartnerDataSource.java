package com.stevejrong.music.factory.analysis.datasource;

/**
 * 第三方音乐数据源接口
 */
public interface IPartnerDataSource {

    /**
     * 设置根据关键字搜索歌曲信息的请求地址
     *
     * @return
     */
    void setSearchSongUrl();

    /**
     * 设置根据第三方凭据搜索歌曲的专辑图片的请求地址
     *
     * @return
     */
    void setSearchAlbumPicUrl();

    /**
     * 根据关键字搜索歌曲信息
     *
     * @param keywords
     * @return
     */
    String searchSongByKeywords(String... keywords);

    /**
     * 根据第三方凭据搜索歌曲的专辑图片
     *
     * @param partnerCredential
     * @return
     */
    byte[] searchAlbumPicByPartnerCredential(String partnerCredential);
}
