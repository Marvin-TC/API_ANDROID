package com.example.jpa.repository;

import com.example.jpa.models.ClientesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientesRepository extends JpaRepository<ClientesModel, Long> {
    boolean existsByNit(String nit);
}