package com.sloydev.busparser.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonDiff;
import com.sloydev.busparser.core.DiffOutput;
import com.sloydev.busparser.core.model.Linea;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class JsonPatchDiffOutput implements DiffOutput {

    private final ObjectMapper objectMapper;

    public JsonPatchDiffOutput(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void outputDiffLineas(List<Linea> left, List<Linea> right) {
        JsonNode nodeLeft = objectMapper.valueToTree(left);
        JsonNode nodeRight = objectMapper.valueToTree(right);

        JsonNode diffNode = JsonDiff.asJson(nodeLeft, nodeRight);

        System.out.println("left -> " + nodeLeft.toString());
        System.out.println("right -> " + nodeRight.toString());
        System.out.println("diff -> " + diffNode.toString());

        try {
            Files.write(Paths.get("out/jsondiffpatch/data.js"), Arrays.asList("var left = " + nodeLeft, "var right = " + nodeRight));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
