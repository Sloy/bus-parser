package com.sloydev.busparser.core.model.valueobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class LineaId {

    @JsonProperty
    public abstract int id();

    public static LineaId create(int id) {
        return new AutoValue_LineaId(id);
    }
}
