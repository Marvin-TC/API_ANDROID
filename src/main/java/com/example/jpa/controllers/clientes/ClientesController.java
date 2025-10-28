package com.example.jpa.controllers.clientes;


import com.example.jpa.models.ClientesModel;
import com.example.jpa.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClientesController {

    private final ClientesRepository clientesRepository;

    @Autowired
    public ClientesController(ClientesRepository clientesRepository) {
        this.clientesRepository = clientesRepository;
    }

    @GetMapping
    public List<ClientesModel> getAllClientes() {
        return clientesRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientesModel> getClienteById(@PathVariable Long id) {
        return clientesRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ClientesModel createCliente(@RequestBody ClientesModel cliente) {
        return clientesRepository.save(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientesModel> updateCliente(@PathVariable Long id, @RequestBody ClientesModel clienteDetails) {
        return clientesRepository.findById(id)
                .map(cliente -> {
                    cliente.setNombres(clienteDetails.getNombres());
                    cliente.setApellidos(clienteDetails.getApellidos());
                    cliente.setDireccion(clienteDetails.getDireccion());
                    cliente.setNit(clienteDetails.getNit());
                    cliente.setCorreoElectronico(clienteDetails.getCorreoElectronico());
                    ClientesModel updated = clientesRepository.save(cliente);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        if (!clientesRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        clientesRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}