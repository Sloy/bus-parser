package com.sloydev.busparser.service;

import com.sloydev.busparser.core.DataSource;
import com.sloydev.busparser.core.DataOutput;
import com.sloydev.busparser.core.model.Linea;

import java.util.List;

public class InputOutputService {

    private final DataSource dataSource;
    private final DataOutput dataOutput;

    public InputOutputService(DataSource dataSource, DataOutput dataOutput) {
        this.dataSource = dataSource;
        this.dataOutput = dataOutput;
    }

    public void run() {
        List<Linea> lineas = dataSource.obtainLineas();
        dataOutput.outputLineas(lineas);
    }

}
