package com.example.jpa.controllers.ventas;

import com.example.jpa.models.DetalleVentaModel;
import com.example.jpa.models.ProductosModel;
import com.example.jpa.models.VentasModel;
import com.example.jpa.repository.ClientesRepository;
import com.example.jpa.repository.DetalleVentaRepository;
import com.example.jpa.repository.ProductosRepository;
import com.example.jpa.repository.VentasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Controller
@RequestMapping("/ventas")
public class VentasViewController {

    private final VentasRepository ventasRepo;
    private final DetalleVentaRepository detalleRepo;
    private final ClientesRepository clientesRepo;
    private final ProductosRepository productosRepo;

    @Autowired
    public VentasViewController(VentasRepository ventasRepo,
                                DetalleVentaRepository detalleRepo,
                                ClientesRepository clientesRepo,
                                ProductosRepository productosRepo) {
        this.ventasRepo = ventasRepo;
        this.detalleRepo = detalleRepo;
        this.clientesRepo = clientesRepo;
        this.productosRepo = productosRepo;
    }

    @GetMapping
    public String listarVentas(Model model) {
        List<VentasModel> ventas = ventasRepo.findAll();
        model.addAttribute("ventas", ventas);
        return "ventas/lista";
    }

    @GetMapping("/nueva")
    public String nuevaVenta(Model model) {
        model.addAttribute("venta", new VentasModel());
        model.addAttribute("clientes", clientesRepo.findAll());
        return "ventas/form";
    }

    @PostMapping("/guardar")
    public String guardarVenta(
            @ModelAttribute VentasModel venta,
            @RequestParam("productoId") List<Long> productoIds,
            @RequestParam("cantidad") List<Integer> cantidades,
            @RequestParam("precio") List<Double> precios
    ) {

        venta.setFechaVenta(LocalDate.now());
        venta.setHoraVenta(LocalTime.now());

        VentasModel savedVenta = ventasRepo.save(venta);
        double totalVenta = 0.0;
        for (int i = 0; i < productoIds.size(); i++) {
            Long productoId = productoIds.get(i);
            Integer cantidad = cantidades.get(i);
            Double precio = precios.get(i);

            ProductosModel producto = productosRepo.findById(productoId)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado ID: " + productoId));

            // Validar stock
            if (cantidad > producto.getStock()) {
                throw new RuntimeException("Stock insuficiente para " + producto.getNombre());
            }
            // Descontar stock
            producto.setStock(producto.getStock() - cantidad);
            productosRepo.save(producto);
            // Crear detalle
            DetalleVentaModel detalle = new DetalleVentaModel();
            detalle.setVenta(savedVenta);
            detalle.setProducto(producto);
            detalle.setCantidad(cantidad);
            detalle.setPrecio(precio);
            detalle.setDescuento(0.0);
            detalle.setSubtotal(precio * cantidad);
            detalleRepo.save(detalle);

            totalVenta += precio * cantidad;
        }
        //Actualizar total de la venta
        savedVenta.setTotal(totalVenta);
        ventasRepo.save(savedVenta);

        return "redirect:/ventas";
    }

    @GetMapping("/detalle/{id}")
    public String detalleVenta(@PathVariable Long id, Model model) {
        VentasModel venta = ventasRepo.findById(id).orElse(null);
        List<DetalleVentaModel> detalles = detalleRepo.findByVentaId(id);

        model.addAttribute("venta", venta);
        model.addAttribute("detalles", detalles);

        return "ventas/detalle"; // templates/ventas/detalle.html
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarVenta(@PathVariable Long id) {
        VentasModel venta = ventasRepo.findById(id).orElse(null);
        if (venta == null) return "redirect:/ventas";

        List<DetalleVentaModel> detalles = detalleRepo.findByVentaId(id);
        for (DetalleVentaModel det : detalles) {
            ProductosModel producto = det.getProducto();
            producto.setStock(producto.getStock() + det.getCantidad());
            productosRepo.save(producto);
        }

        detalleRepo.deleteAll(detalles);
        ventasRepo.delete(venta);
        return "redirect:/ventas";
    }
    @GetMapping("/buscar-productos")
    @ResponseBody
    public List<ProductosModel> buscarProductos(@RequestParam String q) {
        return productosRepo.findByNombreContainingIgnoreCaseOrCodigoInternoContainingIgnoreCase(q, q);
    }
}