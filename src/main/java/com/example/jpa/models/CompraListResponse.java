package com.example.jpa.models;

public class CompraListResponse {

    private Long id;
    private String nombreProveedor;
    private String fechaCompra;
    private String numeroFactura;
    private String serieFactura;
    private double total;
    private double descuentoAplicado;

    public CompraListResponse(Long id, String nombreProveedor, String fechaCompra,
                              String numeroFactura, String serieFactura,
                              double total, double descuentoAplicado) {
        this.id = id;
        this.nombreProveedor = nombreProveedor;
        this.fechaCompra = fechaCompra;
        this.numeroFactura = numeroFactura;
        this.serieFactura = serieFactura;
        this.total = total;
        this.descuentoAplicado = descuentoAplicado;
    }

    // GETTERS
    public Long getId() { return id; }
    public String getNombreProveedor() { return nombreProveedor; }
    public String getFechaCompra() { return fechaCompra; }
    public String getNumeroFactura() { return numeroFactura; }
    public String getSerieFactura() { return serieFactura; }
    public double getTotal() { return total; }
    public double getDescuentoAplicado() { return descuentoAplicado; }
}