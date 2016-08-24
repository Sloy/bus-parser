package com.sloydev.busparser.submodules.sql;

import com.sloydev.busparser.core.model.DataOutput;
import com.sloydev.busparser.core.model.Linea;
import com.sloydev.busparser.core.model.Parada;
import com.sloydev.busparser.core.model.Seccion;
import com.sloydev.busparser.core.model.valueobject.ParadaId;
import com.sloydev.busparser.core.model.valueobject.SeccionId;
import com.sloydev.busparser.core.model.valueobject.TipoLinea;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.google.common.base.Preconditions.checkArgument;

public class SqlDataOutput implements DataOutput {

    private final String lineasFilePath;
    private final String seccionesFilePath;
    private final String tipoLineasFilePath;
    private final String paradasFilePath;
    private final String relacionesFilePath;

    public SqlDataOutput(String lineasFilePath, String seccionesFilePath, String tipoLineasFilePath, String paradasFilePath, String relacionesFilePath) {
        this.lineasFilePath = lineasFilePath;
        this.seccionesFilePath = seccionesFilePath;
        this.tipoLineasFilePath = tipoLineasFilePath;
        this.paradasFilePath = paradasFilePath;
        this.relacionesFilePath = relacionesFilePath;
    }

    @Override
    public void outputLineas(List<Linea> lineas) {
        writeLineas(lineas);
        writeSecciones(lineas);
        writeTipoLineas(lineas);
        writeRelaciones(lineas);
    }

    @Override
    public void outputParadas(List<Parada> paradas) {
        writeParadas(paradas);
    }

    private void writeInserts(List<String> inserts, String filePath) {
        Path path = Paths.get(filePath);
        try {
            Files.write(path, inserts, Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeLineas(List<Linea> lineas) {
        List<String> lineasInserts = lineas.stream()
                .map(SqlDataOutput::mapLineaToSql)
                .collect(Collectors.toList());

        writeInserts(lineasInserts, lineasFilePath);
    }

    private void writeSecciones(List<Linea> lineas) {
        List<String> seccionesInsert = lineas.stream()
                .flatMap(linea -> linea.trayectos().stream())
                .map(SqlDataOutput::mapSeccionToSql)
                .collect(Collectors.toList());

        writeInserts(seccionesInsert, seccionesFilePath);
    }

    private void writeTipoLineas(List<Linea> lineas) {
        List<String> tipoLineas = lineas.stream()
                .map(Linea::tipo)
                .distinct()
                .map(SqlDataOutput::mapTipoLineaToSql)
                .collect(Collectors.toList());

        writeInserts(tipoLineas, tipoLineasFilePath);
    }

    private void writeParadas(List<Parada> paradas) {
        List<String> paradasInserts = paradas.stream()
                .map(SqlDataOutput::mapParadaToSql)
                .collect(Collectors.toList());
        writeInserts(paradasInserts, paradasFilePath);
    }

    private void writeRelaciones(List<Linea> lineas) {
        List<String> relacionesInserts = lineas.stream()
                .flatMap(linea -> linea.trayectos().stream())
                .map(SqlDataOutput::mapRelacionesToSql)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        writeInserts(relacionesInserts, relacionesFilePath);
    }

    private static String mapLineaToSql(Linea linea) {
        String sqlFormat = "INSERT INTO linea (id, numero, nombre, tipo_id, color) VALUES " +
                "(%d, '%s', '%s', %d, '%s');";
        int id = linea.id().id();
        String numero = linea.numero();
        String nombre = linea.nombre();
        int tipoId = linea.tipo().id();
        String color = linea.color();
        return String.format(sqlFormat, id, numero, nombre, tipoId, color);

    }

    private static String mapSeccionToSql(Seccion seccion) {
        String sqlFormat = "INSERT INTO seccion (id, nombreSeccion, numeroSeccion, horaInicio, horaFin, linea_id) VALUES " +
                "(%d, '%s', %d, '%s', '%s', %d);";
        int id = generateSeccionDbId(seccion.id());
        String nombreSeccion = seccion.nombreSeccion();
        int numeroSeccion = seccion.id().numeroSeccion();
        String horaInicio = seccion.horaInicio();
        String horaFin = seccion.horaFin();
        int lineaId = seccion.id().lineaId().id();

        return String.format(sqlFormat, id, nombreSeccion, numeroSeccion, horaInicio, horaFin, lineaId);
    }

    private static String mapTipoLineaToSql(TipoLinea tipoLinea) {
        String sqlFormat = "INSERT INTO tipolinea (id, nombre) VALUES(%d, '%s');";
        int id = tipoLinea.id();
        String nombre = tipoLinea.nombre();
        return String.format(sqlFormat, id, nombre);
    }

    private static String mapParadaToSql(Parada parada) {
        String sqlFormat = "INSERT INTO parada (numero, descripcion, latitud, longitud) VALUES " +
                "(%d, '%s', %s, %s);";
        int numero = parada.id().numero();
        String descripcion = parada.descripcion();
        String latitud = parada.latitud().toString().replace(",", ".");
        String longitud = parada.longitud().toString().replace(",", ".");
        return String.format(sqlFormat, numero, descripcion, latitud, longitud);

    }

    private static List<String> mapRelacionesToSql(Seccion seccion) {
        final String sqlFormat = "INSERT INTO paradaseccion (id, seccion_id, parada_id) VALUES (%d, %d, %d);";
        List<ParadaId> paradaIds = seccion.paradaIds();
        return IntStream.range(0, paradaIds.size()).boxed()
                .map(i -> {
                    int paradaId = paradaIds.get(i).numero();
                    int relacionId = generateRelacionDbId(seccion.id(), i);
                    int seccionDbId = generateSeccionDbId(seccion.id());
                    return String.format(sqlFormat, relacionId, seccionDbId, paradaId);
                }).collect(Collectors.toList());
    }

    /**
     * Output: ss lll
     */
    private static int generateSeccionDbId(SeccionId seccionId) {
        int seccion = seccionId.numeroSeccion();
        int linea = seccionId.lineaId().id();
        checkArgument(seccion < 100);
        checkArgument(linea < 1000);
        return seccion * 1000 + linea;
    }

    /**
     * Output: ss lll iii
     */
    private static int generateRelacionDbId(SeccionId seccionId, int index) {
        int seccion = seccionId.numeroSeccion();
        int linea = seccionId.lineaId().id();
        checkArgument(seccion < 100);
        checkArgument(linea < 1000000);
        checkArgument(index < 1000);
        return seccion * 1000000 + linea * 1000 + index;
    }
}
