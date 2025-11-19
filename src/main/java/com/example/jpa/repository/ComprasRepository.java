package com.example.jpa.repository;

import com.example.jpa.models.CompraListResponse;
import com.example.jpa.models.ComprasModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComprasRepository extends JpaRepository<ComprasModel, Long> {
    boolean existsByProveedor_id(Long id);


    @Query("""
    SELECT new com.example.jpa.models.CompraListResponse(
        c.id,
        c.proveedor.nombreEmpresa,
        c.fechaCompra,
        c.numeroFactura,
        c.serieFactura,
        c.total,
        c.descuentoAplicado
    )
    FROM ComprasModel c
    ORDER BY c.id DESC
    """)
    List<CompraListResponse> listarCompras();
}