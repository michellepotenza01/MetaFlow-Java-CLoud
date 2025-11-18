package br.com.fiap.metaflow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.metaflow.dto.MetaRequest;
import br.com.fiap.metaflow.dto.MetaResponse;
import br.com.fiap.metaflow.exception.RecursoNaoEncontradoException;
import br.com.fiap.metaflow.mapper.MetaMapper;
import br.com.fiap.metaflow.model.Meta;
import br.com.fiap.metaflow.model.Usuario;
import br.com.fiap.metaflow.repository.MetaRepository;
import br.com.fiap.metaflow.repository.UsuarioRepository;

@Service
public class MetaService {
  private final MetaRepository metaRepository;
  private final UsuarioRepository usuarioRepository;
  private final MetaMapper metaMapper = new MetaMapper();

  @Autowired
  public MetaService(MetaRepository metaRepository, UsuarioRepository usuarioRepository) {
    this.metaRepository = metaRepository;
    this.usuarioRepository = usuarioRepository;
  }

  public MetaResponse save(MetaRequest metaRequest) {
    Usuario usuario = usuarioRepository
        .findById(metaRequest.usuarioId())
        .orElseThrow(() -> new RecursoNaoEncontradoException(
            "Usuário não encontrado com ID: " + metaRequest.usuarioId()));
    Meta meta = metaMapper.requestToMeta(metaRequest, usuario);
    return metaMapper.metaToResponse(metaRepository.save(meta));
  }

  public List<MetaResponse> findAll() {
    return metaRepository.findAll()
        .stream()
        .map(metaMapper::metaToResponse)
        .toList();
  }

  public MetaResponse findById(Long id) {
    return metaRepository.findById(id)
        .map(metaMapper::metaToResponse)
        .orElseThrow(() -> new RecursoNaoEncontradoException("Funcionário não encontrado com o ID: " + id));
  }

  public MetaResponse update(MetaRequest metaRequest, Long id) {
    Meta meta = metaRepository.findById(id)
        .orElseThrow(() -> new RecursoNaoEncontradoException("Funcionário não encontrado com o ID: " + id));

    meta.setTitulo(metaRequest.titulo());
    meta.setCategoria(metaRequest.categoria());
    meta.setDescricao(metaRequest.descricao());
    meta.setPrazo(metaRequest.prazo());
    meta.setStatus(metaRequest.status());
    meta.setValorAlvo(metaRequest.valorAlvo());
    meta.setValorAtual(metaRequest.valorAtual());

    return metaMapper.metaToResponse(metaRepository.save(meta));
  }

  public void delete(Long id) {
    if (!metaRepository.existsById(id)) {
      throw new RecursoNaoEncontradoException("Funcionário não encontrado com o ID: " + id);
    }
    metaRepository.deleteById(id);
  }
}