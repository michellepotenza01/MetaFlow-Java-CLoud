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

import br.com.fiap.metaflow.dto.HabilidadeRequest;
import br.com.fiap.metaflow.dto.HabilidadeResponse;
import br.com.fiap.metaflow.service.HabilidadeService;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping(value = "/habilidades")
public class HabilidadeController {
  @Autowired
  private HabilidadeService habilidadeService;

  @PostMapping
  public ResponseEntity<HabilidadeResponse> createHabilidade(
      @Valid @RequestBody HabilidadeRequest habilidade) {
    HabilidadeResponse response = habilidadeService.save(habilidade);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<HabilidadeResponse>> readHabilidades() {
    return ResponseEntity.ok(habilidadeService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<HabilidadeResponse> readHabilidade(@PathVariable("id") Long id) {
    HabilidadeResponse habilidade = habilidadeService.findById(id);
    return ResponseEntity.ok(habilidade);
  }

  @PutMapping("/{id}")
  public ResponseEntity<HabilidadeResponse> updateHabilidade(@PathVariable("id") Long id,
      @RequestBody HabilidadeRequest habilidadeRequest) {
    HabilidadeResponse habilidade = habilidadeService.update(habilidadeRequest, id);
    return ResponseEntity.ok(habilidade);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteHabilidade(@PathVariable("id") Long id) {
    habilidadeService.delete(id);
    return ResponseEntity.noContent().build();
  }
}