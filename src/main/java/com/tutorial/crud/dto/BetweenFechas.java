package com.tutorial.crud.dto;

import java.util.Calendar;
import java.sql.Date;

public class BetweenFechas {

    String usuario;
    Date fechaFirst;
    Date fechaSecond;

    public BetweenFechas(){}

    public BetweenFechas(String usuario, Date fechaFirst, Date fechaSecond) {
        this.usuario = usuario;
        this.fechaFirst = fechaFirst;
        this.fechaSecond = fechaSecond;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Date getFechaFirst() {
        return fechaFirst;
    }

    public void setFechaFirst(Date fechaFirst) {
        this.fechaFirst = fechaFirst;
    }

    public Date getFechaSecond() {
        return fechaSecond;
    }

    public void setFechaSecond(Date fechaSecond) {
        this.fechaSecond = fechaSecond;
    }
}
