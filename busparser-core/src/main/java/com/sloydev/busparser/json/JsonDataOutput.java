package com.sloydev.busparser.json;

import com.sloydev.busparser.core.DataOutput;
import com.sloydev.busparser.core.model.Linea;
import com.sloydev.jsonadapters.JsonAdapter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class JsonDataOutput implements DataOutput {

    private final String filePath;
    private final JsonAdapter jsonAdapter;

    public JsonDataOutput(String filePath, JsonAdapter jsonAdapter) {
        this.filePath = filePath;
        this.jsonAdapter = jsonAdapter;
    }

    @Override
    public void outputLineas(List<Linea> lineas) {
        String json = jsonAdapter.toJson(lineas);

        Path path = Paths.get(filePath);
        try {
            Files.write(path, Collections.singletonList(json), Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
