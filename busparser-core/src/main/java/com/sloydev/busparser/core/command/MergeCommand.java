package com.sloydev.busparser.core.command;

import com.sloydev.busparser.core.model.DataOutput;
import com.sloydev.busparser.core.model.DataSource;
import com.sloydev.busparser.core.model.Linea;
import com.sloydev.busparser.core.model.Seccion;
import com.sloydev.busparser.core.model.valueobject.LineaId;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Stream.concat;

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
        Stream<Linea> concat = concat(
                input1.obtainLineas()
                        .stream(),
                input2.obtainLineas()
                        .stream()
        );

        Map<LineaId, List<Linea>> mapa = concat.collect(groupingBy(Linea::id));

        List<Linea> all = mapa
                .entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .map(sameLineList -> mergeSecciones(sameLineList))
                .collect(Collectors.toList());


        output.outputLineas(all);
    }

    private Linea mergeSecciones(List<Linea> sameLineList) {
        Linea lineaWithAllSections = sameLineList.stream()
                .reduce((linea1, linea2) -> linea2.withTrayectos(
                        combine(linea1.trayectos(), linea2.trayectos()))
                ).get();
        // Merge repeated sections
        List<Seccion> sectionsWithAllParadas = lineaWithAllSections.trayectos()
                .stream()
                .collect(groupingBy(Seccion::id))
                .entrySet().stream()
                .map(Map.Entry::getValue)
                .map(sameSectionList -> mergeParadas(sameSectionList))
                .collect(Collectors.toList());

        return lineaWithAllSections.withTrayectos(sectionsWithAllParadas);

    }

    private Seccion mergeParadas(List<Seccion> sameSectionList) {
        Seccion seccionWithAllParadas = sameSectionList.stream()
                .reduce((s1, s2) -> s2.withParadas(
                        combine(s1.paradaIds(), s2.paradaIds()))
                ).map(withRepeatedParadas ->
                        withRepeatedParadas.withParadas(distinct(withRepeatedParadas.paradaIds())))
                .get();

        return seccionWithAllParadas;
    }

    private static <T> List<T> distinct(List<T> list) {
        return list.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    private static <T> List<T> combine(List<T> list1, List<T> list2) {
        return Stream.concat(list1.stream(), list2.stream()).collect(Collectors.toList());
    }

}
