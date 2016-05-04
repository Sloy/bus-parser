package com.sloydev.busparser.sql;

public class LineaInsert {

    private int id;
    private String numero;
    private String nombre;
    private int tipoLinea;
    private String color;

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

    public int getTipoLinea() {
        return tipoLinea;
    }

    public void setTipoLinea(int tipoLinea) {
        this.tipoLinea = tipoLinea;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
