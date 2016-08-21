package com.sloydev.busparser.task;

import com.sloydev.busparser.Injections;
import com.sloydev.busparser.submodules.api.ApiDataSource;
import com.sloydev.busparser.submodules.console.ConsoleDataOutput;
import com.sloydev.busparser.core.command.TransformCommand;
import com.sloydev.jsonadapters.JsonAdapter;
import okhttp3.OkHttpClient;

public class ApiToConsoleTask {

    public static void main(String[] args) {
        JsonAdapter jsonAdapter = Injections.getJsonAdapter();
        ApiDataSource apiDataSource = new ApiDataSource(new OkHttpClient(), jsonAdapter);
        ConsoleDataOutput consoleOutput = new ConsoleDataOutput();

        TransformCommand transformCommand = new TransformCommand(apiDataSource, consoleOutput);
        transformCommand.run();
    }
}
