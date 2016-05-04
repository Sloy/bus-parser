package com.sloydev.busparser.core;

import com.sloydev.busparser.core.model.Linea;

import java.util.List;

public interface DataOutput {

    void outputLineas(List<Linea> lineas);
}
