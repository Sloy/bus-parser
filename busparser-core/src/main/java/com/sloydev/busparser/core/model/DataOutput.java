package com.sloydev.busparser.core.model;

import java.util.List;

public interface DataOutput {

    void outputLineas(List<Linea> lineas);

    void outputParadas(List<Parada> paradas);
}
