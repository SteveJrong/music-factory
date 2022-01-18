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
package com.stevejrong.music.factory.common.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @author Steve Jrong
 * create date: 2021-11-21 10:54 AM
 * @since 1.0
 */
public final class ArrayUtil {

    /**
     * Byte数组转换为ByteBuffer对象
     *
     * @param byteArray Byte数组
     * @return ByteBuffer对象
     */
    public static ByteBuffer byteArrayToByteBuffer(byte[] byteArray) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(byteArray.length);
        byteBuffer.put(byteArray);
        byteBuffer.flip();

        return byteBuffer;
    }

    /**
     * 图片Byte数组转换为BufferImage对象
     *
     * @param byteArrayForImage 图片Byte数组
     * @return BufferImage对象
     */
    public static BufferedImage byteArrayToBufferImage(byte[] byteArrayForImage) {
        BufferedImage bufferedImage = null;
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArrayForImage);
        try {
            bufferedImage = ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bufferedImage;
    }
}