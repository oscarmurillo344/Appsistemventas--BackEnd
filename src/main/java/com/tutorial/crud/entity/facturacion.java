package com.tutorial.crud.entity;

import com.tutorial.crud.security.entity.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @Temporal(TemporalType.DATE)
    private Date registroDate;

    @NotNull
    @Temporal(TemporalType.TIME)
    private Date registroTime;

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

    public facturacion(int numeroFact, @NotNull String usuario, @NotNull Date registroDate, @NotNull Date registroTime, @NotNull Producto productoId, int cantidad) {
        this.numeroFact = numeroFact;
        this.usuario = usuario;
        this.registroDate = registroDate;
        this.registroTime = registroTime;
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

    public Date getRegistroDate() {
        return registroDate;
    }

    public void setRegistroDate(Date registroDate) {
        this.registroDate = registroDate;
    }

    public Date getRegistroTime() {
        return registroTime;
    }

    public void setRegistroTime(Date registroTime) {
        this.registroTime = registroTime;
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