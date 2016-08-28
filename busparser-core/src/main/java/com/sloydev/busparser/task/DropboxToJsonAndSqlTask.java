package com.sloydev.busparser.task;

import com.sloydev.busparser.Injections;
import com.sloydev.busparser.core.command.TransformCommand;
import com.sloydev.busparser.submodules.api.ApiDataSource;
import com.sloydev.busparser.submodules.dropbox.DropboxDataSource;
import com.sloydev.busparser.submodules.json.JsonFileDataOutput;
import com.sloydev.busparser.submodules.sql.SqlDataOutput;
import com.sloydev.busparser.submodules.util.CompositeDataOutput;
import com.sloydev.jsonadapters.JsonAdapter;
import okhttp3.OkHttpClient;

public class DropboxToJsonAndSqlTask {

    public static void main(String[] args) {
        JsonAdapter jsonAdapter = Injections.getJsonAdapter();
        ApiDataSource apiDataSource = new ApiDataSource(new OkHttpClient(), jsonAdapter);

        DropboxDataSource dropboxDataSource = new DropboxDataSource(new OkHttpClient());
        dropboxDataSource.setDataVersion(2);
        JsonFileDataOutput jsonOutput = new JsonFileDataOutput(
                "out/json/lineas.json",
                "out/json/paradas.json",
                jsonAdapter);
        SqlDataOutput sqlOutput = new SqlDataOutput(
                "out/sql/lineas.sql",
                "out/sql/secciones.sql",
                "out/sql/tipolineas.sql",
                "out/sql/paradas.sql",
                "out/sql/relaciones.sql"
        );

        CompositeDataOutput jsonAndSqlOutput = new CompositeDataOutput(jsonOutput, sqlOutput);

        TransformCommand transformCommand = new TransformCommand(dropboxDataSource, jsonAndSqlOutput);
        transformCommand.run();
    }
}
