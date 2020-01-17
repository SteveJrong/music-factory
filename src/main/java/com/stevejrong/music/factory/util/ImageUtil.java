package com.stevejrong.music.factory.util;

import java.nio.ByteBuffer;

/**
 * Util - 图片操作工具类
 */
public final class ImageUtil {

    /**
     * 字节数组转ByteBuffer对象
     *
     * @param value
     * @return
     */
    public static ByteBuffer bytesToByteBuffer(byte[] value) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(value.length);
        byteBuffer.clear();
        byteBuffer.get(value, 0, value.length);
        return byteBuffer;
    }
}
