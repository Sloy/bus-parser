package com.sloydev.busparser.submodules.api.internal;

import com.sloydev.busparser.core.model.Linea;
import com.sloydev.busparser.core.model.Parada;
import com.sloydev.busparser.core.model.valueobject.LineaId;
import com.sloydev.busparser.core.model.Seccion;
import com.sloydev.busparser.core.model.valueobject.ParadaId;
import com.sloydev.busparser.core.model.valueobject.SeccionId;
import com.sloydev.busparser.core.model.valueobject.TipoLinea;
import com.sloydev.busparser.submodules.api.internal.model.LineaApiModel;
import com.sloydev.busparser.submodules.api.internal.model.ParadaApiModel;
import com.sloydev.busparser.submodules.api.internal.model.SeccionApiModel;

import java.util.Collections;
import java.util.stream.Collectors;

public class ApiMapper {

    public static Linea mapLinea(LineaApiModel in) {
        LineaId lineaId = LineaId.create(Integer.valueOf(in.getMacro()));
        return Linea.builder()
                .id(lineaId)
                .numero(in.getLabel())
                .nombre(in.getNombre())
                .color(in.getColor())
                .trayectos(in.getSecciones().seccion.stream()
                        .map((seccionApi) -> mapSeccion(lineaId, seccionApi))
                        .collect(Collectors.toList())
                )
                .tipo(TipoLinea.create())//TODO
                .build();
    }

    private static Seccion mapSeccion(LineaId lineaId, SeccionApiModel in) {
        return Seccion.builder()
                .id(SeccionId.create(lineaId, Integer.valueOf(in.getNumeroSeccion())))
                .nombreSeccion(in.getNombreSeccion())
                .horaInicio(in.getHoraInicio())
                .horaFin(in.getHoraFin())
                .build();
    }

    public static Parada mapParadaSingleSection(SeccionId seccion, ParadaApiModel in) {
        return Parada.builder()
                .id(ParadaId.create(in.codigoNodo))
                .descripcion(in.descripcion)
                .latitud(in.posicion.latitud)
                .longitud(in.posicion.longitud)
                .secciones(Collections.singletonList(seccion))
                .build();
    }

}
