package com.example.jpa.models;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "clientes")
public class ClientesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombres;

    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(length = 500)
    private String direccion;

    @Column(length = 20)
    private String nit;

    @Column(length = 200)
    private String correoElectronico;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    public ClientesModel() {
    }

    public ClientesModel(String nombres, String apellidos, String direccion, String nit, String correoElectronico) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.nit = nit;
        this.correoElectronico = correoElectronico;
    }
    // Getters y Setters

    public Long getId() {
        return id;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getNit() {
        return nit;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }
    //setters

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}