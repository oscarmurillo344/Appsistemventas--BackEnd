package com.tutorial.crud.dto;

import java.util.Calendar;

public class gastosX {

    String usuario;
    String tipo;
    Calendar inicial;
    Calendar fin;

    public gastosX(){}

    public gastosX(String usuario, String tipo, Calendar inicial, Calendar fin) {
        this.usuario = usuario;
        this.tipo = tipo;
        this.inicial = inicial;
        this.fin = fin;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Calendar getInicial() {
        return inicial;
    }

    public void setInicial(Calendar inicial) {
        this.inicial = inicial;
    }

    public Calendar getFin() {
        return fin;
    }

    public void setFin(Calendar fin) {
        this.fin = fin;
    }
}
