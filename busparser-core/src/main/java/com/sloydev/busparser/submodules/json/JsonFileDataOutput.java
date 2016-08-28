package com.sloydev.busparser.submodules.json;

import com.sloydev.busparser.core.model.DataOutput;
import com.sloydev.busparser.core.model.Linea;
import com.sloydev.busparser.core.model.Parada;
import com.sloydev.busparser.submodules.json.internal.LineaJson;
import com.sloydev.busparser.submodules.json.internal.ParadaJson;
import com.sloydev.jsonadapters.JsonAdapter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        String json = jsonAdapter.toJson(lineas.stream()
                .map(LineaJson::fromLinea)
                .collect(Collectors.toList()));

        Path path = Paths.get(lineasFilePath);
        try {
            Files.createDirectories(path.getParent());
            Files.write(path, Collections.singletonList(json), Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void outputParadas(List<Parada> paradas) {
        String json = jsonAdapter.toJson(paradas.stream()
                .map(ParadaJson::fromParada)
                .collect(Collectors.toList()));

        Path path = Paths.get(paradasFilePath);
        try {
            Files.createDirectories(path.getParent());
            Files.write(path, Collections.singletonList(json), Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
