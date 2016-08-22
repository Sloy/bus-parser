package com.sloydev.busparser.submodules.console;

import com.sloydev.busparser.core.model.DataOutput;
import com.sloydev.busparser.core.model.Linea;

import java.util.List;

public class ConsoleDataOutput implements DataOutput {


    @Override
    public void outputLineas(List<Linea> lineas) {
        lineas.stream()
          .forEach(System.out::println);
    }
}