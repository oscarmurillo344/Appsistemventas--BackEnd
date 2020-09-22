package com.tutorial.crud.dto;

import com.tutorial.crud.entity.Producto;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;


public class facturacionDto {
    @NotBlank
    private String usuarioId;
    @Min(0)
    private int numeroFact;

    @NotBlank
    private Producto productoId;
    @Min(0)
    private int cantidad;



    public facturacionDto(){
    }

    public facturacionDto(@NotBlank String usuarioId, @Min(0) int numeroFact, @NotBlank Producto productoId, @Min(0) int cantidad) {
        this.usuarioId = usuarioId;
        this.numeroFact = numeroFact;
        this.productoId = productoId;
        this.cantidad = cantidad;
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
}
