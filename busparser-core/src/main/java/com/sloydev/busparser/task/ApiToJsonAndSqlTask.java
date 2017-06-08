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

    private static final String OUTPUT_FOLDER = "sevibus-data/";

    public static void main(String[] args) {
        JsonAdapter jsonAdapter = Injections.getJsonAdapter();
        ApiDataSource apiDataSource = new ApiDataSource(new OkHttpClient(), jsonAdapter);

        JsonFileDataOutput jsonOutput = new JsonFileDataOutput(
                OUTPUT_FOLDER + "json/lineas.json",
                OUTPUT_FOLDER + "json/paradas.json",
                jsonAdapter);
        SqlDataOutput sqlOutput = new SqlDataOutput(
                OUTPUT_FOLDER + "sql/lineas.sql",
                OUTPUT_FOLDER + "sql/secciones.sql",
                OUTPUT_FOLDER + "sql/tipolineas.sql",
                OUTPUT_FOLDER + "sql/paradas.sql",
                OUTPUT_FOLDER + "sql/relaciones.sql"
        );

        CompositeDataOutput jsonAndSqlOutput = new CompositeDataOutput(jsonOutput, sqlOutput);

        TransformCommand transformCommand = new TransformCommand(apiDataSource, jsonAndSqlOutput);
        transformCommand.run();
    }
}
