package com.cibertec.bd;

import java.io.Serializable;

public class Imagen implements Serializable {
   
    private String nombre;
    private int tamano;
    private byte[] datos;
    private String ruta;
    
    public String getRuta() {
        return ruta;
    }
    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getTamano() {
        return tamano;
    }
    public void setTamano(int tamano) {
        this.tamano = tamano;
    }
    public byte[] getDatos() {
        return datos;
    }
    public void setDatos(byte[] datos) {
        this.datos = datos;
    }
   

    
   
}
