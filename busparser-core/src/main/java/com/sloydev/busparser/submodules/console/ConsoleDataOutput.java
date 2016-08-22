package com.sloydev.busparser.submodules.console;

import com.sloydev.busparser.core.model.DataOutput;
import com.sloydev.busparser.core.model.Linea;
import com.sloydev.busparser.core.model.Parada;

import java.util.List;

public class ConsoleDataOutput implements DataOutput {


    @Override
    public void outputLineas(List<Linea> lineas) {
        lineas.forEach(System.out::println);
    }

    @Override
    public void outputParadas(List<Parada> paradas) {
        paradas.forEach(System.out::println);
    }
}
