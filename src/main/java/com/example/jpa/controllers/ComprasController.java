package com.example.jpa.controllers;

import com.example.jpa.models.ComprasModel;
import com.example.jpa.repository.ComprasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compras")
public class ComprasController {

    private final ComprasRepository comprasRepository;

    @Autowired
    public ComprasController(ComprasRepository comprasRepository) {
        this.comprasRepository = comprasRepository;
    }

    @GetMapping
    public List<ComprasModel> getAllCompras() {
        return comprasRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComprasModel> getCompraById(@PathVariable Long id) {
        return comprasRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ComprasModel createCompra(@RequestBody ComprasModel compra) {
        return comprasRepository.save(compra);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComprasModel> updateCompra(@PathVariable Long id, @RequestBody ComprasModel compraDetails) {
        return comprasRepository.findById(id)
                .map(compra -> {
                    compra.setProveedor(compraDetails.getProveedor());
                    compra.setFechaCompra(compraDetails.getFechaCompra());
                    compra.setTotal(compraDetails.getTotal());
                    compra.setDescuentoAplicado(compraDetails.getDescuentoAplicado());
                    compra.setNumeroFactura(compraDetails.getNumeroFactura());
                    compra.setSerieFactura(compraDetails.getSerieFactura());
                    return ResponseEntity.ok(comprasRepository.save(compra));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompra(@PathVariable Long id) {
        if (!comprasRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        comprasRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}