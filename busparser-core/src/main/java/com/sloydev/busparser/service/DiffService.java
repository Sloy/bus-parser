package com.sloydev.busparser.service;

import com.sloydev.busparser.core.DataSource;
import com.sloydev.busparser.core.DiffOutput;
import com.sloydev.busparser.core.model.Linea;

import java.util.List;

public class DiffService {

    private final DataSource dataSourceLeft;
    private final DataSource dataSourceRight;
    private final DiffOutput diffOutput;

    public DiffService(DataSource dataSourceLeft, DataSource dataSourceRight, DiffOutput diffOutput) {
        this.dataSourceLeft = dataSourceLeft;
        this.dataSourceRight = dataSourceRight;
        this.diffOutput = diffOutput;
    }

    public void run() {
        List<Linea> lineasLeft = dataSourceLeft.obtainLineas();
        List<Linea> lineasRight = dataSourceRight.obtainLineas();

        diffOutput.outputDiffLineas(lineasLeft, lineasRight);
    }
}
