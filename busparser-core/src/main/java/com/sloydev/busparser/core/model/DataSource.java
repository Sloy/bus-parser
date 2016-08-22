package com.sloydev.busparser.core.model;

import java.util.List;

public interface DataSource {

    List<Linea> obtainLineas();

    List<Parada> obtainParadas();

}
