package com.example.jpa.controllers.clientes;
import com.example.jpa.models.ClientesModel;
import com.example.jpa.repository.ClientesRepository;
import com.example.jpa.repository.VentasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/clientes")
public class ClientesViewController {

    private final ClientesRepository clientesRepository;
    private final VentasRepository ventasRepository;

    @Autowired
    public ClientesViewController(ClientesRepository clientesRepository, VentasRepository ventasRepository) {
        this.clientesRepository = clientesRepository;
        this.ventasRepository = ventasRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", clientesRepository.findAll());
        return "clientes/lista";
    }

    @GetMapping("/nuevo")
    public String nuevoCliente(Model model) {
        model.addAttribute("cliente", new ClientesModel());
        return "clientes/form";
    }

    @PostMapping("/nuevo")
    public String guardarNuevo(@ModelAttribute("cliente") ClientesModel cliente) {
        clientesRepository.save(cliente);
        return "redirect:/clientes";
    }

    @PostMapping("/editar/{id}")
    public String actualizar(@PathVariable Long id, @ModelAttribute("cliente") ClientesModel clienteForm) {
        ClientesModel clienteDB = clientesRepository.findById(id).orElseThrow();
        clienteDB.setNombres(clienteForm.getNombres());
        clienteDB.setApellidos(clienteForm.getApellidos());
        clienteDB.setDireccion(clienteForm.getDireccion());
        clienteDB.setNit(clienteForm.getNit());
        clienteDB.setCorreoElectronico(clienteForm.getCorreoElectronico());
        clientesRepository.save(clienteDB);
        return "redirect:/clientes";
    }

    @GetMapping("/editar/{id}")
    public String editarCliente(@PathVariable Long id, Model model) {
        ClientesModel cliente = clientesRepository.findById(id).orElseThrow();
        model.addAttribute("cliente", cliente);
        return "clientes/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (ventasRepository.existsByCliente_Id(id)) {
            redirectAttributes.addFlashAttribute("error", "No se puede eliminar el cliente porque tiene ventas asociadas.");
            return "redirect:/clientes";
        }
        clientesRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Cliente eliminado correctamente.");
        return "redirect:/clientes";
    }
}