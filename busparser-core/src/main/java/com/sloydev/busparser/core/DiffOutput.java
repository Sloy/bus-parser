package com.sloydev.busparser.core;

import com.sloydev.busparser.core.model.Linea;

import java.util.List;

public interface DiffOutput {

    void outputDiffLineas(List<Linea> left, List<Linea> right);

}
