package com.sloydev.busparser.submodules.api;

import com.sloydev.busparser.core.model.DataSource;
import com.sloydev.busparser.core.model.Linea;
import com.sloydev.busparser.submodules.api.internal.ApiMapper;
import com.sloydev.busparser.submodules.api.internal.model.LineasList;
import com.sloydev.jsonadapters.JsonAdapter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ApiDataSource implements DataSource {

    private final static String URL_LINEAS = "http://sevibus.sloydev.com/awesomeTussamParser/lineas.php";

    private final OkHttpClient client;
    private final JsonAdapter jsonAdapter;

    public ApiDataSource(OkHttpClient client, JsonAdapter jsonAdapter) {
        this.client = client;
        this.jsonAdapter = jsonAdapter;
    }

    @Override
    public List<Linea> obtainLineas() {
        Request request = new Request.Builder()
          .get()
          .url(URL_LINEAS)
          .build();

        try {
            Response response = client.newCall(request).execute();
            String json = response.body().string();
            LineasList[] lineas = jsonAdapter.fromJson(json, LineasList[].class);

            return lineas[0].linea.stream()
              .map(ApiMapper::mapLinea)
              .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
