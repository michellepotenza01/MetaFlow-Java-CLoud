package br.com.fiap.metaflow.mapper;

import java.util.Optional;

import br.com.fiap.metaflow.dto.MetaRequest;
import br.com.fiap.metaflow.dto.MetaResponse;
import br.com.fiap.metaflow.model.Meta;
import br.com.fiap.metaflow.model.Usuario;

public class MetaMapper {

  public Meta requestToMeta(MetaRequest metaRequest, Usuario usuario) {
    Meta meta = new Meta();
    meta.setTitulo(metaRequest.titulo());
    meta.setCategoria(metaRequest.categoria());
    meta.setDescricao(metaRequest.descricao());
    meta.setPrazo(metaRequest.prazo());
    meta.setStatus(metaRequest.status());
    meta.setValorAlvo(metaRequest.valorAlvo());
    meta.setValorAtual(metaRequest.valorAtual());
    meta.setUsuario(usuario);
    return meta;
  }

  public MetaResponse metaToResponse(Meta meta) {
    Long usuarioId = Optional.ofNullable(meta.getUsuario())
        .map(Usuario::getId)
        .orElse(null);

    return new MetaResponse(
        meta.getId(),
        meta.getTitulo(),
        meta.getCategoria(),
        meta.getStatus(),
        meta.getDescricao(),
        meta.getValorAtual(),
        meta.getValorAlvo(),
        meta.getPrazo(),
        usuarioId);
  }
}