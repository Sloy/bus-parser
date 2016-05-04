package com.sloydev.busparser.core.model;

public class Parada {

    private final Integer numero;
    private final String descripcion;
    private final Double latitud;
    private final Double longitud;

    public Parada(Double longitud, Double latitud, String descripcion, Integer numero) {
        this.longitud = longitud;
        this.latitud = latitud;
        this.descripcion = descripcion;
        this.numero = numero;
    }

    public Integer getNumero() {
        return numero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Double getLatitud() {
        return latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    @Override
    public String toString() {
        return "Parada{" +
          "numero=" + numero +
          ", descripcion='" + descripcion + '\'' +
          ", latitud=" + latitud +
          ", longitud=" + longitud +
          '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Parada parada = (Parada) o;

        if (!numero.equals(parada.numero)) return false;
        if (!descripcion.equals(parada.descripcion)) return false;
        if (!latitud.equals(parada.latitud)) return false;
        return longitud.equals(parada.longitud);

    }

    @Override
    public int hashCode() {
        int result = numero.hashCode();
        result = 31 * result + descripcion.hashCode();
        result = 31 * result + latitud.hashCode();
        result = 31 * result + longitud.hashCode();
        return result;
    }
}
