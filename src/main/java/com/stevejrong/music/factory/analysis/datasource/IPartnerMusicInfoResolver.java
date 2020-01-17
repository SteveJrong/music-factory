package com.stevejrong.music.factory.analysis.datasource;

/**
 * 第三方音乐信息解析器接口
 */
public interface IPartnerMusicInfoResolver<T> {

    /**
     * 根据根据关键字搜索歌曲信息返回的结果转换为有用的Bo对象
     *
     * @param searchSongResult
     * @return
     */
    T getMusicInfoBySearchSongResult(String searchSongResult);
}
