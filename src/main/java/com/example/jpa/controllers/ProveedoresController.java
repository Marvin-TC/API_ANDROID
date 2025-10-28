package com.example.jpa.controllers;

import com.example.jpa.models.ProveedoresModel;
import com.example.jpa.repository.ProveedoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedoresController {

    private final ProveedoresRepository proveedoresRepository;

    @Autowired
    public ProveedoresController(ProveedoresRepository proveedoresRepository) {
        this.proveedoresRepository = proveedoresRepository;
    }

    @GetMapping
    public List<ProveedoresModel> getAllProveedores() {
        return proveedoresRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedoresModel> getProveedorById(@PathVariable Long id) {
        return proveedoresRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ProveedoresModel createProveedor(@RequestBody ProveedoresModel proveedor) {
        return proveedoresRepository.save(proveedor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProveedoresModel> updateProveedor(@PathVariable Long id, @RequestBody ProveedoresModel proveedorDetails) {
        return proveedoresRepository.findById(id)
                .map(proveedor -> {
                    proveedor.setNombreEmpresa(proveedorDetails.getNombreEmpresa());
                    proveedor.setDireccion(proveedorDetails.getDireccion());
                    proveedor.setTelefono(proveedorDetails.getTelefono());
                    proveedor.setNombreVendedor(proveedorDetails.getNombreVendedor());
                    proveedor.setTelefonoVendedor(proveedorDetails.getTelefonoVendedor());
                    return ResponseEntity.ok(proveedoresRepository.save(proveedor));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProveedor(@PathVariable Long id) {
        if (!proveedoresRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        proveedoresRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}