package com.stevejrong.music.factory.common.exception;

/**
 * 无歌曲搜索结果异常类
 */
public class NoSearchResultOfSongException extends RuntimeException {
    private static final long serialVersionUID = -709626715988187573L;

    public NoSearchResultOfSongException() {
        super("无歌曲搜索结果");
    }
}
