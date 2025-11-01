package com.example.jpa.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "proveedores")
public class ProveedoresModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String nombreEmpresa;

    @Column(nullable = false, length = 500)
    private String direccion;

    @Column(nullable = false, length = 20)
    private String telefono;

    @Column(nullable = false, length = 100)
    private String nombreVendedor;

    @Column(nullable = false, length = 20)
    private String telefonoVendedor;

    @Column(name = "fecha_registro", insertable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    public ProveedoresModel() {
    }

    public ProveedoresModel(String nombreEmpresa, String direccion, String telefono, String nombreVendedor, String telefonoVendedor) {
        this.nombreEmpresa = nombreEmpresa;
        this.direccion = direccion;
        this.telefono = telefono;
        this.nombreVendedor = nombreVendedor;
        this.telefonoVendedor = telefonoVendedor;
    }
    // Getters
    public Long getId() {
        return id;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getNombreVendedor() {
        return nombreVendedor;
    }

    public String getTelefonoVendedor() {
        return telefonoVendedor;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }
    //setters
    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setNombreVendedor(String nombreVendedor) {
        this.nombreVendedor = nombreVendedor;
    }

    public void setTelefonoVendedor(String telefonoVendedor) {
        this.telefonoVendedor = telefonoVendedor;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}