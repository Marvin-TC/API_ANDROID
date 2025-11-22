package com.example.jpa.controllers;

import com.example.jpa.models.ProveedoresModel;
import com.example.jpa.repository.ComprasRepository;
import com.example.jpa.repository.ProveedoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/proveedor")
public class ProveedoresController {

    private final ProveedoresRepository proveedoresRepository;
    private final ComprasRepository comprasRepository;

    @Autowired
    public ProveedoresController(ProveedoresRepository proveedoresRepository,
                                 ComprasRepository comprasRepository) {
        this.proveedoresRepository = proveedoresRepository;
        this.comprasRepository = comprasRepository;
    }

    @GetMapping
    public List<ProveedoresModel> getAllProveedores() {
        return proveedoresRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProveedorById(@PathVariable Long id) {
        return proveedoresRepository.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("El proveedor con ID "+ id+" no existe"));
    }

    @PostMapping
    public ResponseEntity<?> createProveedor(@RequestBody ProveedoresModel proveedor) {
        if (proveedor==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Debes enviar un provedor valido");
        }
        try {
            ZoneId zonaGT = ZoneId.of("America/Guatemala");
            ZonedDateTime ahoraGT = ZonedDateTime.now(zonaGT);
            proveedor.setFechaRegistro(ahoraGT.toLocalDateTime());
            ProveedoresModel nuevo = proveedoresRepository.save(proveedor);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Proveedor creado correctamente con ID "+nuevo.getId());
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al crear el proveedor: "+ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProveedor(@PathVariable Long id, @RequestBody ProveedoresModel proveedorDetails) {
        ProveedoresModel proveedor = proveedoresRepository.findById(id).orElse(null);
        if (proveedor==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede actualizar el proveedor con ID "+id+" no existe");
        }

        try {
            proveedor.setNombreEmpresa(proveedorDetails.getNombreEmpresa());
            proveedor.setDireccion(proveedorDetails.getDireccion());
            proveedor.setTelefono(proveedorDetails.getTelefono());
            proveedor.setNombreVendedor(proveedorDetails.getNombreVendedor());
            proveedor.setTelefonoVendedor(proveedorDetails.getTelefonoVendedor());
            proveedoresRepository.save(proveedor);
            return ResponseEntity.ok("proveedor actualizado correctamente");
        }catch (Exception ex)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al actualizar el proveedor: "+ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProveedor(@PathVariable Long id) {
        if (!proveedoresRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede eliminar el proveedor con ID: "+id+
                            " no existe");
        }

        if (comprasRepository.existsByProveedor_id(id)){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("no se puede eliminar el proveedor con id: "+id+
                            " porque tiene compras emitidas a su nombre");
        }
        try {
            proveedoresRepository.deleteById(id);
            return ResponseEntity.ok("proveedor eliminado correctamente");
        }catch (Exception ex)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el proveedor: "+ex.getMessage());
        }
    }
}