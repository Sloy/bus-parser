package com.sloydev.busparser.core.model;

import com.sloydev.busparser.core.model.valueobject.Seccion;
import com.sloydev.busparser.core.model.valueobject.TipoLinea;

import java.util.List;

public class Linea {

    private int id;
    private String numero;
    private String nombre;
    private String color;
    private List<Seccion> trayectos;
    private TipoLinea tipo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Seccion> getTrayectos() {
        return trayectos;
    }

    public void setTrayectos(List<Seccion> trayectos) {
        this.trayectos = trayectos;
    }

    public TipoLinea getTipo() {
        return tipo;
    }

    public void setTipo(TipoLinea tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Linea{" +
          "id=" + id +
          ", numero='" + numero + '\'' +
          ", nombre='" + nombre + '\'' +
          ", color='" + color + '\'' +
          ", trayectos=" + trayectos +
          ", tipo=" + tipo +
          '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Linea linea = (Linea) o;

        if (id != linea.id) return false;
        if (!numero.equals(linea.numero)) return false;
        if (!nombre.equals(linea.nombre)) return false;
        if (!color.equals(linea.color)) return false;
        if (!trayectos.equals(linea.trayectos)) return false;
        return tipo.equals(linea.tipo);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + numero.hashCode();
        result = 31 * result + nombre.hashCode();
        result = 31 * result + color.hashCode();
        result = 31 * result + trayectos.hashCode();
        result = 31 * result + tipo.hashCode();
        return result;
    }
}
