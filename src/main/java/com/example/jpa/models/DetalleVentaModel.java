package com.example.jpa.models;


import jakarta.persistence.*;

@Entity
@Table(name = "detalle_venta")
public class DetalleVentaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ventas_id")
    private VentasModel venta;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private ProductosModel producto;

    private Integer cantidad;

    private Double precio;

    private Double descuento;

    private Double subtotal;

    public DetalleVentaModel() {
    }

    public DetalleVentaModel(VentasModel venta, ProductosModel producto, Integer cantidad, Double precio, Double descuento, Double subtotal) {
        this.venta = venta;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.descuento = descuento;
        this.subtotal = subtotal;
    }
    // Getters
    public Long getId() {
        return id;
    }

    public VentasModel getVenta() {
        return venta;
    }

    public ProductosModel getProducto() {
        return producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public Double getDescuento() {
        return descuento;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    // Setters
    public void setVenta(VentasModel venta) {
        this.venta = venta;
    }

    public void setProducto(ProductosModel producto) {
        this.producto = producto;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
}