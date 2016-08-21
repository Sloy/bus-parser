package com.sloydev.busparser.core.command;

import com.sloydev.busparser.core.model.DataSource;
import com.sloydev.busparser.core.model.DataOutput;
import com.sloydev.busparser.core.model.Linea;

import java.util.List;

public class TransformCommand {

    private final DataSource dataSource;
    private final DataOutput dataOutput;

    public TransformCommand(DataSource dataSource, DataOutput dataOutput) {
        this.dataSource = dataSource;
        this.dataOutput = dataOutput;
    }

    public void run() {
        List<Linea> lineas = dataSource.obtainLineas();
        dataOutput.outputLineas(lineas);
    }

}
