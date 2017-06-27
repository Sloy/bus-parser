package com.sloydev.busparser.task;

import com.sloydev.busparser.Injections;
import com.sloydev.busparser.core.command.TransformCommand;
import com.sloydev.busparser.submodules.api.ApiDataSource;
import com.sloydev.busparser.submodules.json.JsonFileDataOutput;
import com.sloydev.busparser.submodules.sql.SqlDataOutput;
import com.sloydev.busparser.submodules.util.CompositeDataOutput;
import com.sloydev.jsonadapters.JsonAdapter;
import okhttp3.OkHttpClient;

public class ApiToJsonAndSqlTask {


    public static void main(String[] args) {
        String outputFolder = args[0];
        JsonAdapter jsonAdapter = Injections.getJsonAdapter();
        ApiDataSource apiDataSource = new ApiDataSource(new OkHttpClient(), jsonAdapter);

        JsonFileDataOutput jsonOutput = new JsonFileDataOutput(
                outputFolder + "json/lineas.json",
                outputFolder + "json/paradas.json",
                jsonAdapter);
        SqlDataOutput sqlOutput = new SqlDataOutput(
                outputFolder + "sql/lineas.sql",
                outputFolder + "sql/secciones.sql",
                outputFolder + "sql/tipolineas.sql",
                outputFolder + "sql/paradas.sql",
                outputFolder + "sql/relaciones.sql"
        );

        CompositeDataOutput jsonAndSqlOutput = new CompositeDataOutput(jsonOutput, sqlOutput);

        TransformCommand transformCommand = new TransformCommand(apiDataSource, jsonAndSqlOutput);
        transformCommand.run();
    }
}
