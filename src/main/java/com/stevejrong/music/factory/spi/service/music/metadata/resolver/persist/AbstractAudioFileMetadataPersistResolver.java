package com.stevejrong.music.factory.spi.service.music.metadata.resolver.persist;

import com.stevejrong.music.factory.spi.service.music.metadata.resolver.query.IAudioFileMetadataQueryResolver;
import org.jaudiotagger.audio.AudioFile;

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
