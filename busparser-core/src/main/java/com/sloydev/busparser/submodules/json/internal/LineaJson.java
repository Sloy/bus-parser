package com.sloydev.busparser.submodules.json.internal;

import com.sloydev.busparser.core.model.Linea;
import com.sloydev.busparser.core.model.valueobject.TipoLinea;

import java.util.List;
import java.util.stream.Collectors;

public class LineaJson {

    public int id;
    public String numero;
    public String nombre;
    public String color;
    public TipoLinea tipo;
    public List<SeccionJson> trayectos;

    public static LineaJson fromLinea(Linea linea) {
        LineaJson lineaJson = new LineaJson();
        lineaJson.id = linea.id().id();
        lineaJson.numero = linea.numero();
        lineaJson.nombre = linea.nombre();
        lineaJson.color = linea.color();
        lineaJson.tipo = linea.tipo();
        lineaJson.trayectos = linea.trayectos().stream()
                .map(SeccionJson::fromSeccion)
                .collect(Collectors.toList());
        return lineaJson;
    }
}
