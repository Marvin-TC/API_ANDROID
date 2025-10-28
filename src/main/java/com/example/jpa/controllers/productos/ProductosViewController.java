package com.example.jpa.controllers.productos;


import com.example.jpa.models.ProductosModel;
import com.example.jpa.repository.ProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/productos")
public class ProductosViewController {

    private final ProductosRepository productosRepository;

    @Autowired
    public ProductosViewController(ProductosRepository productosRepository) {
        this.productosRepository = productosRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("productos", productosRepository.findAll());
        return "productos/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("producto", new ProductosModel());
        return "productos/form";
    }

    @PostMapping("/nuevo")
    public String guardarNuevo(@ModelAttribute("producto") ProductosModel producto) {
        productosRepository.save(producto);
        return "redirect:/productos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        ProductosModel producto = productosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + id));
        model.addAttribute("producto", producto);
        return "productos/form";
    }
    @PostMapping("/editar/{id}")
    public String actualizar(@PathVariable Long id, @ModelAttribute("producto") ProductosModel formProducto) {
        ProductosModel existente = productosRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + id));

        existente.setNombre(formProducto.getNombre());
        existente.setNombreFacturacion(formProducto.getNombreFacturacion());
        existente.setCodigoInterno(formProducto.getCodigoInterno());
        existente.setCodigoBarra(formProducto.getCodigoBarra());
        existente.setUrlImagen(formProducto.getUrlImagen());
        existente.setPrecioUnidad(formProducto.getPrecioUnidad());
        existente.setPrecioMayorista(formProducto.getPrecioMayorista());
        existente.setStock(formProducto.getStock());
        existente.setEstado(formProducto.getEstado());
        productosRepository.save(existente);
        return "redirect:/productos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        productosRepository.deleteById(id);
        return "redirect:/productos";
    }
}