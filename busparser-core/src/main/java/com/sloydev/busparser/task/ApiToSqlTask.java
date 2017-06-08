package com.sloydev.busparser.task;

import com.sloydev.busparser.Injections;
import com.sloydev.busparser.core.command.TransformCommand;
import com.sloydev.busparser.submodules.api.ApiDataSource;
import com.sloydev.busparser.submodules.sql.SqlDataOutput;
import com.sloydev.jsonadapters.JsonAdapter;
import okhttp3.OkHttpClient;

public class ApiToSqlTask {

    private static final String OUTPUT_FOLDER = "../busparser-data/sql/";

    public static void main(String[] args) {
        JsonAdapter jsonAdapter = Injections.getJsonAdapter();
        ApiDataSource apiDataSource = new ApiDataSource(new OkHttpClient(), jsonAdapter);
        SqlDataOutput sqlOutput = new SqlDataOutput(
                OUTPUT_FOLDER + "lineas.sql",
                OUTPUT_FOLDER + "secciones.sql",
                OUTPUT_FOLDER + "tipolineas.sql",
                OUTPUT_FOLDER + "paradas.sql",
                OUTPUT_FOLDER + "relaciones.sql"
        );

        TransformCommand transformCommand = new TransformCommand(apiDataSource, sqlOutput);
        transformCommand.run();
    }
}
