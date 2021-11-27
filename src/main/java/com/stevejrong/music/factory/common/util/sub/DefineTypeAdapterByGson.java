package com.stevejrong.music.factory.common.util.sub;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class DefineTypeAdapterByGson<T> extends TypeAdapter<T> {

    @Override
    public T read(JsonReader reader) {
        return null;
    }

    @Override
    public void write(JsonWriter writer, T obj) throws IOException {
        if (obj == null) {
            writer.nullValue();
            return;
        }
        writer.value(obj.toString());
    }
}
