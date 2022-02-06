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
package com.stevejrong.music.factory.provider.service.music.metadata.resolver.query.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagField;
import org.jaudiotagger.tag.vorbiscomment.VorbisCommentTag;
import org.jaudiotagger.tag.vorbiscomment.VorbisCommentTagField;

import com.google.common.collect.Lists;
import com.stevejrong.music.factory.common.util.DateTimeUtil;
import com.stevejrong.music.factory.spi.service.music.metadata.resolver.query.IAudioFileMetadataQueryResolver;

/**
 * Ogg Vorbis音频文件的元数据解析器
 *
 * @author Steve Jrong
 * @since 1.0
 */
public class OggVorbisMetadataQueryResolver implements IAudioFileMetadataQueryResolver {

	@Override
	public String getSongTitle(AudioFile audioFile) {
		VorbisCommentTag vorbisCommentTag = (VorbisCommentTag) audioFile.getTag();

		TagField songTitleTagField = vorbisCommentTag.getFirstField(FieldKey.TITLE);
		if (null != songTitleTagField
				&& StringUtils.isNotBlank(((VorbisCommentTagField) songTitleTagField).getContent())) {
			return ((VorbisCommentTagField) (songTitleTagField)).getContent();
		}

		return null;
	}

	@Override
	public String getSongArtist(AudioFile audioFile) {
		VorbisCommentTag vorbisCommentTag = (VorbisCommentTag) audioFile.getTag();

		TagField songArtistTagField = vorbisCommentTag.getFirstField(FieldKey.ARTIST);
		if (null != songArtistTagField
				&& StringUtils.isNotBlank(((VorbisCommentTagField) songArtistTagField).getContent())) {
			return ((VorbisCommentTagField) (songArtistTagField)).getContent();
		}

		return null;
	}

	@Override
	public String getSongLyrics(AudioFile audioFile) {
		VorbisCommentTag vorbisCommentTag = (VorbisCommentTag) audioFile.getTag();

		TagField songLyricsTagField = vorbisCommentTag.getFirstField(FieldKey.LYRICS);
		if (null != songLyricsTagField
				&& StringUtils.isNotBlank(((VorbisCommentTagField) songLyricsTagField).getContent())) {
			return ((VorbisCommentTagField) songLyricsTagField).getContent();
		}

		return null;
	}

	@Override
	public String getAlbumName(AudioFile audioFile) {
		VorbisCommentTag vorbisCommentTag = (VorbisCommentTag) audioFile.getTag();

		TagField albumNameTagField = vorbisCommentTag.getFirstField(FieldKey.ALBUM);
		if (null != albumNameTagField
				&& StringUtils.isNotBlank(((VorbisCommentTagField) albumNameTagField).getContent())) {
			return ((VorbisCommentTagField) albumNameTagField).getContent();
		}

		return null;
	}

	@Override
	public String getAlbumArtist(AudioFile audioFile) {
		VorbisCommentTag vorbisCommentTag = (VorbisCommentTag) audioFile.getTag();

		TagField albumArtistTagField = vorbisCommentTag.getFirstField(FieldKey.ALBUM_ARTIST);
		if (null != albumArtistTagField
				&& StringUtils.isNotBlank(((VorbisCommentTagField) albumArtistTagField).getContent())) {
			return ((VorbisCommentTagField) albumArtistTagField).getContent();
		}

		return null;
	}

	@Override
	public LocalDate getAlbumPublishDate(AudioFile audioFile) {
		VorbisCommentTag vorbisCommentTag = (VorbisCommentTag) audioFile.getTag();

		List<TagField> albumPublishDateTagFields = Optional.ofNullable(vorbisCommentTag.getFields("DATE"))
				.orElse(Lists.newArrayList());
		String albumPublishDate;
		if (CollectionUtils.isNotEmpty(albumPublishDateTagFields)) {
			albumPublishDate = ((VorbisCommentTagField) albumPublishDateTagFields.get(0)).getContent();

			if (DateTimeUtil.DATE_PATTERN_OF_YYYYMMDD_FORMAT.matcher(albumPublishDate).matches()) {

				return DateTimeUtil.stringToLocalDate(DateTimeUtil.DatePattern.YYYYMMDD_FORMAT.getValue(),
						((VorbisCommentTagField) albumPublishDateTagFields.get(0)).getContent());
			} else if (DateTimeUtil.DATE_PATTERN_OF_YYYYMMDD_FORMAT_WITHOUT_SYMBOL.matcher(albumPublishDate)
					.matches()) {

				return DateTimeUtil.stringToLocalDate(
						DateTimeUtil.DatePattern.YYYYMMDD_FORMAT_WITHOUT_SYMBOL.getValue(),
						((VorbisCommentTagField) albumPublishDateTagFields.get(0)).getContent());
			}
		}

		// FLAC中的标签支持年月日完整存储。除了形如 0000-00-00 或 00001122
		// 这两种形式外，其余形式均认为发布时间不存在，返回null打标签，来进行补全
		return null;
	}

	@Override
	public String getAlbumDescription(AudioFile audioFile) {
		VorbisCommentTag vorbisCommentTag = (VorbisCommentTag) audioFile.getTag();

		TagField albumDescriptionTagField = vorbisCommentTag.getFirstField(FieldKey.COMMENT);
		if (null != albumDescriptionTagField
				&& StringUtils.isNotBlank(((VorbisCommentTagField) albumDescriptionTagField).getContent())) {
			return ((VorbisCommentTagField) albumDescriptionTagField).getContent();
		}

		return null;
	}

	@Override
	public String getAlbumLanguage(AudioFile audioFile) {
		VorbisCommentTag vorbisCommentTag = (VorbisCommentTag) audioFile.getTag();

		TagField albumLanguageTagField = vorbisCommentTag.getFirstField(FieldKey.LANGUAGE);
		if (null != albumLanguageTagField
				&& StringUtils.isNotBlank(((VorbisCommentTagField) albumLanguageTagField).getContent())) {
			return ((VorbisCommentTagField) albumLanguageTagField).getContent();
		}

		return null;
	}

	@Override
	public String getAlbumCopyright(AudioFile audioFile) {
		VorbisCommentTag vorbisCommentTag = (VorbisCommentTag) audioFile.getTag();

		TagField albumCopyrightTagField = vorbisCommentTag.getFirstField(FieldKey.COPYRIGHT);
		if (null != albumCopyrightTagField
				&& StringUtils.isNotBlank(((VorbisCommentTagField) albumCopyrightTagField).getContent())) {
			return ((VorbisCommentTagField) albumCopyrightTagField).getContent();
		}

		return null;
	}

	@Override
	public byte[] getAlbumPicture(AudioFile audioFile, boolean sizeLimit) {
		VorbisCommentTag vorbisCommentTag = (VorbisCommentTag) audioFile.getTag();

		byte[] originalAlbumPictureData;
		if (null != vorbisCommentTag.getFirstArtwork()
				&& ArrayUtils.isNotEmpty(vorbisCommentTag.getFirstArtwork().getBinaryData())) {
			originalAlbumPictureData = vorbisCommentTag.getFirstArtwork().getBinaryData();

			BufferedImage image = null;
			try {
				image = ImageIO.read(new ByteArrayInputStream(originalAlbumPictureData));
			} catch (IOException e) {
			}

			if (sizeLimit && (image.getWidth() * image.getHeight() >= 500 * 500)) {

				// 当开启专辑封面尺寸限定、专辑封面存在且符合尺寸时，才返回专辑图片的字节数组
				return originalAlbumPictureData;
			} else if (!sizeLimit) {

				// 若专辑封面尺寸限定已关闭，则表明无需限制尺寸，直接返回图片数组
				return originalAlbumPictureData;
			}
		}

		return null;
	}
}
