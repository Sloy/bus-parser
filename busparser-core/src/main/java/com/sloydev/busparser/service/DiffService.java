package com.sloydev.busparser.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonDiff;
import com.sloydev.busparser.core.DataSource;
import com.sloydev.busparser.core.model.Linea;

import java.util.List;

public class DiffService {

    private final DataSource dataSource1;
    private final DataSource dataSource2;
    private final ObjectMapper objectMapper;
    //TODO have some abstract output model

    public DiffService(DataSource dataSource1, DataSource dataSource2, ObjectMapper objectMapper) {
        this.dataSource1 = dataSource1;
        this.dataSource2 = dataSource2;
        this.objectMapper = objectMapper;
    }

    public void run() {
        List<Linea> lineas1 = dataSource1.obtainLineas();
        List<Linea> lineas2 = dataSource2.obtainLineas();

        JsonNode node1 = objectMapper.valueToTree(lineas1);
        JsonNode node2 = objectMapper.valueToTree(lineas2);

        JsonNode diffNode = JsonDiff.asJson(node1, node2);
        String diffText = diffNode.toString();

        System.out.println(diffText);
    }
}
