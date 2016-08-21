package com.sloydev.busparser.submodules.api.internal;

import com.sloydev.busparser.core.model.Linea;
import com.sloydev.busparser.core.model.valueobject.Seccion;
import com.sloydev.busparser.submodules.api.internal.model.LineaApiModel;
import com.sloydev.busparser.submodules.api.internal.model.SeccionApiModel;

import java.util.stream.Collectors;

public class ApiMapper {

    public static Linea mapLinea(LineaApiModel in) {
        Linea out = new Linea();
        out.setId(Integer.valueOf(in.getMacro()));
        out.setNumero(in.getLabel());
        out.setNombre(in.getNombre());
        out.setColor(in.getColor());
        out.setTrayectos(in.getSecciones().seccion.stream()
          .map(ApiMapper::mapSeccion)
          .collect(Collectors.toList())
        );
        out.setTipo(null);//TODO
        return out;
    }

    private static Seccion mapSeccion(SeccionApiModel in) {
        Seccion out = new Seccion();
        out.setHoraInicio(in.getHoraInicio());
        out.setHoraFin(in.getHoraFin());
        out.setNombreSeccion(in.getNombreSeccion());
        out.setNumeroSeccion(Integer.valueOf(in.getNumeroSeccion()));
        return out;
    }
}
