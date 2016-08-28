package com.sloydev.busparser.core.model.valueobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ParadaId {
    @JsonProperty
    public abstract int numero();

    public static ParadaId create(int numero) {
        return new AutoValue_ParadaId(numero);
    }


}
