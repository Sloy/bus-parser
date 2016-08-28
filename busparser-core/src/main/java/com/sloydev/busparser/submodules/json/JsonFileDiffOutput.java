package com.sloydev.busparser.submodules.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonDiff;
import com.sloydev.busparser.core.model.DiffOutput;
import com.sloydev.busparser.core.model.Linea;
import com.sloydev.busparser.core.model.Parada;
import com.sloydev.busparser.submodules.json.internal.LineaJson;
import com.sloydev.busparser.submodules.json.internal.ParadaJson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JsonFileDiffOutput implements DiffOutput {

    private final ObjectMapper objectMapper;

    public JsonFileDiffOutput(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void outputDiffLineas(List<Linea> left, List<Linea> right) {
        Map<String, LineaJson> mapLeft = left.stream()
                .map(LineaJson::fromLinea)
                .collect(Collectors.toMap(lineaJson -> lineaJson.numero, Function.identity()));

        Map<String, LineaJson> mapRight = right.stream()
                .map(LineaJson::fromLinea)
                .collect(Collectors.toMap(lineaJson -> lineaJson.numero, Function.identity()));

        JsonNode nodeLeft = objectMapper.valueToTree(mapLeft);
        JsonNode nodeRight = objectMapper.valueToTree(mapRight);

        JsonNode diffNode = JsonDiff.asJson(nodeLeft, nodeRight);

        try {
            Path path = Paths.get("out/diff/data_lineas.js");
            Files.createDirectories(path.getParent());
            Files.write(path, Arrays.asList("var left = " + nodeLeft, "var right = " + nodeRight, "var delta = " + diffNode));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void outputDiffParadas(List<Parada> left, List<Parada> right) {
        Map<Integer, ParadaJson> mapLeft = left.stream()
                .map(ParadaJson::fromParada)
                .collect(Collectors.toMap(paradaJson -> paradaJson.numero, Function.identity()));

        Map<Integer, ParadaJson> mapRight = right.stream()
                .map(ParadaJson::fromParada)
                .collect(Collectors.toMap(paradaJson -> paradaJson.numero, Function.identity()));

        JsonNode nodeLeft = objectMapper.valueToTree(mapLeft);
        JsonNode nodeRight = objectMapper.valueToTree(mapRight);

        JsonNode diffNode = JsonDiff.asJson(nodeLeft, nodeRight);

        try {
            Path path = Paths.get("out/diff/data_paradas.js");
            Files.createDirectories(path.getParent());
            Files.write(path, Arrays.asList("var left = " + nodeLeft, "var right = " + nodeRight, "var delta = " + diffNode));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
