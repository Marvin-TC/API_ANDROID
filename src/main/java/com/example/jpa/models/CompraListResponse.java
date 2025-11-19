package com.example.jpa.models;


import java.time.LocalDate;

public class CompraListResponse {

    private Long id;
    private String nombreProveedor;
    private LocalDate fechaCompra;
    private String numeroFactura;
    private String serieFactura;
    private Double total;
    private Double descuentoAplicado;

    public CompraListResponse(Long id,
                              String nombreProveedor,
                              LocalDate fechaCompra,
                              String numeroFactura,
                              String serieFactura,
                              Double total,
                              Double descuentoAplicado) {
        this.id = id;
        this.nombreProveedor = nombreProveedor;
        this.fechaCompra = fechaCompra;
        this.numeroFactura = numeroFactura;
        this.serieFactura = serieFactura;
        this.total = total;
        this.descuentoAplicado = descuentoAplicado;
    }

    public Long getId() { return id; }
    public String getNombreProveedor() { return nombreProveedor; }
    public LocalDate getFechaCompra() { return fechaCompra; }
    public String getNumeroFactura() { return numeroFactura; }
    public String getSerieFactura() { return serieFactura; }
    public Double getTotal() { return total; }
    public Double getDescuentoAplicado() { return descuentoAplicado; }
}