package com.example.jpa.controllers.clientes;


import com.example.jpa.models.ClientesModel;
import com.example.jpa.repository.ClientesRepository;
import com.example.jpa.repository.VentasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
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
        if (clientes.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No hay clientes registrados");
        }
        return clientes;
    }

    @GetMapping("/{id}")
    public ClientesModel getClienteById(@PathVariable Long id) {
        return clientesRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "El cliente con ID " + id + " no existe"
                ));
    }

    @PostMapping
    public ClientesModel createCliente(ClientesModel cliente) {
        if (cliente==null){throw new ResponseStatusException(HttpStatus.NO_CONTENT,"debes enviar un cliente que no sea vacio");}

        if (cliente.getNit() != null && clientesRepository.existsByNit(cliente.getNit())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe un cliente con el NIT " + cliente.getNit()
            );
        }
        try {
            return clientesRepository.save(cliente);
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Error al crear el cliente: " + ex.getMessage()
            );
        }
    }

    @PutMapping("/{id}")
    public ClientesModel updateCliente(@PathVariable Long id, ClientesModel clienteDetails) {
        ClientesModel cliente = clientesRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No se puede actualizar: el cliente con ID " + id + " no existe"
                ));
        try {
            cliente.setNombres(clienteDetails.getNombres());
            cliente.setApellidos(clienteDetails.getApellidos());
            cliente.setDireccion(clienteDetails.getDireccion());
            cliente.setNit(clienteDetails.getNit());
            cliente.setCorreoElectronico(clienteDetails.getCorreoElectronico());
            return clientesRepository.save(cliente);
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Error al actualizar el cliente: " + ex.getMessage()
            );
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCliente(@PathVariable Long id) {
        if (!clientesRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No se puede eliminar: el cliente con ID " + id + " no existe"
            );
        }

        //VALIDAR SI NO TIENE VENTAS YA REGISTRADAS para no eliminar
        if (ventasRepository.existsByCliente_Id(id)){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar el cliente con ID: "+id+ " porque tiene facturas emitas a su nombre"
            );
        }

        try {
            clientesRepository.deleteById(id);
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al eliminar el cliente: " + ex.getMessage()
            );
        }
    }
}