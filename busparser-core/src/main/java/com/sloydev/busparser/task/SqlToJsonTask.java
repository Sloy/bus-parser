package com.sloydev.busparser.task;

import com.sloydev.busparser.Injections;
import com.sloydev.busparser.core.model.DataSource;
import com.sloydev.busparser.core.model.DataOutput;
import com.sloydev.busparser.submodules.json.JsonFileDataOutput;
import com.sloydev.busparser.core.command.TransformCommand;
import com.sloydev.busparser.submodules.sql.SqlDataSource;
import com.sloydev.jsonadapters.JsonAdapter;

import java.nio.file.Paths;

public class SqlToJsonTask {

    public static void main(String[] args) {
        JsonAdapter jsonAdapter = Injections.getJsonAdapter();
        DataSource dataSource = new SqlDataSource(Paths.get("in/lineas.sql"), Paths.get("in/secciones.sql"));
        DataOutput dataOutput = new JsonFileDataOutput("out/sql_lineas.json", "out/sql_paradas.json", jsonAdapter);

        TransformCommand transformCommand = new TransformCommand(dataSource, dataOutput);
        transformCommand.run();
    }

}
