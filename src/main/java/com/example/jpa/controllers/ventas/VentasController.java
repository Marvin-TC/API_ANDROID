package com.example.jpa.controllers.ventas;

import com.example.jpa.models.VentasModel;
import com.example.jpa.repository.DetalleVentaRepository;
import com.example.jpa.repository.VentasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ventas")
public class VentasController {

    private final VentasRepository ventasRepository;
    private final DetalleVentaRepository detalleVentaRepository;

    @Autowired
    public VentasController(VentasRepository ventasRepository,
                            DetalleVentaRepository detalleVentaRepository) {
        this.ventasRepository = ventasRepository;
        this.detalleVentaRepository = detalleVentaRepository;
    }

    @GetMapping
    public List<VentasModel> getAllVentas() {
        return ventasRepository.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<VentasModel> getVentaById(@PathVariable Long id) {
        return ventasRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public VentasModel createVenta(@RequestBody VentasModel venta) {
        return ventasRepository.save(venta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VentasModel> updateVenta(@PathVariable Long id, @RequestBody VentasModel ventaDetails) {
        return ventasRepository.findById(id)
                .map(venta -> {
                    venta.setCliente(ventaDetails.getCliente());
                    venta.setFechaVenta(ventaDetails.getFechaVenta());
                    venta.setHoraVenta(ventaDetails.getHoraVenta());
                    venta.setVendedor(ventaDetails.getVendedor());
                    venta.setTotal(ventaDetails.getTotal());
                    venta.setDescuento(ventaDetails.getDescuento());
                    return ResponseEntity.ok(ventasRepository.save(venta));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenta(@PathVariable Long id) {
        if (!ventasRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        ventasRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/resumen-ventas")
    public List<Map<String, Object>> resumenVentas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        List<Object[]> resultados = ventasRepository.resumenVentasPorRango(inicio, fin);
        List<Map<String, Object>> response = new ArrayList<>();
        for (Object[] fila : resultados) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("fecha", fila[0]);
            row.put("total_ventas", fila[1]);
            row.put("total_descuentos", fila[2]);
            response.add(row);
        }
        return response;
    }

    @GetMapping("/top-productos")
    public List<Map<String, Object>> topProductos(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin,
            @RequestParam(defaultValue = "5") int limite) {

        List<Object[]> resultados = detalleVentaRepository.topProductosMasVendidos(inicio, fin);
        List<Map<String, Object>> response = new ArrayList<>();

        for (int i = 0; i < Math.min(limite, resultados.size()); i++) {
            Object[] fila = resultados.get(i);
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("producto", fila[0]);
            row.put("cantidad_vendida", fila[1]);
            row.put("total_monto", fila[2]);
            response.add(row);
        }
        return response;
    }

}