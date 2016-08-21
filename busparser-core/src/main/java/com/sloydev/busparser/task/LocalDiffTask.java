package com.sloydev.busparser.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sloydev.busparser.core.model.DataSource;
import com.sloydev.busparser.core.model.DiffOutput;
import com.sloydev.busparser.submodules.json.JsonFileDiffOutput;
import com.sloydev.busparser.core.command.DiffCommand;
import com.sloydev.busparser.submodules.sql.SqlDataSource;

import java.nio.file.Paths;

public class LocalDiffTask {

    public static void main(String[] args) {
        DataSource dataSource1 = new SqlDataSource(Paths.get("in/lineas.sql"), Paths.get("in/secciones.sql"));
        DataSource dataSource2 = new SqlDataSource(Paths.get("in/lineas2.sql"), Paths.get("in/secciones2.sql"));
        DiffOutput diffOutput = new JsonFileDiffOutput(new ObjectMapper());

        DiffCommand diffCommand = new DiffCommand(dataSource1, dataSource2, diffOutput);

        diffCommand.run();
    }

}
