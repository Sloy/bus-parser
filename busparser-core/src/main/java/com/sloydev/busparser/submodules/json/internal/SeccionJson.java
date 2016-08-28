package com.sloydev.busparser.submodules.json.internal;

import com.sloydev.busparser.core.model.Seccion;
import com.sloydev.busparser.core.model.valueobject.ParadaId;

import java.util.List;
import java.util.stream.Collectors;

public class SeccionJson {

    public int numeroSeccion;
    public String nombreSeccion;
    public String horaInicio;
    public String horaFin;
    public List<Integer> paradas;

    public static SeccionJson fromSeccion(Seccion seccion) {
        SeccionJson seccionJson = new SeccionJson();
        seccionJson.numeroSeccion = seccion.id().numeroSeccion();
        seccionJson.nombreSeccion = seccion.nombreSeccion();
        seccionJson.horaInicio = seccion.horaInicio();
        seccionJson.horaFin = seccion.horaFin();
        seccionJson.paradas = seccion.paradaIds().stream()
                .map(ParadaId::numero)
                .collect(Collectors.toList());
        return seccionJson;
    }
}
