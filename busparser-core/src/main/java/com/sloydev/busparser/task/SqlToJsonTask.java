package com.sloydev.busparser.task;

import com.sloydev.busparser.Injections;
import com.sloydev.busparser.core.DataSource;
import com.sloydev.busparser.core.DataOutput;
import com.sloydev.busparser.json.JsonDataOutput;
import com.sloydev.busparser.service.InputOutputService;
import com.sloydev.busparser.sql.SqlDataSource;
import com.sloydev.jsonadapters.JsonAdapter;

import java.nio.file.Paths;

public class SqlToJsonTask {

    public static void main(String[] args) {
        JsonAdapter jsonAdapter = Injections.getJsonAdapter();
        DataSource dataSource = new SqlDataSource(Paths.get("in/lineas.sql"), Paths.get("in/secciones.sql"));
        DataOutput dataOutput = new JsonDataOutput("out/sql_lineas.json", jsonAdapter);

        InputOutputService inputOutputService = new InputOutputService(dataSource, dataOutput);
        inputOutputService.run();
    }

}
