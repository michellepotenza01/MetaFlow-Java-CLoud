package br.com.fiap.metaflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.metaflow.model.Habilidade;

@Repository
public interface HabilidadeRepository extends JpaRepository<Habilidade, Long> {
  List<Habilidade> findByUsuarioId(Long usuarioId);
}