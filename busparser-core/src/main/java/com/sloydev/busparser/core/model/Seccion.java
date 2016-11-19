package com.sloydev.busparser.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;
import com.sloydev.busparser.core.model.valueobject.ParadaId;
import com.sloydev.busparser.core.model.valueobject.SeccionId;

import java.util.List;

@AutoValue
public abstract class Seccion {

    public static Builder builder() {
        return new AutoValue_Seccion.Builder();
    }

    @JsonProperty
    public abstract SeccionId id();

    @JsonProperty
    public abstract String nombreSeccion();

    @JsonProperty
    public abstract String horaInicio();

    @JsonProperty
    public abstract String horaFin();

    @JsonProperty
    public abstract List<ParadaId> paradaIds();

    abstract Builder toBuilder();

    public Seccion withParadas(List<ParadaId> paradas) {
        return toBuilder().paradaIds(paradas).build();
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder id(SeccionId id);

        public abstract Builder nombreSeccion(String nombreSeccion);

        public abstract Builder horaInicio(String horaInicio);

        public abstract Builder horaFin(String horaFin);

        public abstract Builder paradaIds(List<ParadaId> paradaIds);

        public abstract Seccion build();
    }

}
