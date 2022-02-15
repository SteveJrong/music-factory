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
package com.stevejrong.music.factory.spi.service.music.metadata.resolver.persist;

import com.stevejrong.music.factory.spi.service.music.metadata.resolver.query.IAudioFileMetadataQueryResolver;
import org.jaudiotagger.audio.AudioFile;


/**
 * 音频文件文件元数据存储器抽象类
 * <p>
 * 承载音频文件AudioFile对象以及解析器对象
 *
 * @author Steve Jrong
 * @since 1.0
 */
public abstract class AbstractAudioFileMetadataPersistResolver {

    /**
     * 音频文件对象
     */
    protected AudioFile audioFile;

    /**
     * 音频文件文件元数据解析器接口
     */
    protected IAudioFileMetadataQueryResolver metadataQueryResolver;

    protected AudioFile getAudioFile() {
        return audioFile;
    }

    public IAudioFileMetadataQueryResolver getMetadataQueryResolver() {
        return metadataQueryResolver;
    }
}
