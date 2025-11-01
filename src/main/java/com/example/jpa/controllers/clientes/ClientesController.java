package com.example.jpa.controllers.clientes;


import com.example.jpa.models.ClientesModel;
import com.example.jpa.repository.ClientesRepository;
import com.example.jpa.repository.VentasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
@RestController
@RequestMapping("/api/cliente")
public class ClientesController {

    private final ClientesRepository clientesRepository;
    private final VentasRepository ventasRepository;

    @Autowired
    public ClientesController(ClientesRepository clientesRepository,
                              VentasRepository ventasRepository) {
        this.clientesRepository = clientesRepository;
        this.ventasRepository = ventasRepository;
    }

    @GetMapping
    public List<ClientesModel> getAllClientes() {
        List<ClientesModel> clientes = clientesRepository.findAll();
        return clientes;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClienteById(@PathVariable Long id) {
        return clientesRepository.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("El cliente con ID " + id + " no existe"));
    }

    @PostMapping
    public ResponseEntity<?> createCliente(@RequestBody ClientesModel cliente) {
        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Debes enviar un cliente que no sea vacío");
        }

        if (cliente.getNit() != null && clientesRepository.existsByNit(cliente.getNit())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Ya existe un cliente con el NIT " + cliente.getNit());
        }

        try {
            ZoneId zonaGT = ZoneId.of("America/Guatemala");
            ZonedDateTime ahoraGT = ZonedDateTime.now(zonaGT);
            cliente.setFechaRegistro(ahoraGT.toLocalDateTime());
            ClientesModel nuevo = clientesRepository.save(cliente);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Cliente creado correctamente con ID " + nuevo.getId());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al crear el cliente: " + ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCliente(@PathVariable Long id, @RequestBody ClientesModel clienteDetails) {
        ClientesModel cliente = clientesRepository.findById(id)
                .orElse(null);
        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede actualizar: el cliente con ID " + id + " no existe");
        }

        try {
            cliente.setNombres(clienteDetails.getNombres());
            cliente.setApellidos(clienteDetails.getApellidos());
            cliente.setDireccion(clienteDetails.getDireccion());
            cliente.setNit(clienteDetails.getNit());
            cliente.setCorreoElectronico(clienteDetails.getCorreoElectronico());
            clientesRepository.save(cliente);
            return ResponseEntity.ok("Cliente actualizado correctamente.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al actualizar el cliente: " + ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCliente(@PathVariable Long id) {
        if (!clientesRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede eliminar: el cliente con ID " + id + " no existe");
        }

        if (ventasRepository.existsByCliente_Id(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("No se puede eliminar el cliente con ID " + id +
                            " porque tiene facturas emitidas a su nombre");
        }

        try {
            clientesRepository.deleteById(id);
            return ResponseEntity.ok("Cliente eliminado correctamente.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el cliente: " + ex.getMessage());
        }
    }
}