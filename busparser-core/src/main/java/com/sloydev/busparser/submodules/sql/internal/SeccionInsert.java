package com.sloydev.busparser.submodules.sql.internal;

public class SeccionInsert {

    private String nombreSeccion;
    private int numeroSeccion;
    private String horaInicio;
    private String horaFin;
    private int lineaId;

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

    public int getLineaId() {
        return lineaId;
    }

    public void setLineaId(int lineaId) {
        this.lineaId = lineaId;
    }
}
