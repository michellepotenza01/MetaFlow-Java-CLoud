package br.com.fiap.metaflow.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.fiap.metaflow.model.Meta;
import br.com.fiap.metaflow.model.Meta.Status;

@Repository
public interface MetaRepository extends JpaRepository<Meta, Long> {

  long countByStatus(Status concluida);

  @Query("SELECT m.categoria, COUNT(m) FROM Meta m GROUP BY m.categoria")
  List<Object[]> countMetasPorCategoria();
  
  List<Meta> findByUsuarioId(Long usuarioId);

}
