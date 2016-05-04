package com.sloydev.busparser.task;

import com.sloydev.busparser.Injections;
import com.sloydev.busparser.core.DiffOutput;
import com.sloydev.busparser.dropbox.DropboxDataSource;
import com.sloydev.busparser.json.WebDiffOutput;
import com.sloydev.busparser.service.DiffService;
import okhttp3.OkHttpClient;

public class DropboxVersionsDiffTask {

    public static void main(String[] args) {
        OkHttpClient okHttpClient = new OkHttpClient();
        DropboxDataSource dataSource1 = new DropboxDataSource(okHttpClient);
        dataSource1.setDataVersion(11);
        DropboxDataSource dataSource2 = new DropboxDataSource(okHttpClient);

        DiffOutput diffOutput = new WebDiffOutput(Injections.getJsonAdapter());

        DiffService diffService = new DiffService(dataSource1, dataSource2, diffOutput);

        diffService.run();
    }
}
