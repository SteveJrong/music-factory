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
package com.stevejrong.music.factory.provider.service.music.formatConversion.impl;

import com.google.common.collect.Maps;
import com.stevejrong.music.factory.common.constants.BaseConstants;
import com.stevejrong.music.factory.common.util.AlbumPictureUtil;
import com.stevejrong.music.factory.common.util.FFmpegUtil;
import com.stevejrong.music.factory.spi.music.bo.formatConversion.FFmpegBuilderBo;
import com.stevejrong.music.factory.spi.service.music.formatConversion.AbstractAudioFileConverterUsePipeInDllFile;
import com.stevejrong.music.factory.spi.service.music.formatConversion.IAudioFileConverter;

import java.io.File;
import java.util.Map;

/**
 * DSF（索尼专有格式）音频编码格式 转换为 Ogg Vorbis音频编码格式 音频转换器
 *
 * @author Steve Jrong
 * @since 1.0
 */
public class DSF_to_OGG_VORBIS_Converter extends AbstractAudioFileConverterUsePipeInDllFile implements IAudioFileConverter {

    @Override
    public int converterNum() {
        return 2;
    }

    @Override
    public String sourceFileSuffix() {
        return BaseConstants.POINT_CHAR + BaseConstants.FILE_SUFFIX_DSF;
    }

    @Override
    public String targetFileSuffix() {
        return BaseConstants.POINT_CHAR + BaseConstants.FILE_SUFFIX_OGG;
    }

    @Override
    protected Map<String, String> addReplaceStringsMapOfExecuteCommand(FFmpegBuilderBo ffmpegBuilderBo) {
        Map<String, String> replaceStringsMap = Maps.newHashMap();
        replaceStringsMap.put("@ffmpegPath", FFmpegUtil.getFfmpegFilePath());
        replaceStringsMap.put("@sourceFilePath", ffmpegBuilderBo.getSourcePath());
        replaceStringsMap.put("@metadata_block_picture", AlbumPictureUtil.buildBase64BlobMetadataStringOfAlbumPictureByOggVorbis(
                AlbumPictureUtil.albumPictureCompressByAlbumPictureByteArray(
                        500, 500,
                        super.getAlbumPictureByteArray(ffmpegBuilderBo.getSourcePath()))));
        replaceStringsMap.put("@targetFilePath", ffmpegBuilderBo.getTargetDirectory() + File.separatorChar + ffmpegBuilderBo.getTargetFileName() + this.targetFileSuffix());

        return replaceStringsMap;
    }
}