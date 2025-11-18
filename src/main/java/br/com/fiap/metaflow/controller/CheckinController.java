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

import br.com.fiap.metaflow.dto.CheckinRequest;
import br.com.fiap.metaflow.dto.CheckinResponse;
import br.com.fiap.metaflow.service.CheckinService;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping(value = "/checkins")
public class CheckinController {
  @Autowired
  private CheckinService checkinService;

  @PostMapping
  public ResponseEntity<CheckinResponse> createCheckin(
      @Valid @RequestBody CheckinRequest checkin) {
    CheckinResponse response = checkinService.save(checkin);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<CheckinResponse>> readCheckins() {
    return ResponseEntity.ok(checkinService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<CheckinResponse> readCheckin(@PathVariable("id") Long id) {
    CheckinResponse checkin = checkinService.findById(id);
    return ResponseEntity.ok(checkin);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CheckinResponse> updateCheckin(@PathVariable("id") Long id,
      @RequestBody CheckinRequest checkinRequest) {
    CheckinResponse checkin = checkinService.update(checkinRequest, id);
    return ResponseEntity.ok(checkin);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCheckin(@PathVariable("id") Long id) {
    checkinService.delete(id);
    return ResponseEntity.noContent().build();
  }
}