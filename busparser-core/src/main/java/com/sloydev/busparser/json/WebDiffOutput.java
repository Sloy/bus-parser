package com.sloydev.busparser.json;

import com.sloydev.busparser.core.DiffOutput;
import com.sloydev.busparser.core.model.Linea;
import com.sloydev.jsonadapters.JsonAdapter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WebDiffOutput implements DiffOutput {

    private final JsonAdapter jsonAdapter;

    public WebDiffOutput(JsonAdapter jsonAdapter) {
        this.jsonAdapter = jsonAdapter;
    }

    @Override
    public void outputDiffLineas(List<Linea> left, List<Linea> right) {

        Map<String, Linea> leftMap = left.stream()
          .collect(Collectors.toMap(Linea::getNumero, Function.identity()));
        Map<String, Linea> rightMap = right.stream()
          .collect(Collectors.toMap(Linea::getNumero, Function.identity()));

        String nodeLeft = jsonAdapter.toJson(leftMap);
        String nodeRight= jsonAdapter.toJson(rightMap);

        try {
            Files.write(Paths.get("out/jsondiffpatch/data.js"), Arrays.asList("var left = " + nodeLeft, "var right = " + nodeRight));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
