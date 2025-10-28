package com.example.jpa.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "compras")
public class ComprasModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private ProveedoresModel proveedor;

    private LocalDate fechaCompra;

    private Double total;

    private Double descuentoAplicado;

    @Column(length = 20)
    private String numeroFactura;

    @Column(length = 20)
    private String serieFactura;

    public ComprasModel() {
    }

    public ComprasModel(ProveedoresModel proveedor, LocalDate fechaCompra, Double total, Double descuentoAplicado, String numeroFactura, String serieFactura) {
        this.proveedor = proveedor;
        this.fechaCompra = fechaCompra;
        this.total = total;
        this.descuentoAplicado = descuentoAplicado;
        this.numeroFactura = numeroFactura;
        this.serieFactura = serieFactura;
    }
    // Getters
    public Long getId() {
        return id;
    }

    public ProveedoresModel getProveedor() {
        return proveedor;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public Double getTotal() {
        return total;
    }

    public Double getDescuentoAplicado() {
        return descuentoAplicado;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public String getSerieFactura() {
        return serieFactura;
    }

    // Setters
    public void setProveedor(ProveedoresModel proveedor) {
        this.proveedor = proveedor;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public void setDescuentoAplicado(Double descuentoAplicado) {
        this.descuentoAplicado = descuentoAplicado;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public void setSerieFactura(String serieFactura) {
        this.serieFactura = serieFactura;
    }
}