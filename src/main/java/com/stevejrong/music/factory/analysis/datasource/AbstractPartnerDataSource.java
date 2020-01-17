package com.stevejrong.music.factory.analysis.datasource;

/**
 * 第三方音乐数据源抽象类
 */
public abstract class AbstractPartnerDataSource implements IPartnerDataSource {
    protected String searchSongUrl = null;
    protected String searchAlbumPicUrl = null;

    public AbstractPartnerDataSource() {
        setSearchSongUrl();
        setSearchAlbumPicUrl();
    }

    protected String getSearchSongUrl() {
        return this.searchSongUrl;
    }

    protected String getSearchAlbumPicUrl() {
        return this.searchAlbumPicUrl;
    }
}
