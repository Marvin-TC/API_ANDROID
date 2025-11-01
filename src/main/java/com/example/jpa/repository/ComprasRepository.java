package com.example.jpa.repository;

import com.example.jpa.models.ComprasModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComprasRepository extends JpaRepository<ComprasModel, Long> {
    boolean existsByProveedor_id(Long id);

}