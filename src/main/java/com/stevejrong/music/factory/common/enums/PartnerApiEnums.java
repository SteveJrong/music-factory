package com.stevejrong.music.factory.common.enums;

/**
 * 第三方音乐API
 */
public class PartnerApiEnums {

    /**
     * QQ音乐第三方API
     */
    public enum QQMusicApi implements AbstractEnum {
        SEARCH_SONG_URL {
            @Override
            public String getDesc() {
                return "https://c.y.qq.com/soso/fcgi-bin/client_search_cp";
            }
        },
        SEARCH_ALBUM_PIC_URL {
            @Override
            public String getDesc() {
                return "http://y.gtimg.cn/music/photo_new/T002R180x180M000";
            }
        }
    }
}
