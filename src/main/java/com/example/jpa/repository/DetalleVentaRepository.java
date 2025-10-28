package com.example.jpa.repository;

import com.example.jpa.models.DetalleVentaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVentaModel, Long> {
    List<DetalleVentaModel> findByVentaId(Long ventaId);

    @Query("SELECT d.producto.nombre AS nombreProducto, " +
            "SUM(d.cantidad) AS totalCantidad, " +
            "SUM(d.subtotal) AS totalMonto " +
            "FROM DetalleVentaModel d " +
            "WHERE d.venta.fechaVenta BETWEEN :inicio AND :fin " +
            "GROUP BY d.producto.nombre " +
            "ORDER BY totalCantidad DESC")
    List<Object[]> topProductosMasVendidos(
            @Param("inicio") LocalDate inicio,
            @Param("fin") LocalDate fin
    );
}