package com.example.jpa.controllers.productos;

import com.example.jpa.models.ProductosModel;
import com.example.jpa.repository.ProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ProductosModel> getProductoById(@PathVariable Long id) {
        return productosRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ProductosModel createProducto(@RequestBody ProductosModel producto) {
        return productosRepository.save(producto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductosModel> updateProducto(@PathVariable Long id, @RequestBody ProductosModel productoDetails) {
        return productosRepository.findById(id)
                .map(producto -> {
                    producto.setNombre(productoDetails.getNombre());
                    producto.setNombreFacturacion(productoDetails.getNombreFacturacion());
                    producto.setCodigoInterno(productoDetails.getCodigoInterno());
                    producto.setCodigoBarra(productoDetails.getCodigoBarra());
                    producto.setUrlImagen(productoDetails.getUrlImagen());
                    producto.setPrecioUnidad(productoDetails.getPrecioUnidad());
                    producto.setPrecioMayorista(productoDetails.getPrecioMayorista());
                    return ResponseEntity.ok(productosRepository.save(producto));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        if (!productosRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productosRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}