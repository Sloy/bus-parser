package com.sloydev.busparser.dropbox;

import com.google.gson.Gson;
import com.sloydev.busparser.core.DataSource;
import com.sloydev.busparser.core.model.Linea;
import com.sloydev.busparser.sql.SqlMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.chernobyl.Chernobyl.checkNotNull;

public class DropboxDataSource implements DataSource {

    private static final String URL_LINEAS = "https://dl.dropboxusercontent.com/u/1587994/SeviBus%%20Data/v%d/lineas.sql";
    private static final String URL_SECCIONES = "https://dl.dropboxusercontent.com/u/1587994/SeviBus%%20Data/v%d/secciones.sql";

    private final OkHttpClient okHttpClient;

    private Optional<Integer> dataVersion = Optional.empty();

    public DropboxDataSource(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }


    @Override
    public List<Linea> obtainLineas() {
        Integer version = dataVersion.orElseGet(this::retrieveLatestVersion);
        String urlLineas = String.format(URL_LINEAS, version);
        String urlSecciones = String.format(URL_SECCIONES, version);

        String lineasSql = requestData(urlLineas);
        String seccionesSql = requestData(urlSecciones);

        List<String> lineasInserts = Pattern.compile("\n")
          .splitAsStream(lineasSql)
          .collect(Collectors.toList());

        List<String> seccionesInserts = Pattern.compile("\n")
          .splitAsStream(seccionesSql)
          .collect(Collectors.toList());

        return SqlMapper.mapLineasFromInserts(lineasInserts, seccionesInserts);
    }

    public void setDataVersion(Integer dataVersion) {
        this.dataVersion = Optional.of(dataVersion);
    }

    public void setLatestDataVersion() {
        this.dataVersion = Optional.empty();
    }

    private Integer retrieveLatestVersion() {
        String info = requestData("https://dl.dropboxusercontent.com/u/1587994/SeviBus%20Data/info.json");
        InfoModel infoModel = new Gson().fromJson(info, InfoModel.class);
        return infoModel.data_version;
    }

    private String requestData(String url) {
        try {
            Request request = new Request.Builder()
              .get()
              .url(url).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class InfoModel {
        public int data_version;
    }

}
