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
package com.stevejrong.music.factory.spi.service.music.parallel.albumPictureCompression;

import com.stevejrong.music.factory.spi.music.bo.parallel.albumPictureCompression.AlbumPictureCompressionTaskBo;
import com.stevejrong.music.factory.spi.service.music.parallel.IMultiThreadedTaskProcessor;

/**
 * 音频文件专辑封面压缩器接口
 *
 * @author Steve Jrong
 * @since 1.0
 */
public interface IAudioFileAlbumPictureCompressor extends IMultiThreadedTaskProcessor<AlbumPictureCompressionTaskBo> {

    /**
     * 音频文件专辑封面压缩的像素值（长宽像素值相同）
     *
     * @return 音频文件专辑封面压缩的像素值
     */
    int compressPixelValue();

    @Override
    boolean execute(AlbumPictureCompressionTaskBo paramBo);
}