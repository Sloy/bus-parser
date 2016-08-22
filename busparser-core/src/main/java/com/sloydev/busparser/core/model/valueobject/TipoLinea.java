package com.sloydev.busparser.core.model.valueobject;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class TipoLinea {

    public static TipoLinea create() {
        return new AutoValue_TipoLinea();
    }
}
