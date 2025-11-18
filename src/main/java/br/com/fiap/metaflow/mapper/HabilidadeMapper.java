package br.com.fiap.metaflow.mapper;

import java.util.Optional;

import br.com.fiap.metaflow.dto.HabilidadeRequest;
import br.com.fiap.metaflow.dto.HabilidadeResponse;
import br.com.fiap.metaflow.model.Habilidade;
import br.com.fiap.metaflow.model.Usuario;

public class HabilidadeMapper {

  public Habilidade requestToHabilidade(HabilidadeRequest habilidadeRequest, Usuario usuario) {
    Habilidade habilidade = new Habilidade();
    habilidade.setNome(habilidadeRequest.nome());
    habilidade.setCategoria(habilidadeRequest.categoria());
    habilidade.setNivelAtual(habilidadeRequest.nivelAtual());
    habilidade.setNivelDesejado(habilidadeRequest.nivelDesejado());
    habilidade.setEmAprendizado(habilidadeRequest.emAprendizado());
    habilidade.setUsuario(usuario);
    return habilidade;
  }

  public HabilidadeResponse habilidadeToResponse(Habilidade habilidade) {
    Long usuarioId = Optional.ofNullable(habilidade.getUsuario())
        .map(Usuario::getId)
        .orElse(null);

    return new HabilidadeResponse(
        habilidade.getId(),
        habilidade.getNome(),
        habilidade.getCategoria(),
        habilidade.getNivelAtual(),
        habilidade.getNivelDesejado(),
        habilidade.getEmAprendizado(),
        usuarioId);
  }
}