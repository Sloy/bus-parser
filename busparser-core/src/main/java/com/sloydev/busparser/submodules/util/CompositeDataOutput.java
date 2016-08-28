package com.sloydev.busparser.submodules.util;

import com.sloydev.busparser.core.model.DataOutput;
import com.sloydev.busparser.core.model.Linea;
import com.sloydev.busparser.core.model.Parada;

import java.util.Arrays;
import java.util.List;

public class CompositeDataOutput implements DataOutput{

    private final DataOutput[] outputs;

    public CompositeDataOutput(DataOutput... outputs) {
        this.outputs = outputs;
    }

    @Override
    public void outputLineas(List<Linea> lineas) {
        Arrays.stream(outputs)
                .forEach(dataOutput -> dataOutput.outputLineas(lineas));
    }

    @Override
    public void outputParadas(List<Parada> paradas) {
        Arrays.stream(outputs)
                .forEach(dataOutput -> dataOutput.outputParadas(paradas));
    }
}
