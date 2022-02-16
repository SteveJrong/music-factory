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
package com.stevejrong.music.factory.test;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stevejrong.music.factory.bean.TestBean1;
import com.stevejrong.music.factory.bean.TestBean2;
import com.stevejrong.music.factory.common.util.*;
import com.stevejrong.music.factory.config.SystemConfig;
import com.stevejrong.music.factory.provider.service.music.formatConversion.impl.DSF_to_OGG_VORBIS_Converter;
import com.stevejrong.music.factory.provider.service.music.formatConversion.impl.FLAC_to_OGG_VORBIS_Converter;
import com.stevejrong.music.factory.provider.service.music.impl.AnalyzingInfoForAudioFileModule;
import com.stevejrong.music.factory.provider.service.music.impl.AudioFileFormatConversionModule;
import com.stevejrong.music.factory.provider.service.music.impl.ComplementsInfoForAudioFileModule;
import com.stevejrong.music.factory.spi.music.bo.AnalyzingForAudioFileModuleBo;
import com.stevejrong.music.factory.spi.service.music.IMusicFactoryModule;
import com.stevejrong.music.factory.spi.service.music.formatConversion.IAudioFileConverter;
import com.stevejrong.music.factory.spi.service.music.metadata.resolver.persist.IAudioFileMetadataPersistResolver;
import com.stevejrong.music.factory.spi.service.music.metadata.resolver.query.IAudioFileMetadataQueryResolver;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.*;
import org.jaudiotagger.tag.images.Artwork;
import org.jaudiotagger.tag.vorbiscomment.VorbisCommentTag;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

import static com.stevejrong.music.factory.common.util.SpringBeanUtil.getBean;

/**
 * 测试类
 *
 * @author Steve Jrong
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-bean.xml")
public class TestCase {

    @Test
    public void analyzingAudioFile() {
        IMusicFactoryModule<List<AnalyzingForAudioFileModuleBo>> analyzingInfoForAudioFileModule = getBean("analyzingInfoForAudioFileModule");
        List<AnalyzingForAudioFileModuleBo> needComplementsMusicList = analyzingInfoForAudioFileModule.doAction();
        Assert.assertNotNull(needComplementsMusicList);
    }

    @Test
    public void complementsInfoForAudioFile() {
        IMusicFactoryModule<List<AnalyzingForAudioFileModuleBo>> analyzingInfoForAudioFileModule = getBean("analyzingInfoForAudioFileModule");
        List<AnalyzingForAudioFileModuleBo> needComplementsMusicList = analyzingInfoForAudioFileModule.doAction();

        ComplementsInfoForAudioFileModule complementsInfoForAudioFileModule = getBean("complementsInfoForAudioFileModule");
        complementsInfoForAudioFileModule.setNeedComplementsAudioFileList(needComplementsMusicList);
        complementsInfoForAudioFileModule.doAction();
    }

    @Test
    public void complementsInfoForAudioFileManual() {
        String audioFilePath = "/Users/stevejrong/Desktop/test/Aanysa-Snakehips - Burn Break Crash.mp3";
        String songTitle = "Burn Break Crash", songArtist = "Aanysa-Snakehips";
        List<AnalyzingForAudioFileModuleBo> needComplementsMusicList = Lists
                .newArrayList(new AnalyzingForAudioFileModuleBo.Builder(audioFilePath, songTitle, songArtist).build());

        ComplementsInfoForAudioFileModule complementsInfoForAudioFileModule = getBean("complementsInfoForAudioFileModule");
        complementsInfoForAudioFileModule.setNeedComplementsAudioFileList(needComplementsMusicList);
        complementsInfoForAudioFileModule.doAction();
    }

    @Test
    public void getPictureSize() throws IOException {
        BufferedImage image = ImageIO.read(new FileInputStream(
                "/Users/stevejrong/Documents/Projects/IDEA-Projects/music-factory/src/main/resources/img/default_album_pic.png"));
        System.out.println(String.format("Image width: %d, weight: %d.", image.getWidth(), image.getHeight()));
    }

    @Test
    public void stringToLocalDateTest() {
        String dateString = "2020-02-28"; // [0-9]{4}(0[1-9]$|^1[0-2])[0-9]{2}
        Pattern pattern = Pattern.compile("(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)");
        System.out.println(pattern.matcher(dateString).matches());
    }

    @Test
    public void getAudioFileMetadataResolversConfig() {
        SystemConfig systemConfig = getBean("systemConfig");
        Map<String, List> resolvers = systemConfig.getAnalysingAndComplementsForAudioFileConfig().getAudioFileMetadataResolvers();
        Assert.assertNotNull(resolvers);
    }

    @Test
    public void getResolver() {
        String encodingType = "mp3";

        SystemConfig systemConfig = getBean("systemConfig");
        IAudioFileMetadataQueryResolver metadataQueryResolver = (IAudioFileMetadataQueryResolver) systemConfig
                .getAnalysingAndComplementsForAudioFileConfig().getAudioFileMetadataResolvers().get(encodingType)
                .stream().filter(resolver -> resolver instanceof IAudioFileMetadataQueryResolver).findAny().get();

        Assert.assertNotNull(metadataQueryResolver);
    }

    @Test
    public void getSearchKeywordsByFileName() {
        String fileName = "Beyonce - Halo";
        String keyword1 = fileName.substring(0, fileName.lastIndexOf("-")).trim();
        String keyword2 = fileName.substring(fileName.lastIndexOf("-") + 1).trim();
        System.out.println(keyword1 + ">>>>>>>>>" + keyword2);
    }

    @Test
    public void gsonBeanToStringTest() {
        TestBean2 bean2 = new TestBean2();
        bean2.setFavoriteColor("red");
        bean2.setFavoriteFoods(Lists.newArrayList("noodles", "rice", "beef soup", "chicken", "LaoGanMa"));
        bean2.setFavoriteFriendsNameAndAddress(new HashMap<String, String>() {{
            put("A", "AA");
            put("B", "BB");
            put("C", "CC");
        }});
        bean2.setFavoriteNum(100);

        TestBean1 bean1 = new TestBean1();
        bean1.setName("Mariah Carey");
        bean1.setSerialNum(33492576);
        bean1.setBean2(bean2);

        String jsonString = GsonUtil.beanToJsonString(bean1);
        Assert.assertNotNull(jsonString);
    }

    @Test
    public void gsonStringToBeanTest() {
        TestBean2 bean2 = new TestBean2();
        bean2.setFavoriteColor("red");
        bean2.setFavoriteFoods(Lists.newArrayList("noodles", "rice", "beef soup", "chicken", "LaoGanMa"));
        bean2.setFavoriteFriendsNameAndAddress(new HashMap<String, String>() {{
            put("A", "AA");
            put("B", "BB");
            put("C", "CC");
        }});
        bean2.setFavoriteNum(100);

        TestBean1 bean1 = new TestBean1();
        bean1.setName("Mariah Carey");
        bean1.setSerialNum(33492576);
        bean1.setBean2(bean2);

        String jsonString = GsonUtil.beanToJsonString(bean1);

        TestBean1 bean11 = GsonUtil.jsonStringToBean(jsonString, TestBean1.class);
        Assert.assertNotNull(bean11);
    }

    @Test
    public void beanUtilTest() {
        TestBean2 bean2 = new TestBean2();
        bean2.setFavoriteColor("red");
        bean2.setFavoriteFoods(Lists.newArrayList("noodles", "rice", "beef soup", "chicken", "LaoGanMa"));
        bean2.setFavoriteFriendsNameAndAddress(new HashMap<String, String>() {{
            put("A", "AA");
            put("B", "BB");
            put("C", "CC");
        }});
        bean2.setFavoriteNum(100);

        TestBean1 bean1 = new TestBean1();
        bean1.setName("Mariah Carey");
        bean1.setSerialNum(33492576);
        bean1.setBean2(bean2);

        Map<String, String> bean3Map = BeanMapperUtil.map(bean1, HashMap.class);
        Assert.assertNotNull(bean3Map);
    }

    @Test
    public void randomTest1() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            int f = (int) (Math.random() * 62);
            if (f < 10) {
                sb.append(f);
            } else if (f < 36) {
                sb.append((char) (f + 'A' - 10));
            } else {
                sb.append((char) (f + 'a' - 36));
            }
        }

        System.out.println(sb.toString().toUpperCase());
    }

    @Test
    public void randomTest2() {
        int val1 = RandomUtil.getARandomNumeric(1, 8);
        String val2 = RandomUtil.getRandomStringWithNumeric(20);
        String val3 = RandomUtil.getRandomStringWithNumericAndWord(20);

        System.out.println(String.format(">>>>>>>>>>>>>>>>>>>>>>>>>>\n1: %d\n2: %s\n3: %s\n>>>>>>>>>>>>>>>>>>>>>>>>>>", val1, val2, val3));
    }

    @Test
    public void imageForUrlToByteArrayTest() throws IOException {
        ArrayUtil.byteArrayToFile(HttpUtil.getImage("http://imge.kugou.com/stdmusic/20161109/20161109000208518738.jpg", null), "/Users/stevejrong/Desktop/demo.jpg");
    }

    @Test
    public void jaudiotaggerNewVersionTest() throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        AudioFile audioFile = AudioFileIO.read(new File("/Users/stevejrong/Desktop/test/Aanysa-Snakehips - Burn Break Crash.mp3"));

        Tag id3v2Tag = audioFile.getTagOrCreateDefault();
        AbstractTagFrame frame = (AbstractTagFrame) id3v2Tag.getFields("USLT").get(0);
        String val = frame.getBody().getObjectValue("Description").toString();
        Assert.assertNotNull(val);
    }

    @Test
    public void setAlbumPictureTest() throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        AudioFile audioFile = AudioFileIO.read(new File("/Users/stevejrong/Desktop/test/Aanysa-Snakehips - Burn Break Crash.mp3"));
        byte[] bytesOfPicture = ImageUtil.imageFileToByteArray("/Users/stevejrong/Desktop/demo500500.jpg");

        IAudioFileMetadataPersistResolver persistResolver = getBean("mp3MetadataPersistResolver");
        persistResolver.setAudioFile(audioFile);
        persistResolver.setAlbumPicture(bytesOfPicture);
    }

    @Test
    public void albumPicturePropertiesTest() throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        AudioFile audioFile = AudioFileIO.read(new File("/Users/stevejrong/Desktop/test/Aanysa-Snakehips - Burn Break Crash.mp3"));
        Tag tag = audioFile.getTagOrCreateDefault();
        Artwork artwork = tag.getFirstArtwork();
        String description = Optional.ofNullable(artwork.getDescription()).orElse("");
        int height = artwork.getHeight();
        int width = artwork.getWidth();
        String mimeType = Optional.ofNullable(artwork.getMimeType()).orElse("");
        int pictureType = artwork.getPictureType();
        Assert.assertNotNull(mimeType);
    }

    @Test
    public void getPictureInfoByBufferedImage() throws IOException {
        byte[] byteArrayForImage = ImageUtil.imageFileToByteArray("/Users/stevejrong/Desktop/demo.jpg");

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayForImage);
        BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);

        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        System.out.println(String.format("Picture width: %d, height: %d.", width, height));
    }

    @Test
    public void beanToJsonStringWithGson() {
        TestBean2 bean2 = new TestBean2();
        bean2.setFavoriteColor("red");
        bean2.setFavoriteFoods(Lists.newArrayList("noodles", "rice", "beef soup", "chicken", "LaoGanMa"));
        bean2.setFavoriteFriendsNameAndAddress(new HashMap<String, String>() {{
            put("A", "AA");
            put("B", "BB");
            put("C", "CC");
        }});
        bean2.setFavoriteNum(100);

        TestBean1 bean1 = new TestBean1();
        bean1.setName("Mariah Carey");
        bean1.setSerialNum(33492576);
        bean1.setBean2(bean2);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        String jsonBean1 = gson.toJson(bean1);
        String jsonBean2 = GsonUtil.beanToJsonString(bean1);
        String jsonBean3 = GsonUtil.beanToJsonString("Alan Walker、K-391、Tungevaag - Play.flac");
        Assert.assertNotNull(jsonBean1);
        Assert.assertNotNull(jsonBean2);
        Assert.assertNotNull(jsonBean3);
    }

    @Test
    public void isDirectoryTest() {
        boolean result = Files.isDirectory().test(new File("/Users/stevejrong/Desktop/test/Adam Young、Orjan Nilsen - In The Air.flac"));
        System.out.println(result ? "是目录" : "不是目录");
    }

    @Test
    public void dateFormatTest() {
        String dateString = "2020-03-1";
        Pattern DATE_PATTERN_OF_YYYYMMD_FORMAT = Pattern.compile("yyyy-MM-d");
    }

    @Test
    public void ID3V1TagCreateTest() throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException, CannotWriteException {
        File file = new File("/Users/stevejrong/Desktop/test/Lady Gaga - Donatella (Instrumental).mp3");
        AudioFile audioFile = AudioFileIO.read(file);
        MP3File mp3File = (MP3File) audioFile;

        ID3v1Tag id3v1Tag = mp3File.getID3v1Tag();

        if (!mp3File.hasID3v1Tag()) {
            id3v1Tag = new ID3v11Tag();
            id3v1Tag.setTitle(new String("标题".getBytes(), StandardCharsets.ISO_8859_1));
        }

        mp3File.setID3v1Tag(id3v1Tag);
        mp3File.commit();

        Assert.assertNotNull(id3v1Tag);
    }

    @Test
    public void ID3V2TagCreateTest() throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException, CannotWriteException {
        File file = new File("/Users/stevejrong/Desktop/test/Lady Gaga - Donatella (Instrumental).mp3");
        AudioFile audioFile = AudioFileIO.read(file);
        MP3File mp3File = (MP3File) audioFile;

        AbstractID3v2Tag id3v2Tag = mp3File.getID3v2Tag();

        if (!mp3File.hasID3v2Tag()) {
            id3v2Tag = new ID3v23Tag();
            id3v2Tag.setField(FieldKey.TITLE, "标题");
        }

        mp3File.setID3v2Tag(id3v2Tag);
        mp3File.commit();

        Assert.assertNotNull(id3v2Tag);
    }

    @Test
    public void replaceCharactersTest() {
        String sourceString = "Trademark/Cash Cash/Clean Bandit/The Chainsmokers/Jess Glynne/SirenXX";
        String regEx = "[\\n`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        System.out.println(">>>>>>>>>>>>>>>>>" + sourceString.replaceAll(regEx, " "));

        System.out.println("*****************" + StringUtil.removeSpecialChars(sourceString));
    }

    @Test
    public void imageFileReadTest() throws IOException {
        for (int i = 0; i < ImageIO.getReaderFormatNames().length; i++) {
            System.out.println((i + 1) + ":" + ImageIO.getReaderFormatNames()[i]);
        }

        BufferedImage image = ImageIO.read(new File("/Users/stevejrong/Desktop/20160110020233941355.jpg"));
        Assert.assertNotNull(image);
    }

    @Test
    public void formatConversionTest() {
        AudioFileFormatConversionModule formatConversionModule = getBean("audioFileFormatConversionModule");

        SystemConfig systemConfig = getBean("systemConfig");
        systemConfig.getAnalysingAndComplementsForAudioFileConfig().setAudioFileDirectory("/Users/stevejrong/Desktop/old");
        systemConfig.getAudioFileFormatConversionConfig().setConvertedAudioFileDirectory("/Users/stevejrong/Desktop/new");
        systemConfig.getAudioFileFormatConversionConfig().setSelectedAudioFileConverters(Lists.newArrayList((IAudioFileConverter) getBean("FLAC_to_OGG_VORBIS_Converter")));
        formatConversionModule.setSystemConfig(systemConfig);

        formatConversionModule.doAction();
    }

    @Test
    public void getFileSuffixTest() {
        System.out.println(FileUtil.getFileSuffixWithPoint("/Users/stevejrong/Desktop/20160110020233941355.jpg"));
    }

    @Test
    public void oggVorbisFileReadTest() throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
        File file = new File("/Users/stevejrong/Desktop/test/Taylor Swift - Style.ogg");
        AudioFile audioFile = AudioFileIO.read(file);
        VorbisCommentTag vorbisTag = (VorbisCommentTag) audioFile.getTag();

        String songTitle = audioFile.getTag().getFirstField(FieldKey.TITLE).toString();
        String songArtist = audioFile.getTag().getFirstField(FieldKey.ARTIST).toString();
        String songLyrics = audioFile.getTag().getFirstField(FieldKey.LYRICS).toString();
        String albumName = audioFile.getTag().getFirstField(FieldKey.ALBUM).toString();
        String albumArtist = audioFile.getTag().getFirstField(FieldKey.ALBUM_ARTIST).toString();
        String albumPublishDate = audioFile.getTag().getFirst("DATE").toString();
        String albumDescription = audioFile.getTag().getFirstField(FieldKey.COMMENT).toString();
        String albumLanguage = audioFile.getTag().getFirstField(FieldKey.LANGUAGE).toString();
        String albumCopyright = audioFile.getTag().getFirstField(FieldKey.COPYRIGHT).toString();
        byte[] albumPicture = audioFile.getTag().getFirstArtwork().getBinaryData();
    }

    @Test
    public void getCpus() {
        int cpuCoreCount = Runtime.getRuntime().availableProcessors();
        System.out.println(cpuCoreCount);
    }

    @Test
    public void ffmpegFormatConversionSingleTest() {
        IAudioFileConverter audioFileConverter = getBean("DSF_to_OGG_VORBIS_Converter");
        audioFileConverter.convert(
                "/Users/stevejrong/Desktop/old/Mariah Carey - Love Takes Time.dsf",
                "/Users/stevejrong/Desktop/new",
                "Mariah Carey - Love Takes Time");
    }

    @Test
    public void ffmpegFormatConversionTest() {
        AudioFileFormatConversionModule audioFileFormatConversionModule = getBean("audioFileFormatConversionModule");

        FLAC_to_OGG_VORBIS_Converter flac_to_ogg_vorbis_converter = SpringBeanUtil.getBean("FLAC_to_OGG_VORBIS_Converter");
        DSF_to_OGG_VORBIS_Converter dsf_to_ogg_vorbis_converter = SpringBeanUtil.getBean("DSF_to_OGG_VORBIS_Converter");

        SystemConfig systemConfig = getBean("systemConfig");
        systemConfig.getAnalysingAndComplementsForAudioFileConfig().setAudioFileDirectory("/Users/stevejrong/Desktop/old");
        /*AudioFileFormatConversionConfig audioFileFormatConversionConfig = SpringBeanUtil.getBean("audioFileFormatConversionConfig");
        systemConfig.setAudioFileFormatConversionConfig(audioFileFormatConversionConfig);*/
        systemConfig.getAudioFileFormatConversionConfig().setConvertedAudioFileDirectory("/Users/stevejrong/Desktop/new");
        systemConfig.getAudioFileFormatConversionConfig().setSelectedAudioFileConverters(Lists.newArrayList(flac_to_ogg_vorbis_converter, dsf_to_ogg_vorbis_converter));

        audioFileFormatConversionModule.doAction();

    }

    @Test
    public void fileUtilTest() {
        String fileNameWithoutSuffix = FileUtil.getFileNameWithoutSuffix("/Users/stevejrong/Desktop/new/Taylor Swift - Style.ogg");
        System.out.println(fileNameWithoutSuffix);
    }

    @Test
    public void generateBase64StringForAudioFileMetadataBlockPictureTest() throws DecoderException {
        String albumCoverPicturePath = "/Users/stevejrong/Desktop/old/2.png";

        int albumCoverType = 3;
        String albumCoverMimeType = "image/jpeg";
        int albumCoverMimeTypeLength = albumCoverMimeType.length();
        String albumCoverDescription = "Cover (front)";
        int albumCoverDescriptionLength = albumCoverDescription.length();

        StringBuffer sb = new StringBuffer(StringUtil.numberToHexString(albumCoverType, 4));
        sb.append(StringUtil.numberToHexString(albumCoverMimeTypeLength, 4))
                .append(StringUtil.stringToHexString(albumCoverMimeType))
                .append(StringUtil.numberToHexString(albumCoverDescriptionLength, 4))
                .append(StringUtil.stringToHexString(albumCoverDescription));

        for (int i = 0; i < 4; i++) {
            sb.append(StringUtil.numberToHexString(0, 4));
        }

        sb.append(StringUtil.numberToHexString(FileUtil.getFileSize(albumCoverPicturePath), 4));

        byte[] byteArray = ImageUtil.imageFileToByteArray(albumCoverPicturePath);
        sb.append(StringUtil.byteArrayToHexString(byteArray));

        byte[] resultBytes1 = Hex.decodeHex(sb.toString());
        // String result1 = new BASE64Encoder().encode(resultBytes1);

        String result2 = new String(Base64.encodeBase64(Hex.decodeHex(sb.toString())));
        System.out.println(result2);
    }

    @Test
    public void pictureCompressTest() throws IOException {
        BufferedImage bufferedImage = ArrayUtil.getBufferedImageByPictureByteArray(
                ImageUtil.imageFileToByteArray("/Users/stevejrong/Desktop/old/3.jpeg"));

        BufferedImage newBufferedImage = Thumbnails.of(bufferedImage).size(400, 400).asBufferedImage();
        ImageIO.write(newBufferedImage, "jpeg", new File("/Users/stevejrong/Desktop/old/3_compressed.jpeg"));
    }

    @Test
    public void dsfAudioFileReadTest() throws CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException, IOException {
        File file = new File("/Users/stevejrong/Desktop/old/Mariah Carey - Love Takes Time.dsf");
        AudioFile audioFile = AudioFileIO.read(file);
        AbstractID3v2Tag tag = (AbstractID3v2Tag) audioFile.getTag();

        /**
         Tag content:
         TIT2:Text="Love Takes Time";
         TPE1:Text="Mariah Carey";
         TALB:Text="Mariah Carey";
         TCON:Text="Pop";
         TRCK:Text="15";
         APIC:MIMEType="image/jpeg"; Description="1.jpg"; PictureData="295897 bytes";
         USLT:Language="English"; Lyrics="歌词歌词";
         COMM:Language="English"; Text="描述";
         TLAN:Text="英语";
         TCOP:Text="索尼音乐";
         TYERTDAT:org.jaudiotagger.tag.id3.TyerTdatAggregatedFrame@142b73b
         */
        System.out.println(tag);
    }

    @Test
    public void querySongInfoTest() throws CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException, IOException {
        AudioFile audioFile = AudioFileIO.read(new File("/Users/stevejrong/Desktop/old/Mariah Carey - Love Takes Time.dsf"));
        IAudioFileMetadataQueryResolver metadataQueryResolver = getBean("dsfMetadataQueryResolver");
        metadataQueryResolver.setAudioFile(audioFile);

        String songTitle = metadataQueryResolver.getSongTitle();
        String songArtist = metadataQueryResolver.getSongArtist();
        String songLyrics = metadataQueryResolver.getSongLyrics();
        String albumName = metadataQueryResolver.getAlbumName();
        String albumArtist = metadataQueryResolver.getAlbumArtist();
        LocalDate albumPublishDate = metadataQueryResolver.getAlbumPublishDate();
        String albumDescription = metadataQueryResolver.getAlbumDescription();
        String albumLanguage = metadataQueryResolver.getAlbumLanguage();
        String albumCopyright = metadataQueryResolver.getAlbumCopyright();
        byte[] albumPicture = metadataQueryResolver.getAlbumPicture(true);

        System.out.println(audioFile);
    }

    @Test
    public void persistSongInfoTest() throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        AudioFile audioFile = AudioFileIO.read(new File("/Users/stevejrong/Desktop/old/Mariah Carey - Love Takes Time.dsf"));

        IAudioFileMetadataPersistResolver metadataPersistResolver = getBean("dsfMetadataPersistResolver");
        metadataPersistResolver.setAudioFile(audioFile);
        metadataPersistResolver.setIAudioFileMetadataQueryResolver(getBean("dsfMetadataQueryResolver"));

        metadataPersistResolver.setSongTitle("新设置的标题");
        metadataPersistResolver.setSongArtist("新设置的艺术家");
        metadataPersistResolver.setAlbumName("新设置的专辑名称");
        metadataPersistResolver.setAlbumPicture(ImageUtil.imageFileToByteArray("/Users/stevejrong/Desktop/一加LOGO.png"));
        metadataPersistResolver.setSongLyrics("新设置的内嵌歌词");
        metadataPersistResolver.setAlbumArtist("新设置的专辑艺术家");
        metadataPersistResolver.setAlbumPublishDate(DateTimeUtil.stringToLocalDate(DateTimeUtil.DatePattern.YYYYMMDD_FORMAT.getValue(), "2000-12-31"));
        metadataPersistResolver.setAlbumDescription("新设置的描述");
        metadataPersistResolver.setAlbumLanguage("eng");
        metadataPersistResolver.setAlbumCopyright("© 新设置的版权");
    }

    @Test
    public void commandTest() throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        String[] commands = {
                "/bin/sh", "/Users/stevejrong/Desktop/test.sh",
                "/Users/stevejrong/Desktop/old/Mariah Carey - Love Takes Time.dsf",
                "/Users/stevejrong/Desktop/old/Mariah Carey - Love Takes Time.ogg"
        };
        processBuilder.command(commands);
        //将标准输入流和错误输入流合并，通过标准输入流读取信息
        processBuilder.redirectErrorStream(true);
        try {
            //启动进程
            Process start = processBuilder.start();
            //获取输入流
            InputStream inputStream = start.getInputStream();
            //转成字符输入流
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            int len = -1;
            char[] c = new char[1024];
            StringBuffer outputString = new StringBuffer();
            //读取进程输入流中的内容
            while ((len = inputStreamReader.read(c)) != -1) {
                String s = new String(c, 0, len);
                outputString.append(s);
                System.out.print(s);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void anaysingAudioFileTest() {
        AnalyzingInfoForAudioFileModule analyzingInfoForAudioFileModule = SpringBeanUtil.getBean("analyzingInfoForAudioFileModule");
        analyzingInfoForAudioFileModule.getSystemConfig().getAnalysingAndComplementsForAudioFileConfig().setAudioFileDirectory("/Users/stevejrong/Desktop/old");
        List<AnalyzingForAudioFileModuleBo> analyzingForAudioFileModuleBos = analyzingInfoForAudioFileModule.doAction();

        ComplementsInfoForAudioFileModule complementsInfoForAudioFileModule = SpringBeanUtil.getBean("complementsInfoForAudioFileModule");
        complementsInfoForAudioFileModule.setNeedComplementsAudioFileList(analyzingForAudioFileModuleBos);
        complementsInfoForAudioFileModule.doAction();
    }
}