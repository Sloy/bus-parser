package com.sloydev.busparser.submodules.dropbox;

import com.google.gson.Gson;
import com.sloydev.busparser.core.model.DataSource;
import com.sloydev.busparser.core.model.Linea;
import com.sloydev.busparser.core.model.Parada;
import com.sloydev.busparser.core.model.Seccion;
import com.sloydev.busparser.core.model.valueobject.LineaId;
import com.sloydev.busparser.core.model.valueobject.ParadaId;
import com.sloydev.busparser.core.model.valueobject.SeccionId;
import com.sloydev.busparser.core.model.valueobject.TipoLinea;
import com.sloydev.busparser.submodules.sql.internal.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DropboxDataSource implements DataSource {

    private static final String URL_LINEAS = "https://dl.dropboxusercontent.com/u/1587994/SeviBus%%20Data/v%d/lineas.sql";
    private static final String URL_SECCIONES = "https://dl.dropboxusercontent.com/u/1587994/SeviBus%%20Data/v%d/secciones.sql";
    private static final String URL_RELACIONES = "https://dl.dropboxusercontent.com/u/1587994/SeviBus%%20Data/v%d/relaciones.sql";
    private static final String URL_PARADAS = "https://dl.dropboxusercontent.com/u/1587994/SeviBus%%20Data/v%d/paradas.sql";

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
        String urlRelaciones = String.format(URL_RELACIONES, version);

        String lineasSql = requestData(urlLineas);
        String seccionesSql = requestData(urlSecciones);
        String relacionesSql = requestData(urlRelaciones);

        List<LineaInsert> lineasInserts = Pattern.compile("\n")
                .splitAsStream(lineasSql)
                .filter((s) -> !s.isEmpty())
                .map(SqlMapper::mapLineaInsert)
                .collect(Collectors.toList());

        List<SeccionInsert> seccionesInserts = Pattern.compile("\n")
                .splitAsStream(seccionesSql)
                .filter((s) -> !s.isEmpty())
                .map(SqlMapper::mapSeccionInsert)
                .collect(Collectors.toList());

        List<RelacionInsert> relacionesInserts = Pattern.compile("\n")
                .splitAsStream(relacionesSql)
                .filter((s) -> !s.isEmpty())
                .map(SqlMapper::mapRelacionInsert)
                .collect(Collectors.toList());


        List<Seccion> secciones = mapSecciones(seccionesInserts, relacionesInserts);
        List<Linea> lineas = mapLineas(lineasInserts, secciones);
        return lineas;
    }

    //TODO want: private List<Linea> mapLineas(List<LineaInsert> lineasInserts, List<SeccionInsert> seccionesInserts) {
    private List<Linea> mapLineas(List<LineaInsert> lineasInserts, List<Seccion> secciones) {
        return lineasInserts.stream()
                .map(lineaInsert -> {
                    LineaId lineaId = LineaId.create(lineaInsert.getId());
                    return Linea.builder()
                            .id(lineaId)
                            .numero(lineaInsert.getNumero())
                            .nombre(lineaInsert.getNombre())
                            .color(lineaInsert.getColor())
                            .tipo(TipoLinea.create()) //TODO
                            .trayectos(secciones.stream()
                                    .filter(seccion -> seccion.id().lineaId().equals(lineaId))
                                    .collect(Collectors.toList()))
                            .build();

                }).collect(Collectors.toList());
    }

    private List<Seccion> mapSecciones(List<SeccionInsert> seccionesInserts, List<RelacionInsert> relacionesInserts) {
        return seccionesInserts.stream()
                .map(seccionInsert -> {
                    LineaId lineaId = LineaId.create(seccionInsert.getLineaId());
                    SeccionId seccionId = SeccionId.create(lineaId, seccionInsert.getNumeroSeccion());
                    return Seccion.builder()
                            .id(seccionId)
                            .nombreSeccion(seccionInsert.getNombreSeccion())
                            .horaInicio(seccionInsert.getHoraInicio())
                            .horaFin(seccionInsert.getHoraFin())
                            .paradaIds(relacionesInserts.stream()
                                    .filter(relacionInsert -> relacionInsert.seccion == seccionInsert.getId())
                                    .map(relacionInsert -> ParadaId.create(relacionInsert.parada))
                                    .collect(Collectors.toList()))
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Parada> obtainParadas() {
        Integer version = dataVersion.orElseGet(this::retrieveLatestVersion);
        String urlSecciones = String.format(URL_SECCIONES, version);
        String urlRelaciones = String.format(URL_RELACIONES, version);
        String urlParadas = String.format(URL_PARADAS, version);

        String seccionesSql = requestData(urlSecciones);
        String relacionesSql = requestData(urlRelaciones);
        String paradasSql = requestData(urlParadas);

        List<SeccionInsert> seccionesInserts = Pattern.compile("\n")
                .splitAsStream(seccionesSql)
                .filter((s) -> !s.isEmpty())
                .map(SqlMapper::mapSeccionInsert)
                .collect(Collectors.toList());

        List<RelacionInsert> relacionesInserts = Pattern.compile("\n")
                .splitAsStream(relacionesSql)
                .filter((s) -> !s.isEmpty())
                .map(SqlMapper::mapRelacionInsert)
                .collect(Collectors.toList());

        List<ParadaInsert> paradasInserts = Pattern.compile("\n")
                .splitAsStream(paradasSql)
                .filter((s) -> !s.isEmpty())
                .map(SqlMapper::mapParadaInsert)
                .collect(Collectors.toList());

        List<Seccion> secciones = mapSecciones(seccionesInserts, relacionesInserts);
        List<Parada> paradas = mapParadas(paradasInserts, secciones);
        return paradas;
    }

    private List<Parada> mapParadas(List<ParadaInsert> paradasInserts, List<Seccion> secciones) {
        return paradasInserts.stream()
                .map(paradaInsert -> {
                    ParadaId paradaId = ParadaId.create(paradaInsert.numero);
                    return Parada.builder()
                            .id(paradaId)
                            .descripcion(paradaInsert.descripcion)
                            .latitud(paradaInsert.latitud)
                            .longitud(paradaInsert.longitud)
                            .secciones(secciones.stream()
                                    .filter(seccion -> seccion.paradaIds().contains(paradaId))
                                    .map(Seccion::id)
                                    .collect(Collectors.toList()))
                            .build();
                })
                .collect(Collectors.toList());
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
