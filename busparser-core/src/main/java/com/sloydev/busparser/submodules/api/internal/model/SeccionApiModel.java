package com.sloydev.busparser.submodules.api.internal.model;

public class SeccionApiModel {

    private String nombreSeccion;
    private String numeroSeccion;
    private String horaInicio;
    private String horaFin;

    public String getNombreSeccion() {
        return nombreSeccion;
    }

    public void setNombreSeccion(String nombreSeccion) {
        this.nombreSeccion = nombreSeccion;
    }

    public String getNumeroSeccion() {
        return numeroSeccion;
    }

    public void setNumeroSeccion(String numeroSeccion) {
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
}
