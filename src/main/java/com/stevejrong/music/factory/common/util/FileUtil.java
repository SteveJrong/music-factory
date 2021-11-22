package com.stevejrong.music.factory.common.util;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Util - 文件操作工具类
 */
public final class FileUtil {

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
     * @param path 图片路径
     * @return 字节数组
     */
    public static byte[] imageFileToByteArray(String path) {
        File file = new File(path);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] imgByteArray = new byte[]{};

        try {
            BufferedImage bi = ImageIO.read(file);
            ImageIO.write(bi, "jpg", byteArrayOutputStream);
            imgByteArray = byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return imgByteArray;
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

        return keyword1 + " " + keyword2;
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
     * 获取项目的根绝对路径
     *
     * @return 项目的根绝对路径
     */
    public static String getProjectAbsolutePath() {
        String projectAbsolutePath = null;

        File file = new File("");
        try {
            projectAbsolutePath = file.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return projectAbsolutePath;
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
     * 根据文件位置获取文件名称（不含文件后缀名）
     *
     * @param filePath 文件位置
     * @return 不含文件后缀名的文件名称
     */
    public static String getFileNameWithoutSuffixByFilePath(String filePath) {
        return filePath.substring(0, filePath.lastIndexOf("."));
    }
}
