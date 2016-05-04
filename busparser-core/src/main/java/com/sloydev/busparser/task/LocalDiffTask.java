package com.sloydev.busparser.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sloydev.busparser.core.DataSource;
import com.sloydev.busparser.core.DiffOutput;
import com.sloydev.busparser.json.JsonPatchDiffOutput;
import com.sloydev.busparser.service.DiffService;
import com.sloydev.busparser.sql.SqlDataSource;

import java.nio.file.Paths;

public class LocalDiffTask {

    public static void main(String[] args) {
        DataSource dataSource1 = new SqlDataSource(Paths.get("in/lineas.sql"), Paths.get("in/secciones.sql"));
        DataSource dataSource2 = new SqlDataSource(Paths.get("in/lineas2.sql"), Paths.get("in/secciones2.sql"));
        DiffOutput diffOutput = new JsonPatchDiffOutput(new ObjectMapper());

        DiffService diffService = new DiffService(dataSource1, dataSource2, diffOutput);

        diffService.run();
    }

}
