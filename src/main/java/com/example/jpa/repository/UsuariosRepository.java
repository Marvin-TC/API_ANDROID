package com.example.jpa.repository;

import com.example.jpa.models.UsuariosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuariosRepository extends JpaRepository<UsuariosModel,Long> {

    boolean existsByUserName(String userName);
    Optional<UsuariosModel> findByUserName(String userName);


}
