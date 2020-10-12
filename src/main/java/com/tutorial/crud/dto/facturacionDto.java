package com.tutorial.crud.dto;

import com.tutorial.crud.entity.Producto;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;


public class facturacionDto {
    @NotBlank
    private String usuarioId;
    @Min(0)
    private int numeroFact;

    private Date fecha;

    @NotBlank
    private Producto productoId;

    @Min(0)
    private int cantidad;

    private String extras;


    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public facturacionDto(){
    }

    public facturacionDto(@NotBlank String usuarioId, @Min(0) int numeroFact, @NotBlank Producto productoId, @Min(0) int cantidad,String extras) {
        this.usuarioId = usuarioId;
        this.numeroFact = numeroFact;
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.extras = extras;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getNumeroFact() {
        return numeroFact;
    }

    public void setNumeroFact(int numeroFact) {
        this.numeroFact = numeroFact;
    }

    public Producto getProductoId() {
        return productoId;
    }

    public void setProductoId(Producto productoId) {
        this.productoId = productoId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getExtras() {
        return extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }
}
