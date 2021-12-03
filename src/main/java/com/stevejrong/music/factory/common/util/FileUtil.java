package com.stevejrong.music.factory.common.util;

import com.stevejrong.music.factory.common.enums.ResourcesFileEnum;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Util - 文件操作工具类
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
            DEFAULT_ALBUM_PICTURE_BYTE_ARRAY = imageFileToByteArray(getResourceFile(ResourcesFileEnum.DEFAULT_ALBUM_PICTURE).getPath());
        }

        return DEFAULT_ALBUM_PICTURE_BYTE_ARRAY;
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
        } catch (IOException ex1) {
            ex1.printStackTrace();
        }

        return imageByteArray;
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
     * 字符串内容写入文本文件
     *
     * @param content    要写入的字符串内容
     * @param fileSuffix 保存的文件后缀名
     * @param fileName   保存的文件名称
     * @param filePath   保存的文件路径
     */
    public static void writeStringContentToFile(String content, String fileSuffix, String fileName, String filePath) {
        File file = new File(filePath.concat(File.separator).concat(fileName).concat(fileSuffix));

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
            Files.write(Paths.get(filePath.concat(File.separator).concat(fileName).concat(fileSuffix)), content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
     * 获取文件后缀名
     *
     * @param filePath 文件位置
     * @return 文件后缀名
     */
    public static String getFileSuffix(String filePath) {
        return filePath.substring(filePath.lastIndexOf(".") + 1);
    }
}
