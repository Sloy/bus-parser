package com.sloydev.busparser.task;

import com.sloydev.busparser.Injections;
import com.sloydev.busparser.core.model.DiffOutput;
import com.sloydev.busparser.submodules.dropbox.DropboxDataSource;
import com.sloydev.busparser.submodules.json.JsonWebDiffOutput;
import com.sloydev.busparser.core.command.DiffCommand;
import okhttp3.OkHttpClient;

public class DropboxVersionsDiffTask {

    public static void main(String[] args) {
        OkHttpClient okHttpClient = new OkHttpClient();
        DropboxDataSource dataSource1 = new DropboxDataSource(okHttpClient);
        dataSource1.setDataVersion(11);
        DropboxDataSource dataSource2 = new DropboxDataSource(okHttpClient);

        DiffOutput diffOutput = new JsonWebDiffOutput(Injections.getJsonAdapter());

        DiffCommand diffCommand = new DiffCommand(dataSource1, dataSource2, diffOutput);

        diffCommand.run();
    }
}
