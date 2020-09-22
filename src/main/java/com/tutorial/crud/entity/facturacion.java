package com.tutorial.crud.entity;

import com.tutorial.crud.security.entity.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

@Entity
public class facturacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int numeroFact;
    @NotNull
    private String usuario;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar registroDate;

    @NotNull
    @JoinTable(
            name = "rel_fact_product",
            joinColumns = @JoinColumn(name = "FK_Fact", nullable = false),
            inverseJoinColumns = @JoinColumn(name="FK_Product", nullable = false)
    )
    @ManyToOne(cascade = {CascadeType.MERGE},
            fetch = FetchType.EAGER)
    private Producto productoId;

    private int cantidad;

    public facturacion(){
    }

    public facturacion(int numeroFact, @NotNull String usuario, @NotNull Calendar registroDate, @NotNull Producto productoId, int cantidad) {
        this.id = id;
        this.numeroFact = numeroFact;
        this.usuario = usuario;
        this.registroDate = registroDate;
        this.productoId = productoId;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumeroFact() {
        return numeroFact;
    }

    public void setNumeroFact(int numeroFact) {
        this.numeroFact = numeroFact;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Calendar getRegistroDate() {
        return registroDate;
    }

    public void setRegistroDate(Calendar registroDate) {
        this.registroDate = registroDate;
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
