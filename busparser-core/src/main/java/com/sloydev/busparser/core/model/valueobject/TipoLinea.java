package com.sloydev.busparser.core.model.valueobject;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class TipoLinea {

    public abstract int id();

    public abstract String nombre();

    public static TipoLinea create() {
        return new AutoValue_TipoLinea(1, "Dummy");
    }

}
