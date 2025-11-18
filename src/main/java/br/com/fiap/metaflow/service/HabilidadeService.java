package br.com.fiap.metaflow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.metaflow.dto.HabilidadeRequest;
import br.com.fiap.metaflow.dto.HabilidadeResponse;
import br.com.fiap.metaflow.exception.RecursoNaoEncontradoException;
import br.com.fiap.metaflow.mapper.HabilidadeMapper;
import br.com.fiap.metaflow.model.Habilidade;
import br.com.fiap.metaflow.model.Usuario;
import br.com.fiap.metaflow.repository.HabilidadeRepository;
import br.com.fiap.metaflow.repository.UsuarioRepository;

@Service
public class HabilidadeService {
  private final HabilidadeRepository habilidadeRepository;
  private final UsuarioRepository usuarioRepository;
  private final HabilidadeMapper habilidadeMapper = new HabilidadeMapper();

  @Autowired
  public HabilidadeService(HabilidadeRepository habilidadeRepository, UsuarioRepository usuarioRepository) {
    this.habilidadeRepository = habilidadeRepository;
    this.usuarioRepository = usuarioRepository;
  }

  public HabilidadeResponse save(HabilidadeRequest habilidadeRequest) {
    Usuario usuario = usuarioRepository
        .findById(habilidadeRequest.usuarioId())
        .orElseThrow(() -> new RecursoNaoEncontradoException(
            "Usuário não encontrado com ID: " + habilidadeRequest.usuarioId()));
    Habilidade habilidade = habilidadeMapper.requestToHabilidade(habilidadeRequest, usuario);
    return habilidadeMapper.habilidadeToResponse(habilidadeRepository.save(habilidade));
  }

  public List<HabilidadeResponse> findAll() {
    return habilidadeRepository.findAll()
        .stream()
        .map(habilidadeMapper::habilidadeToResponse)
        .toList();
  }

  public HabilidadeResponse findById(Long id) {
    return habilidadeRepository.findById(id)
        .map(habilidadeMapper::habilidadeToResponse)
        .orElseThrow(() -> new RecursoNaoEncontradoException("Funcionário não encontrado com o ID: " + id));
  }

  public HabilidadeResponse update(HabilidadeRequest habilidadeRequest, Long id) {
    Habilidade habilidade = habilidadeRepository.findById(id)
        .orElseThrow(() -> new RecursoNaoEncontradoException("Funcionário não encontrado com o ID: " + id));

    habilidade.setNome(habilidadeRequest.nome());
    habilidade.setCategoria(habilidadeRequest.categoria());
    habilidade.setNivelAtual(habilidadeRequest.nivelAtual());
    habilidade.setNivelDesejado(habilidadeRequest.nivelDesejado());
    habilidade.setEmAprendizado(habilidadeRequest.emAprendizado());

    return habilidadeMapper.habilidadeToResponse(habilidadeRepository.save(habilidade));
  }

  public void delete(Long id) {
    if (!habilidadeRepository.existsById(id)) {
      throw new RecursoNaoEncontradoException("Funcionário não encontrado com o ID: " + id);
    }
    habilidadeRepository.deleteById(id);
  }
}