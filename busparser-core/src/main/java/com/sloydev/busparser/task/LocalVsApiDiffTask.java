package com.sloydev.busparser.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sloydev.busparser.Injections;
import com.sloydev.busparser.api.ApiDataSource;
import com.sloydev.busparser.core.DataSource;
import com.sloydev.busparser.service.DiffService;
import com.sloydev.busparser.sql.SqlDataSource;
import com.sloydev.jsonadapters.JsonAdapter;
import okhttp3.OkHttpClient;

import java.nio.file.Paths;

public class LocalVsApiDiffTask {

    public static void main(String[] args) {
        JsonAdapter jsonAdapter = Injections.getJsonAdapter();

        DataSource dataSource1 = new SqlDataSource(Paths.get("in/lineas.sql"), Paths.get("in/secciones.sql"));
        DataSource dataSource2 = new ApiDataSource(new OkHttpClient(), jsonAdapter);

        DiffService diffService = new DiffService(dataSource1, dataSource2, new ObjectMapper());

        diffService.run();
    }

}
