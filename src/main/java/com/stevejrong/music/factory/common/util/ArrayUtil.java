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
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;

/**
 * 数组工具类
 *
 * @author Steve Jrong
 * @since 1.0
 */
public final class ArrayUtil {

    /**
     * 字节数组转换为ByteBuffer对象
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
     * 获取图片字节数组的BufferedImage对象
     *
     * @param pictureByteArray 图片字节数组
     * @return 图片字节数组的BufferedImage对象
     */
    public static BufferedImage getBufferedImageByPictureByteArray(byte[] pictureByteArray) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new ByteArrayInputStream(pictureByteArray));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    /**
     * 字节数组转换为File对象
     * 此方法会生成临时文件
     *
     * @param byteArray 字节数组
     * @param filePath  临时文件创建路径
     * @return File对象
     */
    public static File byteArrayToFile(byte[] byteArray, String filePath) {
        File file = new File(filePath);

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            OutputStream output = new FileOutputStream(file);
            BufferedOutputStream bufferedOutput = new BufferedOutputStream(output);
            bufferedOutput.write(byteArray);
            bufferedOutput.flush();
            bufferedOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    /**
     * 字节数组存为图片
     *
     * @param byteArray 字节数组
     * @param path      图片的存储路径
     */
    public static void byteArrayToImageFile(byte[] byteArray, String path) {
        try {
            FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
            imageOutput.write(byteArray, 0, byteArray.length);
            imageOutput.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}