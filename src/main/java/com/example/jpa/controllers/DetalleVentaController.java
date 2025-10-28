package com.example.jpa.controllers;

import com.example.jpa.models.DetalleVentaModel;
import com.example.jpa.repository.DetalleVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalle-venta")
public class DetalleVentaController {

    private final DetalleVentaRepository detalleVentaRepository;

    @Autowired
    public DetalleVentaController(DetalleVentaRepository detalleVentaRepository) {
        this.detalleVentaRepository = detalleVentaRepository;
    }

    @GetMapping
    public List<DetalleVentaModel> getAllDetallesVenta() {
        return detalleVentaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleVentaModel> getDetalleVentaById(@PathVariable Long id) {
        return detalleVentaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public DetalleVentaModel createDetalleVenta(@RequestBody DetalleVentaModel detalle) {
        return detalleVentaRepository.save(detalle);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDetalleVenta(@PathVariable Long id) {
        if (!detalleVentaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        detalleVentaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}