package com.sloydev.busparser.core.model;

import com.google.auto.value.AutoValue;
import com.sloydev.busparser.core.model.valueobject.SeccionId;

@AutoValue
public abstract class Seccion {

    public static Builder builder() {
        return new AutoValue_Seccion.Builder();
    }

    public abstract SeccionId id();

    public abstract String nombreSeccion();

    public abstract String horaInicio();

    public abstract String horaFin();

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder id(SeccionId id);

        public abstract Builder nombreSeccion(String nombreSeccion);

        public abstract Builder horaInicio(String horaInicio);

        public abstract Builder horaFin(String horaFin);

        public abstract Seccion build();
    }

}
