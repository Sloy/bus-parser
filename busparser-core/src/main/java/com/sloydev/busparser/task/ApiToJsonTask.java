package com.sloydev.busparser.task;

import com.sloydev.busparser.Injections;
import com.sloydev.busparser.submodules.api.ApiDataSource;
import com.sloydev.busparser.submodules.json.JsonFileDataOutput;
import com.sloydev.busparser.core.command.TransformCommand;
import com.sloydev.jsonadapters.JsonAdapter;
import okhttp3.OkHttpClient;

public class ApiToJsonTask {

    public static void main(String[] args) {
        JsonAdapter jsonAdapter = Injections.getJsonAdapter();
        ApiDataSource apiDataSource = new ApiDataSource(new OkHttpClient(), jsonAdapter);
        JsonFileDataOutput consoleOutput = new JsonFileDataOutput("out/api_lineas.json", jsonAdapter);

        TransformCommand transformCommand = new TransformCommand(apiDataSource, consoleOutput);
        transformCommand.run();
    }

}
