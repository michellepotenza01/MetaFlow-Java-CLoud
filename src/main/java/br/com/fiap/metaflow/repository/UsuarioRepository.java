package br.com.fiap.metaflow.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.metaflow.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

  boolean existsByEmailAndIdNot(String email, Long usuarioId);

  Optional<Usuario> findByEmail(String email);

}