package com.sloydev.busparser.util;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

public class StreamUtils {
    public static <T> List<T> distinct(List<T> list) {
        return list.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    public static <T> Stream<T> concat(List<T> list1, List<T> list2) {
        return Stream.concat(list1.stream(), list2.stream());
    }

    public static <T> List<T> combine(List<T> list1, List<T> list2) {
        return concat(list1, list2).collect(Collectors.toList());
    }

    public static <T, K> Collector<T, ?, Stream<List<T>>> streamGroupingBy(Function<? super T, ? extends K> classifier) {
        return Collectors.collectingAndThen(
                groupingBy(classifier),
                listMap -> listMap.entrySet()
                        .stream()
                        .map(Map.Entry::getValue)
        );
    }
}
