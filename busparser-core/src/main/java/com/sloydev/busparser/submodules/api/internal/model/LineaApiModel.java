package com.sloydev.busparser.submodules.api.internal.model;

public class LineaApiModel {

    private String macro;
    private String label;
    private String nombre;
    private String color;
    private SeccionesList secciones;

    public String getMacro() {
        return macro;
    }

    public void setMacro(String macro) {
        this.macro = macro;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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

    public SeccionesList getSecciones() {
        return secciones;
    }

    public void setSecciones(SeccionesList secciones) {
        this.secciones = secciones;
    }
}
