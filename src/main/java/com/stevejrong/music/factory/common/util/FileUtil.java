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

import com.stevejrong.music.factory.common.constants.BaseConstants;
import com.stevejrong.music.factory.common.enums.ResourcesFileEnum;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 文件操作工具类
 *
 * @author Steve Jrong
 * @since 1.0
 */
public final class FileUtil {
    private static byte[] DEFAULT_ALBUM_PICTURE_BYTE_ARRAY = null;

    /**
     * 获取资源文件位置
     *
     * @param resourcesFileEnum 资源文件枚举
     * @return
     */
    private static String getResourceFilePath(ResourcesFileEnum resourcesFileEnum) {
        return resourcesFileEnum.getValue();
    }

    /**
     * 获取资源文件
     *
     * @param resourcesFileEnum 资源文件枚举
     * @return 资源文件
     */
    public static File getResourceFile(ResourcesFileEnum resourcesFileEnum) {
        InputStream inputStream = FileUtil.class.getClassLoader()
                .getResourceAsStream(getResourceFilePath(resourcesFileEnum));

        File resourceFile = null;
        try {
            resourceFile = File.createTempFile(resourcesFileEnum.getValue(), "temp");
            FileUtils.copyInputStreamToFile(inputStream, resourceFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resourceFile;
    }

    /**
     * 获取资源文件
     *
     * @param resourcesFilePath 资源文件位置
     * @return 资源文件
     */
    public static File getResourceFile(String resourcesFilePath) {
        InputStream inputStream = FileUtil.class.getClassLoader()
                .getResourceAsStream(resourcesFilePath);

        File resourceFile = null;
        try {
            resourceFile = File.createTempFile(resourcesFilePath, "temp");
            FileUtils.copyInputStreamToFile(inputStream, resourceFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resourceFile;
    }

    /**
     * 获取默认专辑封面图片字节数组
     *
     * @return
     */
    public static byte[] getDefaultAlbumPictureByteArray() {
        if (ArrayUtils.isEmpty(DEFAULT_ALBUM_PICTURE_BYTE_ARRAY)) {
            DEFAULT_ALBUM_PICTURE_BYTE_ARRAY = ImageUtil.imageFileToByteArray(getResourceFile(ResourcesFileEnum.DEFAULT_ALBUM_PICTURE).getPath());
        }

        return DEFAULT_ALBUM_PICTURE_BYTE_ARRAY;
    }

    /**
     * 根据音频文件名称获取搜索歌曲时的关键字
     * <p>
     * 当音频文件元数据中不能直接取到歌曲名时，按照文件名来取
     * <p>
     * 请注意：格式必须为以下两种格式之一，横杠前后可有或可无空格均可：
     * [歌曲艺术家] - [歌曲标题]
     * [歌曲标题] - [歌曲艺术家]
     *
     * @param fileName 音频文件的文件名称
     * @return 字符串类型的搜索关键字组合
     */
    public static String getSearchSongKeywordsByAudioFileName(String fileName) {
        String keyword1 = fileName.substring(0, fileName.lastIndexOf("-")).trim();
        String keyword2 = fileName.substring(fileName.lastIndexOf("-") + 1).trim();

        return StringUtil.removeSpecialChars(keyword1)
                + " " + StringUtil.removeSpecialChars(keyword2);
    }

    /**
     * 检查路径是否为正确的目录
     *
     * @param path 路径
     * @return true - 是正确的目录；false - 目录不存在
     */
    public static boolean checkIsDirectory(String path) {
        return com.google.common.io.Files.isDirectory().test(new File(path));
    }

    /**
     * 获取文件后缀名
     *
     * @param filePath 文件位置
     * @return 文件后缀名
     */
    public static String getFileSuffix(String filePath) {
        return filePath.substring(filePath.lastIndexOf(".") + 1);
    }

    /**
     * 获取文件名称（不含文件后缀名）
     *
     * @param filePath
     * @return
     */
    public static String getFileNameWithoutSuffix(String filePath) {
        return filePath.substring(filePath.lastIndexOf(File.separatorChar) + 1, filePath.lastIndexOf(BaseConstants.POINT_CHAR));
    }

    /**
     * 获取文件大小
     *
     * @param filePath 源文件位置
     * @return 长整型文件大小值
     */
    public static long getFileSize(String filePath) {
        return new File(filePath).length();
    }
}
