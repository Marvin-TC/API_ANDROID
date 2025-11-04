package com.example.jpa.controllers;

import com.example.jpa.models.ProductosModel;
import com.example.jpa.repository.ProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductosController {

    private final ProductosRepository productosRepository;

    @Autowired
    public ProductosController(ProductosRepository productosRepository) {
        this.productosRepository = productosRepository;
    }
    @GetMapping
    public List<ProductosModel> getAllProductos() {
        return productosRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductoById(@PathVariable Long id) {
        return productosRepository.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("El producto con ID " + id + " no existe"));
    }

    @PostMapping
    public ResponseEntity<?> createProducto(@RequestBody ProductosModel producto) {
        if (producto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Debes enviar un producto válido");
        }
        try {
            ZoneId zonaGT = ZoneId.of("America/Guatemala");
            ZonedDateTime ahoraGT = ZonedDateTime.now(zonaGT);
            producto.setFechaRegistro(ahoraGT.toLocalDateTime());

            ProductosModel nuevo = productosRepository.save(producto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Producto creado correctamente con ID " + nuevo.getId());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al crear el producto: " + ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProducto(@PathVariable Long id, @RequestBody ProductosModel productoDetails) {
        ProductosModel producto = productosRepository.findById(id).orElse(null);
        if (producto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede actualizar el producto con ID " + id + ", no existe");
        }

        try {
            producto.setNombre(productoDetails.getNombre());
            producto.setNombreFacturacion(productoDetails.getNombreFacturacion());
            producto.setCodigoInterno(productoDetails.getCodigoInterno());
            producto.setCodigoBarra(productoDetails.getCodigoBarra());
            producto.setUrlImagen(productoDetails.getUrlImagen());
            producto.setPrecioUnidad(productoDetails.getPrecioUnidad());
            producto.setPrecioMayorista(productoDetails.getPrecioMayorista());

            productosRepository.save(producto);
            return ResponseEntity.ok("Producto actualizado correctamente");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al actualizar el producto: " + ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProducto(@PathVariable Long id) {
        if (!productosRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede eliminar el producto con ID: " + id + ", no existe");
        }

        try {
            productosRepository.deleteById(id);
            return ResponseEntity.ok("Producto eliminado correctamente");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el producto: " + ex.getMessage());
        }
    }
}