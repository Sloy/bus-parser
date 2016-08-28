package com.sloydev.busparser.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sloydev.busparser.core.command.DiffCommand;
import com.sloydev.busparser.core.model.DiffOutput;
import com.sloydev.busparser.submodules.dropbox.DropboxDataSource;
import com.sloydev.busparser.submodules.json.JsonFileDiffOutput;
import okhttp3.OkHttpClient;

public class DropboxVersionsDiffTask {

    public static void main(String[] args) {
        OkHttpClient okHttpClient = new OkHttpClient();
        DropboxDataSource dataSource1 = new DropboxDataSource(okHttpClient);
        dataSource1.setDataVersion(1);
        DropboxDataSource dataSource2 = new DropboxDataSource(okHttpClient);
        dataSource2.setDataVersion(2);

        DiffOutput diffOutput = new JsonFileDiffOutput(new ObjectMapper());
        DiffCommand diffCommand = new DiffCommand(dataSource1, dataSource2, diffOutput);
        diffCommand.run();
    }
}
