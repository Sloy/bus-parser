package com.sloydev.busparser.core.model.valueobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class TipoLinea {

    @JsonProperty
    public abstract int id();

    @JsonProperty
    public abstract String nombre();

    public static TipoLinea create() {
        return new AutoValue_TipoLinea(1, "Todas");
    }

}
