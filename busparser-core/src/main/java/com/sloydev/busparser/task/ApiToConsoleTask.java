package com.sloydev.busparser.task;

import com.sloydev.busparser.Injections;
import com.sloydev.busparser.api.ApiDataSource;
import com.sloydev.busparser.console.ConsoleDataOutput;
import com.sloydev.busparser.service.InputOutputService;
import com.sloydev.jsonadapters.JsonAdapter;
import okhttp3.OkHttpClient;

public class ApiToConsoleTask {

    public static void main(String[] args) {
        JsonAdapter jsonAdapter = Injections.getJsonAdapter();
        ApiDataSource apiDataSource = new ApiDataSource(new OkHttpClient(), jsonAdapter);
        ConsoleDataOutput consoleOutput = new ConsoleDataOutput();

        InputOutputService inputOutputService = new InputOutputService(apiDataSource, consoleOutput);
        inputOutputService.run();
    }
}
