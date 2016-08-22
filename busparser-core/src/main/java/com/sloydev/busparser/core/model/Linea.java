package com.sloydev.busparser.core.model;

import com.google.auto.value.AutoValue;
import com.sloydev.busparser.core.model.valueobject.LineaId;
import com.sloydev.busparser.core.model.valueobject.TipoLinea;

import java.util.List;

@AutoValue
public abstract class Linea {

    public abstract LineaId id();

    public abstract String numero();

    public abstract String nombre();

    public abstract String color();

    public abstract List<Seccion> trayectos();

    public abstract TipoLinea tipo();

    public static Builder builder() {
        return new AutoValue_Linea.Builder();
    }


    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder id(LineaId id);

        public abstract Builder numero(String numero);

        public abstract Builder nombre(String nombre);

        public abstract Builder color(String color);

        public abstract Builder trayectos(List<Seccion> trayectos);

        public abstract Builder tipo(TipoLinea tipo);

        public abstract Linea build();
    }
}
