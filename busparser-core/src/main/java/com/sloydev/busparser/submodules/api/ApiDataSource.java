package com.sloydev.busparser.submodules.api;

import com.sloydev.busparser.core.model.DataSource;
import com.sloydev.busparser.core.model.Linea;
import com.sloydev.busparser.core.model.Parada;
import com.sloydev.busparser.core.model.valueobject.ParadaId;
import com.sloydev.busparser.core.model.valueobject.SeccionId;
import com.sloydev.busparser.submodules.api.internal.ApiMapper;
import com.sloydev.busparser.submodules.api.internal.model.LineasList;
import com.sloydev.busparser.submodules.api.internal.model.ParadaApiModel;
import com.sloydev.busparser.submodules.api.internal.model.ParadasList;
import com.sloydev.jsonadapters.JsonAdapter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ApiDataSource implements DataSource {

    private final static String URL_LINEAS = "http://sevibus.sloydev.com/awesomeTussamParser/lineas.php";
    private final static String URL_PARADAS_FORMAT = "http://sevibus.sloydev.com/awesomeTussamParser/paradas.php?macro=%d&seccion=%d";

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

    @Override
    public List<Parada> obtainParadas() {
        return obtainLineas().parallelStream()
                .flatMap(linea -> linea.trayectos().stream())
                .peek(seccion -> System.out.println("Downloading seccion " + seccion.nombreSeccion())) //TODO debug
                .flatMap(seccion -> requestParadas(seccion.id())
                        .stream()
                        .map(paradaApiModel -> ApiMapper.mapParadaSingleSection(seccion.id(), paradaApiModel))
                )
                .collect(Collectors.groupingBy(Parada::id))
                .entrySet().stream()
                .map(Map.Entry::getValue)
                .map(ApiDataSource::reduceParadaWithMergedSections)
                .collect(Collectors.toList());
    }

    private List<ParadaApiModel> requestParadas(SeccionId seccionId) {
        String url = String.format(URL_PARADAS_FORMAT, seccionId.lineaId().id(), seccionId.numeroSeccion());
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String json = response.body().string();
            ParadasList[] paradas = jsonAdapter.fromJson(json, ParadasList[].class);

            return paradas[0].nodo;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static Parada reduceParadaWithMergedSections(List<Parada> paradasWithSingleSection) {
        return paradasWithSingleSection.stream()
                .reduce(emptyParada(), (paradaAcumulator, paradaSingleSeccion) ->
                        paradaSingleSeccion.withSecciones(Stream.of(paradaAcumulator.secciones(), paradaSingleSeccion.secciones())
                                .flatMap(Collection::stream)
                                .collect(Collectors.toList())));
    }

    private static Parada emptyParada() {
        return Parada.builder()
                .id(ParadaId.create(0))
                .latitud(0d)
                .longitud(0d)
                .descripcion("")
                .secciones(Collections.emptyList())
                .build();

    }
}
