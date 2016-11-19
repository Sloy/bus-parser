package com.sloydev.busparser.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;
import com.sloydev.busparser.core.model.valueobject.LineaId;
import com.sloydev.busparser.core.model.valueobject.TipoLinea;

import java.util.List;

@AutoValue
public abstract class Linea {

    @JsonProperty
    public abstract LineaId id();

    @JsonProperty
    public abstract String numero();

    @JsonProperty
    public abstract String nombre();

    @JsonProperty
    public abstract String color();

    @JsonProperty
    public abstract List<Seccion> trayectos();

    @JsonProperty
    public abstract TipoLinea tipo();

    public static Builder builder() {
        return new AutoValue_Linea.Builder();
    }

    abstract Builder toBuilder();

    public Linea withTrayectos(List<Seccion> secciones) {
        return toBuilder().trayectos(secciones).build();
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
