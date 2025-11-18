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

import br.com.fiap.metaflow.dto.MetaRequest;
import br.com.fiap.metaflow.dto.MetaResponse;
import br.com.fiap.metaflow.service.MetaService;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping(value = "/metas")
public class MetaController {
  @Autowired
  private MetaService metaService;

  @PostMapping
  public ResponseEntity<MetaResponse> createMeta(
      @Valid @RequestBody MetaRequest meta) {
    MetaResponse response = metaService.save(meta);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<MetaResponse>> readMetas() {
    return ResponseEntity.ok(metaService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<MetaResponse> readMeta(@PathVariable("id") Long id) {
    MetaResponse meta = metaService.findById(id);
    return ResponseEntity.ok(meta);
  }

  @PutMapping("/{id}")
  public ResponseEntity<MetaResponse> updateMeta(@PathVariable("id") Long id,
      @RequestBody MetaRequest metaRequest) {
    MetaResponse meta = metaService.update(metaRequest, id);
    return ResponseEntity.ok(meta);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteMeta(@PathVariable("id") Long id) {
    metaService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
