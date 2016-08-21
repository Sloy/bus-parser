package com.sloydev.busparser.core.model.valueobject;

public class TipoLinea {

    private final int id;
    private final String nombre;

    public TipoLinea(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "TipoLinea{" +
          "id=" + id +
          ", nombre='" + nombre + '\'' +
          '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TipoLinea tipoLinea = (TipoLinea) o;

        if (id != tipoLinea.id) return false;
        return nombre.equals(tipoLinea.nombre);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + nombre.hashCode();
        return result;
    }
}
