package com.sloydev.busparser.task;

import com.sloydev.busparser.Injections;
import com.sloydev.busparser.core.command.TransformCommand;
import com.sloydev.busparser.submodules.api.ApiDataSource;
import com.sloydev.busparser.submodules.sql.SqlDataOutput;
import com.sloydev.jsonadapters.JsonAdapter;
import okhttp3.OkHttpClient;

public class ApiToSqlTask {

    public static void main(String[] args) {
//        DropboxDataSource dropboxDataSource = new DropboxDataSource(new OkHttpClient());
//        dropboxDataSource.setLatestDataVersion();

        JsonAdapter jsonAdapter = Injections.getJsonAdapter();
        ApiDataSource apiDataSource = new ApiDataSource(new OkHttpClient(), jsonAdapter);
        SqlDataOutput sqlOutput = new SqlDataOutput(
                "out/lineas.sql",
                "out/secciones.sql",
                "out/tipolineas.sql",
                "out/paradas.sql",
                "out/relaciones.sql"
                );

        TransformCommand transformCommand = new TransformCommand(apiDataSource, sqlOutput);
        transformCommand.run();
    }
}
