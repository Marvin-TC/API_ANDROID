package com.example.jpa.controllers;

import com.example.jpa.dto.LoginResponse;
import com.example.jpa.models.UsuariosModel;
import com.example.jpa.repository.UsuariosRepository;
import com.example.jpa.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuariosController {
    private final UsuariosRepository usuarioRepository;

    @Autowired
    public UsuariosController(UsuariosRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    private boolean userExiste(String userName) {
        try {
            return usuarioRepository.existsByUserName(userName.trim());
        } catch (Exception e) {
            return false;
        }
    }

    //  Verificar login del usuario
    @PostMapping("/login")
    public ResponseEntity<?> checkLogin(@RequestParam String userName,
                                        @RequestParam String password) {
        try {
            Optional<UsuariosModel> usuarioOpt = usuarioRepository.findByUserName(userName.trim());
            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Usuario o contraseña incorrectos.");
            }

            UsuariosModel usuario = usuarioOpt.get();
            byte[] hash = PasswordUtils.fromBase64(usuario.getPasswordHash());
            byte[] salt = PasswordUtils.fromBase64(usuario.getSalt());
            boolean valido = PasswordUtils.verify(password.toCharArray(), salt, hash);
            if (valido) {
                // Convertir los datos del usuario a JSON
                LoginResponse response = new LoginResponse(
                        usuario.getId(),
                        usuario.getNombres() + " " + usuario.getApellidos(),
                        usuario.getUserName()
                );
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Usuario o contraseña incorrectos.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar el login: " + e.getMessage());
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@RequestParam String nombres,
                                              @RequestParam String apellidos,
                                              @RequestParam String userName,
                                              @RequestParam String password) {
        try {
            if (userExiste(userName)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("El usuario '" + userName + "' ya existe.");
            }

            // Generar salt y hash con PasswordUtils
            byte[] salt = PasswordUtils.generarSalt();
            byte[] hash = PasswordUtils.hash(password.toCharArray(), salt);

            UsuariosModel nuevo = new UsuariosModel();
            nuevo.setNombres(nombres.trim());
            nuevo.setApellidos(apellidos.trim());
            nuevo.setUserName(userName.trim());
            nuevo.setSalt(PasswordUtils.toBase64(salt));
            nuevo.setPasswordHash(PasswordUtils.toBase64(hash));

            // Fecha de registro con zona horaria Guatemala
            ZoneId zonaGT = ZoneId.of("America/Guatemala");
            ZonedDateTime ahoraGT = ZonedDateTime.now(zonaGT);
            nuevo.setFechaRegistro(ahoraGT.toLocalDateTime());

            usuarioRepository.save(nuevo);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Usuario registrado correctamente con nombre: " + userName);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar el usuario: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id,
                                               @RequestParam String nombres,
                                               @RequestParam String apellidos) {
        try {
            Optional<UsuariosModel> usuarioOpt = usuarioRepository.findById(id);
            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No existe un usuario con ID " + id);
            }

            UsuariosModel usuario = usuarioOpt.get();

            // Solo se actualizan los datos personales
            usuario.setNombres(nombres.trim());
            usuario.setApellidos(apellidos.trim());

            usuarioRepository.save(usuario);
            return ResponseEntity.ok("Datos personales del usuario actualizados correctamente.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar usuario: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listarUsuarios() {
        try {
            List<UsuariosModel> usuarios = usuarioRepository.findAll();

            if (usuarios.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body("No hay usuarios registrados en el sistema.");
            }
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener la lista de usuarios: " + e.getMessage());
        }
    }
}