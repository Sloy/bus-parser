package com.sloydev.busparser.core.command;

import com.sloydev.busparser.core.model.DataOutput;
import com.sloydev.busparser.core.model.DataSource;
import com.sloydev.busparser.core.model.Linea;
import com.sloydev.busparser.core.model.Seccion;

import java.util.List;
import java.util.stream.Collectors;

import static com.sloydev.busparser.util.StreamUtils.*;

public class MergeCommand {

    private final DataSource input1;
    private final DataSource input2;
    private final DataOutput output;

    public MergeCommand(DataSource input1, DataSource input2, DataOutput output) {
        this.input1 = input1;
        this.input2 = input2;
        this.output = output;
    }

    public void run() {
        List<Linea> allLines = concat(input1.obtainLineas(), input2.obtainLineas())
                .collect(streamGroupingBy(Linea::id))
                .map(this::mergeSectionsInRepeatedLines)
                .collect(Collectors.toList());

        output.outputLineas(allLines);
    }

    private Linea mergeSectionsInRepeatedLines(List<Linea> sameLineList) {
        Linea lineaWithAllSections = sameLineList.stream()
                .reduce((linea1, linea2) -> linea2.withTrayectos(
                        combine(linea1.trayectos(), linea2.trayectos()))
                ).get();
        // Merge repeated sections
        List<Seccion> sectionsWithAllParadas = lineaWithAllSections.trayectos()
                .stream()
                .collect(streamGroupingBy(Seccion::id))
                .map(this::mergeParadasInRepeatedSections)
                .collect(Collectors.toList());

        return lineaWithAllSections.withTrayectos(sectionsWithAllParadas);

    }

    private Seccion mergeParadasInRepeatedSections(List<Seccion> sameSectionList) {
        return sameSectionList.stream()
                .reduce((s1, s2) -> s2.withParadas(
                        combine(s1.paradaIds(), s2.paradaIds()))
                ).map(withRepeatedParadas ->
                        withRepeatedParadas.withParadas(distinct(withRepeatedParadas.paradaIds())))
                .get();
    }

}
