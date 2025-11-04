package com.example.jpa.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table (name = "usuarios")
public class UsuariosModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombres;

    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(name = "user_name", nullable = false, length = 100)
    private String userName;

    @Column(name = "password_hash", nullable = false)
    private String PasswordHash;

    @Column(name = "salt", nullable = false)
    private String salt;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    public UsuariosModel() {
    }

    public UsuariosModel(String nombres, String apellidos, String userName, String passwordHash, String salt, LocalDateTime fechaRegistro) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.userName = userName;
        PasswordHash = passwordHash;
        this.salt = salt;
        this.fechaRegistro = fechaRegistro;
    }
    //getters
    public Long getId() {
        return id;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getUserName() {
        return userName;
    }

    public String getPasswordHash() {
        return PasswordHash;
    }

    public String getSalt() {
        return salt;
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

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPasswordHash(String passwordHash) {
        PasswordHash = passwordHash;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
