package com.example.jpa.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "productos")
public class ProductosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String nombre;

    @Column(length = 50)
    private String nombreFacturacion;

    @Column(nullable = false, length = 10, unique = true)
    private String codigoInterno;

    @Column(length = 30, unique = true)
    private String codigoBarra;

    @Column(length = 1000)
    private String urlImagen;

    private Double precioUnidad;
    private Double precioMayorista;
    private Integer stock;

    @Column(nullable = false)
    private Boolean estado = true;

    @Column(name = "fecha_registro", insertable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    public ProductosModel() {
    }

    public ProductosModel(String nombre, String nombreFacturacion, String codigoInterno, String codigoBarra, String urlImagen, Double precioUnidad, Double precioMayorista, Integer stock, Boolean estado, LocalDateTime fechaRegistro) {
        this.nombre = nombre;
        this.nombreFacturacion = nombreFacturacion;
        this.codigoInterno = codigoInterno;
        this.codigoBarra = codigoBarra;
        this.urlImagen = urlImagen;
        this.precioUnidad = precioUnidad;
        this.precioMayorista = precioMayorista;
        this.stock = stock;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombreFacturacion() {
        return nombreFacturacion;
    }

    public String getCodigoInterno() {
        return codigoInterno;
    }

    public String getCodigoBarra() {
        return codigoBarra;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public Double getPrecioUnidad() {
        return precioUnidad;
    }

    public Double getPrecioMayorista() {
        return precioMayorista;
    }

    public Integer getStock() {
        return stock;
    }

    public Boolean getEstado() {
        return estado;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNombreFacturacion(String nombreFacturacion) {
        this.nombreFacturacion = nombreFacturacion;
    }

    public void setCodigoInterno(String codigoInterno) {
        this.codigoInterno = codigoInterno;
    }

    public void setCodigoBarra(String codigoBarra) {
        this.codigoBarra = codigoBarra;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public void setPrecioUnidad(Double precioUnidad) {
        this.precioUnidad = precioUnidad;
    }

    public void setPrecioMayorista(Double precioMayorista) {
        this.precioMayorista = precioMayorista;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}