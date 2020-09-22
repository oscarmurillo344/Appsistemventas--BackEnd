package com.tutorial.crud.dto;

import com.tutorial.crud.entity.Producto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Calendar;

public class inventarioDto {

    @NotBlank
    private Calendar fecha;

    @NotBlank
    private Producto productoId;

    @Min(0)
    private int cantidad;

    @Min(0)
    private int cantidadExist;

    public inventarioDto(){}

    public inventarioDto(@NotBlank Calendar fecha, @NotBlank Producto productoId, @Min(0) int cantidad, @Min(0) int cantidadExist) {
        this.fecha = fecha;
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.cantidadExist = cantidadExist;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
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

    public int getCantidadExiste() {
        return cantidadExist;
    }

    public void setCantidadExiste(int cantidadExiste) {
        this.cantidadExist = cantidadExiste;
    }
}
