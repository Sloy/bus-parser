package com.sloydev.busparser.core.model;

import com.google.auto.value.AutoValue;
import com.sloydev.busparser.core.model.valueobject.ParadaId;
import com.sloydev.busparser.core.model.valueobject.SeccionId;

import java.util.List;

@AutoValue
public abstract class Parada {

    public abstract ParadaId id();

    public abstract String descripcion();

    public abstract Double latitud();

    public abstract Double longitud();

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
