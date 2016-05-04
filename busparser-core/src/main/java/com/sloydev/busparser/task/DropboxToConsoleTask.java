package com.sloydev.busparser.task;

import com.sloydev.busparser.console.ConsoleDataOutput;
import com.sloydev.busparser.dropbox.DropboxDataSource;
import com.sloydev.busparser.service.InputOutputService;
import okhttp3.OkHttpClient;

public class DropboxToConsoleTask {

    public static void main(String[] args) {
        DropboxDataSource dropboxDataSource = new DropboxDataSource(new OkHttpClient());
        dropboxDataSource.setLatestDataVersion();
        ConsoleDataOutput consoleOutput = new ConsoleDataOutput();

        InputOutputService inputOutputService = new InputOutputService(dropboxDataSource, consoleOutput);
        inputOutputService.run();
    }
}
