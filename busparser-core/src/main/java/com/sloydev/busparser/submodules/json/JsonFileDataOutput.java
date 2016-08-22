package com.sloydev.busparser.submodules.json;

import com.sloydev.busparser.core.model.DataOutput;
import com.sloydev.busparser.core.model.Linea;
import com.sloydev.busparser.core.model.Parada;
import com.sloydev.jsonadapters.JsonAdapter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class JsonFileDataOutput implements DataOutput {

    private final String lineasFilePath;
    private final String paradasFilePath;
    private final JsonAdapter jsonAdapter;

    public JsonFileDataOutput(String lineasFilePath, String paradasFilePath, JsonAdapter jsonAdapter) {
        this.lineasFilePath = lineasFilePath;
        this.paradasFilePath = paradasFilePath;
        this.jsonAdapter = jsonAdapter;
    }

    @Override
    public void outputLineas(List<Linea> lineas) {
        String json = jsonAdapter.toJson(lineas);

        Path path = Paths.get(lineasFilePath);
        try {
            Files.write(path, Collections.singletonList(json), Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void outputParadas(List<Parada> paradas) {
        String json = jsonAdapter.toJson(paradas);

        Path path = Paths.get(paradasFilePath);
        try {
            Files.write(path, Collections.singletonList(json), Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
