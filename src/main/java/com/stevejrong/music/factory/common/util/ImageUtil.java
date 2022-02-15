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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.stream.FileImageInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * 图片工具类
 *
 * @author Steve Jrong
 * @since 1.0
 */
public final class ImageUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageUtil.class);

    /**
     * 图片转字节数组
     *
     * @param pictureFilePath 图片路径
     * @return 字节数组
     */
    public static byte[] imageFileToByteArray(String pictureFilePath) {
        byte[] imageByteArray = null;
        FileImageInputStream imageInputStream;
        try {
            imageInputStream = new FileImageInputStream(new File(pictureFilePath));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead;
            while ((numBytesRead = imageInputStream.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }

            imageByteArray = output.toByteArray();
            output.close();
            imageInputStream.close();
        } catch (IOException e) {
            LOGGER.error(LoggerUtil.builder().append("imageUtil_imageFileToByteArray", "图片转字节数组")
                    .append("exception", e).append("exceptionMsg", e.getMessage()).toString());
        }

        return imageByteArray;
    }
}