package com.sloydev.busparser.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sloydev.busparser.Injections;
import com.sloydev.busparser.api.ApiDataSource;
import com.sloydev.busparser.core.DataSource;
import com.sloydev.busparser.dropbox.DropboxDataSource;
import com.sloydev.busparser.service.DiffService;
import com.sloydev.jsonadapters.JsonAdapter;
import okhttp3.OkHttpClient;

public class DropboxVsApiDiff {

    public static void main(String[] args) {
        JsonAdapter jsonAdapter = Injections.getJsonAdapter();

        OkHttpClient okHttpClient = new OkHttpClient();
        DataSource dataSource1 = new DropboxDataSource(okHttpClient);
        DataSource dataSource2 = new ApiDataSource(okHttpClient, jsonAdapter);

        DiffService diffService = new DiffService(dataSource1, dataSource2, new ObjectMapper());

        diffService.run();
    }
}
