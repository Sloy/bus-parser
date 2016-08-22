package com.sloydev.busparser.core.model.valueobject;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class SeccionId {

    public static SeccionId create(LineaId lineaId, int numeroSeccion) {
        return new AutoValue_SeccionId(lineaId, numeroSeccion);
    }

    public abstract LineaId lineaId();

    public abstract int numeroSeccion();
}
