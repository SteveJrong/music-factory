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

import org.jaudiotagger.audio.flac.metadatablock.MetadataBlockDataPicture;

import java.awt.image.BufferedImage;

/**
 * @author Steve Jrong
 * create date: 2021-11-22 11:41 AM
 * @since 1.0
 */
public final class AlbumCoverUtil {

    /**
     * 构建用于专辑封面的MetadataBlockDataPicture图片对象
     *
     * @param albumPictureByteArray 图片Byte数组
     * @return MetadataBlockDataPicture图片对象
     */
    public static MetadataBlockDataPicture buildMetadataBlockDataPicture(byte[] albumPictureByteArray) {
        BufferedImage bufferedImage = ArrayUtil.byteArrayToBufferImage(albumPictureByteArray);

        return new MetadataBlockDataPicture(albumPictureByteArray,
                bufferedImage.getType(),
                "image/jpeg",
                "",
                bufferedImage.getWidth(),
                bufferedImage.getHeight(),
                0,
                0);
    }
}