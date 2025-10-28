package com.example.jpa.controllers;

import com.example.jpa.models.DetalleCompraModel;
import com.example.jpa.repository.DetalleCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalle-compra")
public class DetalleCompraController {

    private final DetalleCompraRepository detalleCompraRepository;

    @Autowired
    public DetalleCompraController(DetalleCompraRepository detalleCompraRepository) {
        this.detalleCompraRepository = detalleCompraRepository;
    }

    @GetMapping
    public List<DetalleCompraModel> getAllDetallesCompra() {
        return detalleCompraRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleCompraModel> getDetalleCompraById(@PathVariable Long id) {
        return detalleCompraRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public DetalleCompraModel createDetalleCompra(@RequestBody DetalleCompraModel detalle) {
        return detalleCompraRepository.save(detalle);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDetalleCompra(@PathVariable Long id) {
        if (!detalleCompraRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        detalleCompraRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}