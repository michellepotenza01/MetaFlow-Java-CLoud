package br.com.fiap.metaflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.metaflow.model.Checkin;

@Repository
public interface CheckinRepository extends JpaRepository<Checkin, Long> {
  List<Checkin> findByUsuarioId(Long usuarioId);
}