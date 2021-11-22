/**
 * Copyright 2021 Steve Jrong
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stevejrong.music.factory.test;

import com.google.common.collect.Lists;
import com.stevejrong.music.factory.bean.TestBean1;
import com.stevejrong.music.factory.bean.TestBean2;
import com.stevejrong.music.factory.common.util.*;
import com.stevejrong.music.factory.config.SystemConfig;
import com.stevejrong.music.factory.provider.service.music.impl.ComplementsInfoForAudioFileModule;
import com.stevejrong.music.factory.spi.music.bo.AnalyzingForAudioFileModuleBo;
import com.stevejrong.music.factory.spi.service.music.IMusicFactoryModule;
import com.stevejrong.music.factory.spi.service.music.metadata.resolver.persist.IAudioFileMetadataPersistResolver;
import com.stevejrong.music.factory.spi.service.music.metadata.resolver.query.IAudioFileMetadataQueryResolver;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.AbstractTagFrame;
import org.jaudiotagger.tag.images.Artwork;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

/**
 * @author Steve Jrong
 * create date: 2021-11-12 11:14 PM
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-bean.xml")
public class TestCase {

    @Test
    public void analyzingAudioFile() {
        IMusicFactoryModule<List<AnalyzingForAudioFileModuleBo>> analyzingInfoForAudioFileModule = SpringBeanUtil.getBean("analyzingInfoForAudioFileModule");
        List<AnalyzingForAudioFileModuleBo> needComplementsMusicList = analyzingInfoForAudioFileModule.doAction();
        Assert.assertNotNull(needComplementsMusicList);
    }

    @Test
    public void ComplementsInfoForAudioFile() {
        IMusicFactoryModule<List<AnalyzingForAudioFileModuleBo>> analyzingInfoForAudioFileModule = SpringBeanUtil.getBean("analyzingInfoForAudioFileModule");
        List<AnalyzingForAudioFileModuleBo> needComplementsMusicList = analyzingInfoForAudioFileModule.doAction();

        ComplementsInfoForAudioFileModule complementsInfoForAudioFileModule = SpringBeanUtil.getBean("complementsInfoForAudioFileModule");
        complementsInfoForAudioFileModule.setNeedComplementsMusicList(needComplementsMusicList);
        complementsInfoForAudioFileModule.doAction();
    }

    @Test
    public void ComplementsInfoForAudioFileManual() {
        String audioFilePath = "/Users/stevejrong/Desktop/test/Aanysa-Snakehips - Burn Break Crash.mp3";
        String songTitle = "Burn Break Crash", songArtist = "Aanysa-Snakehips";
        List<AnalyzingForAudioFileModuleBo> needComplementsMusicList = Lists
                .newArrayList(new AnalyzingForAudioFileModuleBo.Builder(audioFilePath, songTitle, songArtist).build());

        ComplementsInfoForAudioFileModule complementsInfoForAudioFileModule = SpringBeanUtil.getBean("complementsInfoForAudioFileModule");
        complementsInfoForAudioFileModule.setNeedComplementsMusicList(needComplementsMusicList);
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
        String val = "20130406";
        LocalDate date = DateTimeUtil.stringToLocalDate(DateTimeUtil.DatePattern.YYYYMMDD_FORMAT_WITHOUT_SYMBOL.getValue(), val);
        System.out.println(date);
    }

    @Test
    public void getAudioFileMetadataResolversConfig() {
        SystemConfig systemConfig = SpringBeanUtil.getBean("systemConfig");
        Map<String, List> resolvers = systemConfig.getAnalysingAndComplementsForAudioFileConfig().getAudioFileMetadataResolvers();
        Assert.assertNotNull(resolvers);
    }

    @Test
    public void getResolver() {
        String encodingType = "mp3";

        SystemConfig systemConfig = SpringBeanUtil.getBean("systemConfig");
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
        FileUtil.byteArrayToFile(HttpUtil.getImage("http://imge.kugou.com/stdmusic/20161109/20161109000208518738.jpg", null), "/Users/stevejrong/Desktop/demo.jpg");
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
        byte[] bytesOfPicture = FileUtil.imageFileToByteArray("/Users/stevejrong/Desktop/demo500500.jpg");

        Tag persistTag = audioFile.getTagOrCreateDefault();
        IAudioFileMetadataPersistResolver persistResolver = SpringBeanUtil.getBean("mp3MetadataPersistResolver");
        persistResolver.setAlbumPicture(audioFile, persistTag, bytesOfPicture);
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
        byte[] byteArrayForImage = FileUtil.imageFileToByteArray("/Users/stevejrong/Desktop/demo.jpg");

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayForImage);
        BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);

        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        System.out.println(String.format("Picture width: %d, height: %d.", width, height));
    }
}