package com.sloydev.busparser.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sloydev.busparser.Injections;
import com.sloydev.busparser.submodules.api.ApiDataSource;
import com.sloydev.busparser.core.model.DataSource;
import com.sloydev.busparser.core.model.DiffOutput;
import com.sloydev.busparser.submodules.dropbox.DropboxDataSource;
import com.sloydev.busparser.submodules.json.JsonFileDiffOutput;
import com.sloydev.busparser.core.command.DiffCommand;
import com.sloydev.jsonadapters.JsonAdapter;
import okhttp3.OkHttpClient;

public class DropboxVsApiDiffTask {

    public static void main(String[] args) {
        JsonAdapter jsonAdapter = Injections.getJsonAdapter();

        OkHttpClient okHttpClient = new OkHttpClient();
        DataSource dataSource1 = new DropboxDataSource(okHttpClient);
        DataSource dataSource2 = new ApiDataSource(okHttpClient, jsonAdapter);
        DiffOutput diffOutput = new JsonFileDiffOutput(new ObjectMapper());

        DiffCommand diffCommand = new DiffCommand(dataSource1, dataSource2, diffOutput);

        diffCommand.run();
    }
}
