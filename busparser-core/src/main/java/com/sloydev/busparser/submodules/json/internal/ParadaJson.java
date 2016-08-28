package com.sloydev.busparser.submodules.json.internal;

import com.sloydev.busparser.core.model.Parada;

public class ParadaJson {

    public int numero;
    public String descripcion;
    public Double latitud;
    public Double longitud;

    public static ParadaJson fromParada(Parada parada) {
        ParadaJson paradaJson = new ParadaJson();
        paradaJson.numero = parada.id().numero();
        paradaJson.descripcion = parada.descripcion();
        paradaJson.latitud = parada.latitud();
        paradaJson.longitud = parada.longitud();
        paradaJson.longitud = parada.longitud();
        return paradaJson;
    }
}
