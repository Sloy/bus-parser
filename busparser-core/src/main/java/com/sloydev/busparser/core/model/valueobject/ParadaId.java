package com.sloydev.busparser.core.model.valueobject;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ParadaId {
    public abstract int numero();

    public static ParadaId create(int numero) {
        return new AutoValue_ParadaId(numero);
    }


}
