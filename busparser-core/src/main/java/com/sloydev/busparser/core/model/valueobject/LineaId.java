package com.sloydev.busparser.core.model.valueobject;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class LineaId {

    public abstract int id();

    public static LineaId create(int id) {
        return new AutoValue_LineaId(id);
    }
}
