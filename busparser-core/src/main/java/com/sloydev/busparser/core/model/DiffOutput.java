package com.sloydev.busparser.core.model;

import java.util.List;

public interface DiffOutput {

    void outputDiffLineas(List<Linea> left, List<Linea> right);

    void outputDiffParadas(List<Parada> left, List<Parada> right);

}
