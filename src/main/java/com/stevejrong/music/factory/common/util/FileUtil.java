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
import com.stevejrong.music.factory.config.SystemConfig;
import com.stevejrong.music.factory.spi.service.music.metadata.resolver.query.IAudioFileMetadataQueryResolver;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.channels.FileChannel;

/**
 * 文件操作工具类
 *
 * @author Steve Jrong
 * @since 1.0
 */
public final class FileUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

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
            LOGGER.error(LoggerUtil.builder().append("fileUtil_getResourceFile", "获取资源文件")
                    .append("exception", e).append("exceptionMsg", e.getMessage()).toString());
        }

        return resourceFile;
    }

    /**
     * 获取资源文件
     *
     * @param resourcesFilePath 资源文件位置
     * @param fileSuffix        自定义的临时文件后缀
     * @return 资源文件
     */
    public static File getResourceFile(String resourcesFilePath, String fileSuffix) {
        // TODO 下次版本更新要解决临时文件重复创建浪费磁盘空间的问题
        InputStream inputStream = FileUtil.class.getClassLoader()
                .getResourceAsStream(resourcesFilePath);

        File resourceFile = null;
        try {
            resourceFile = File.createTempFile(resourcesFilePath, StringUtils.isNotEmpty(fileSuffix) ? fileSuffix : "temp");
            FileUtils.copyInputStreamToFile(inputStream, resourceFile);
        } catch (IOException e) {
            LOGGER.error(LoggerUtil.builder().append("fileUtil_getResourceFile", "获取资源文件")
                    .append("exception", e).append("exceptionMsg", e.getMessage()).toString());
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
     * 获取文件后缀名（不含点符号）
     *
     * @param filePath 文件位置
     * @return 文件后缀名
     */
    public static String getFileSuffixWithoutPoint(String filePath) {
        return filePath.substring(filePath.lastIndexOf(".") + 1);
    }

    /**
     * 获取文件后缀名（含点符号）
     *
     * @param filePath 文件位置
     * @return 文件后缀名
     */
    public static String getFileSuffixWithPoint(String filePath) {
        return filePath.substring(filePath.lastIndexOf("."));
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
     * 获取文件名称（含文件后缀名）
     *
     * @param filePath
     * @return
     */
    public static String getFileNameWithSuffix(String filePath) {
        return filePath.substring(filePath.lastIndexOf(File.separatorChar) + 1);
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

    /**
     * 根据音频文件的文件后缀名获取对应的音频文件元数据解析器
     *
     * @param systemConfig 系统配置
     * @param fileSuffix   音频文件的后缀名
     * @return 音频文件元数据解析器
     */
    public static IAudioFileMetadataQueryResolver getAudioFileMetadataQueryResolverByFileSuffix(SystemConfig systemConfig, String fileSuffix) {
        return (IAudioFileMetadataQueryResolver) systemConfig
                .getAnalysingAndComplementsForAudioFileConfig().getAudioFileMetadataResolvers().get(fileSuffix)
                .stream().filter(resolver -> resolver instanceof IAudioFileMetadataQueryResolver).findAny().get();
    }

    /**
     * 文件拷贝
     * <p>
     * NIO方式快速拷贝。
     *
     * @param sourcePath 源文件位置
     * @param targetPath 目标文件位置
     * @return 文件拷贝结果。true - 文件拷贝成功；false - 文件拷贝失败。
     */
    public static boolean fileCopy(String sourcePath, String targetPath) {
        File sourceFile = new File(sourcePath), targetFile = new File(targetPath);

        try (FileInputStream inputStream = new FileInputStream(sourceFile);
             FileOutputStream outputStream = new FileOutputStream(targetFile);

             // 获得源文件的文件通道
             FileChannel sourceChannel = inputStream.getChannel();
             // 获得目标文件的文件通道
             FileChannel targetChannel = outputStream.getChannel();
        ) {

            //两通道连接，并从源文件通道读取数据后，写入到目标文件通道
            sourceChannel.transferTo(0, sourceChannel.size(), targetChannel);
        } catch (IOException e) {
            LOGGER.error(LoggerUtil.builder().append("fileUtil_fileCopy", "文件拷贝")
                    .append("exception", e).append("exceptionMsg", e.getMessage())
                    .append("sourcePath", sourcePath).append("targetPath", targetPath).toString());
            return false;
        }

        return true;
    }
}
