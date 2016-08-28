package com.sloydev.busparser.core.model.valueobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class SeccionId {

    public static SeccionId create(LineaId lineaId, int numeroSeccion) {
        return new AutoValue_SeccionId(lineaId, numeroSeccion);
    }

    @JsonProperty
    public abstract LineaId lineaId();

    @JsonProperty
    public abstract int numeroSeccion();
}
