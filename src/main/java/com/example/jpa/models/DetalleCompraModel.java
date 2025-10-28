package com.example.jpa.models;

import jakarta.persistence.*;

@Entity
@Table(name = "detalle_compra")
public class DetalleCompraModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "compras_id")
    private ComprasModel compra;

    @ManyToOne
    @JoinColumn(name = "productos_id")
    private ProductosModel producto;

    private Integer cantidad;

    private Double costoUnitario;

    public DetalleCompraModel() {
    }

    public DetalleCompraModel(ComprasModel compra, ProductosModel producto, Integer cantidad, Double costoUnitario) {
        this.compra = compra;
        this.producto = producto;
        this.cantidad = cantidad;
        this.costoUnitario = costoUnitario;
    }
    // Getters
    public Long getId() {
        return id;
    }

    public ComprasModel getCompra() {
        return compra;
    }

    public ProductosModel getProducto() {
        return producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public Double getCostoUnitario() {
        return costoUnitario;
    }

    // Setters
    public void setCompra(ComprasModel compra) {
        this.compra = compra;
    }

    public void setProducto(ProductosModel producto) {
        this.producto = producto;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public void setCostoUnitario(Double costoUnitario) {
        this.costoUnitario = costoUnitario;
    }
}