package com.sloydev.busparser.task;

import com.sloydev.busparser.submodules.console.ConsoleDataOutput;
import com.sloydev.busparser.submodules.dropbox.DropboxDataSource;
import com.sloydev.busparser.core.command.TransformCommand;
import okhttp3.OkHttpClient;

public class DropboxToConsoleTask {

    public static void main(String[] args) {
        DropboxDataSource dropboxDataSource = new DropboxDataSource(new OkHttpClient());
        dropboxDataSource.setLatestDataVersion();
        ConsoleDataOutput consoleOutput = new ConsoleDataOutput();

        TransformCommand transformCommand = new TransformCommand(dropboxDataSource, consoleOutput);
        transformCommand.run();
    }
}
