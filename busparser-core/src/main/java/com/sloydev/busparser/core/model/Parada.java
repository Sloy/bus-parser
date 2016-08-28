package com.sloydev.busparser.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;
import com.sloydev.busparser.core.model.valueobject.ParadaId;
import com.sloydev.busparser.core.model.valueobject.SeccionId;

import java.util.List;

@AutoValue
public abstract class Parada {

    @JsonProperty
    public abstract ParadaId id();

    @JsonProperty
    public abstract String descripcion();

    @JsonProperty
    public abstract Double latitud();

    @JsonProperty
    public abstract Double longitud();

    @JsonProperty
    public abstract List<SeccionId> secciones();

    public static Builder builder() {
        return new AutoValue_Parada.Builder();
    }

    abstract Builder toBuilder();

    public Parada withSecciones(List<SeccionId> secciones) {
        return toBuilder().secciones(secciones).build();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder id(ParadaId id);

        public abstract Builder descripcion(String descripcion);

        public abstract Builder latitud(Double latitud);

        public abstract Builder longitud(Double longitud);

        public abstract Builder secciones(List<SeccionId> secciones);

        public abstract Parada build();
    }
}
