package com.sloydev.busparser.task;

import com.sloydev.busparser.Injections;
import com.sloydev.busparser.api.ApiDataSource;
import com.sloydev.busparser.json.JsonDataOutput;
import com.sloydev.busparser.service.InputOutputService;
import com.sloydev.jsonadapters.JsonAdapter;
import okhttp3.OkHttpClient;

public class ApiToJsonTask {

    public static void main(String[] args) {
        JsonAdapter jsonAdapter = Injections.getJsonAdapter();
        ApiDataSource apiDataSource = new ApiDataSource(new OkHttpClient(), jsonAdapter);
        JsonDataOutput consoleOutput = new JsonDataOutput("out/api_lineas.json", jsonAdapter);

        InputOutputService inputOutputService = new InputOutputService(apiDataSource, consoleOutput);
        inputOutputService.run();
    }

}
