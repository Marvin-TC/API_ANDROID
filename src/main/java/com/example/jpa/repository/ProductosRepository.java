package com.example.jpa.repository;

import com.example.jpa.models.ProductosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductosRepository extends JpaRepository<ProductosModel, Long> {
    List<ProductosModel> findByNombreContainingIgnoreCaseOrCodigoInternoContainingIgnoreCase(String nombre, String codigo);
}