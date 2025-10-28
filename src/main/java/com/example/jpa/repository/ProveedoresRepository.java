package com.example.jpa.repository;

import com.example.jpa.models.ProveedoresModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedoresRepository extends JpaRepository<ProveedoresModel, Long> {
}