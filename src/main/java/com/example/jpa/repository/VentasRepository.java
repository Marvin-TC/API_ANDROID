package com.example.jpa.repository;

import com.example.jpa.models.DetalleVentaModel;
import com.example.jpa.models.VentasModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VentasRepository extends JpaRepository<VentasModel, Long> {
    boolean existsByCliente_Id(Long clienteId);
    @Query("SELECT v.fechaVenta AS fecha, " +
            "SUM(v.total) AS totalVentas, " +
            "SUM(v.descuento) AS totalDescuentos " +
            "FROM VentasModel v " +
            "WHERE v.fechaVenta BETWEEN :inicio AND :fin " +
            "GROUP BY v.fechaVenta " +
            "ORDER BY v.fechaVenta ASC")
    List<Object[]> resumenVentasPorRango(
            @Param("inicio") LocalDate inicio,
            @Param("fin") LocalDate fin
    );
}