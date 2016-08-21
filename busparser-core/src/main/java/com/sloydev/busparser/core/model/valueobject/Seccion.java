package com.sloydev.busparser.core.model.valueobject;

public class Seccion {

    private String nombreSeccion;
    private int numeroSeccion;
    private String horaInicio;
    private String horaFin;


    public String getNombreSeccion() {
        return nombreSeccion;
    }

    public void setNombreSeccion(String nombreSeccion) {
        this.nombreSeccion = nombreSeccion;
    }

    public int getNumeroSeccion() {
        return numeroSeccion;
    }

    public void setNumeroSeccion(int numeroSeccion) {
        this.numeroSeccion = numeroSeccion;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Seccion seccion = (Seccion) o;

        if (numeroSeccion != seccion.numeroSeccion) return false;
        if (!nombreSeccion.equals(seccion.nombreSeccion)) return false;
        if (!horaInicio.equals(seccion.horaInicio)) return false;
        return horaFin.equals(seccion.horaFin);

    }

    @Override
    public int hashCode() {
        int result = nombreSeccion.hashCode();
        result = 31 * result + numeroSeccion;
        result = 31 * result + horaInicio.hashCode();
        result = 31 * result + horaFin.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Seccion{" +
          "nombreSeccion='" + nombreSeccion + '\'' +
          ", numeroSeccion=" + numeroSeccion +
          ", horaInicio='" + horaInicio + '\'' +
          ", horaFin='" + horaFin + '\'' +
          '}';
    }
}
