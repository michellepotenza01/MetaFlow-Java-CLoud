package br.com.fiap.metaflow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.metaflow.dto.UsuarioRequest;
import br.com.fiap.metaflow.dto.UsuarioResponse;
import br.com.fiap.metaflow.service.UsuarioService;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping(value = "/usuarios")
public class UsuarioController {
  @Autowired
  private UsuarioService usuarioService;

  @PostMapping
  public ResponseEntity<UsuarioResponse> createUsuario(
      @Valid @RequestBody UsuarioRequest usuario) {
    UsuarioResponse response = usuarioService.save(usuario);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<UsuarioResponse>> readUsuarios() {
    return ResponseEntity.ok(usuarioService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<UsuarioResponse> readUsuario(@PathVariable("id") Long id) {
    UsuarioResponse usuario = usuarioService.findById(id);
    return ResponseEntity.ok(usuario);
  }

  @GetMapping("/email/{email}")
  public ResponseEntity<UsuarioResponse> readUsuarioByEmail(@PathVariable("email") String email) {
    UsuarioResponse usuario = usuarioService.findByEmail(email);
    return ResponseEntity.ok(usuario);
  }
  
  @PutMapping("/{id}")
  public ResponseEntity<UsuarioResponse> updateUsuario(@PathVariable("id") Long id,
      @RequestBody UsuarioRequest usuarioRequest) {
    UsuarioResponse usuario = usuarioService.update(usuarioRequest, id);
    return ResponseEntity.ok(usuario);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUsuario(@PathVariable("id") Long id) {
    usuarioService.delete(id);
    return ResponseEntity.noContent().build();
  }
}