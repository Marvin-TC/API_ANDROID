package com.example.jpa.models;


import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "ventas")
public class VentasModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClientesModel cliente;

    private LocalDate fechaVenta;

    private LocalTime horaVenta;

    @Column(length = 200)
    private String vendedor;

    private Double total;

    private Double descuento;

    public VentasModel() {
    }

    public VentasModel(ClientesModel cliente, LocalDate fechaVenta, LocalTime horaVenta, String vendedor, Double total, Double descuento) {
        this.cliente = cliente;

        this.fechaVenta = fechaVenta;
        this.horaVenta = horaVenta;
        this.vendedor = vendedor;
        this.total = total;
        this.descuento = descuento;
    }
    // Getters
    public Long getId() {
        return id;
    }

    public ClientesModel getCliente() {
        return cliente;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public LocalTime getHoraVenta() {
        return horaVenta;
    }

    public String getVendedor() {
        return vendedor;
    }

    public Double getTotal() {
        return total;
    }

    public Double getDescuento() {
        return descuento;
    }

    // Setters
    public void setCliente(ClientesModel cliente) {
        this.cliente = cliente;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public void setHoraVenta(LocalTime horaVenta) {
        this.horaVenta = horaVenta;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }
}
