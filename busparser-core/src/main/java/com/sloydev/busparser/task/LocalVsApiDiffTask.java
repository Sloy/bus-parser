package com.sloydev.busparser.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sloydev.busparser.Injections;
import com.sloydev.busparser.submodules.api.ApiDataSource;
import com.sloydev.busparser.core.model.DataSource;
import com.sloydev.busparser.core.model.DiffOutput;
import com.sloydev.busparser.submodules.json.JsonFileDiffOutput;
import com.sloydev.busparser.core.command.DiffCommand;
import com.sloydev.busparser.submodules.sql.SqlDataSource;
import com.sloydev.jsonadapters.JsonAdapter;
import okhttp3.OkHttpClient;

import java.nio.file.Paths;

public class LocalVsApiDiffTask {

    public static void main(String[] args) {
        JsonAdapter jsonAdapter = Injections.getJsonAdapter();

        DataSource dataSource1 = new SqlDataSource(Paths.get("in/lineas.sql"), Paths.get("in/secciones.sql"));
        DataSource dataSource2 = new ApiDataSource(new OkHttpClient(), jsonAdapter);
        DiffOutput diffOutput = new JsonFileDiffOutput(new ObjectMapper());

        DiffCommand diffCommand = new DiffCommand(dataSource1, dataSource2, diffOutput);

        diffCommand.run();
    }

}
