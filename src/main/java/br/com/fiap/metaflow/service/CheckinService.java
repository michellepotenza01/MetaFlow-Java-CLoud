package br.com.fiap.metaflow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.metaflow.dto.CheckinRequest;
import br.com.fiap.metaflow.dto.CheckinResponse;
import br.com.fiap.metaflow.exception.RecursoNaoEncontradoException;
import br.com.fiap.metaflow.mapper.CheckinMapper;
import br.com.fiap.metaflow.model.Checkin;
import br.com.fiap.metaflow.model.Usuario;
import br.com.fiap.metaflow.repository.CheckinRepository;
import br.com.fiap.metaflow.repository.UsuarioRepository;

@Service
public class CheckinService {
  private final CheckinRepository checkinRepository;
  private final UsuarioRepository usuarioRepository;
  private final CheckinMapper checkinMapper = new CheckinMapper();

  @Autowired
  public CheckinService(CheckinRepository checkinRepository, UsuarioRepository usuarioRepository) {
    this.checkinRepository = checkinRepository;
    this.usuarioRepository = usuarioRepository;
  }

  public CheckinResponse save(CheckinRequest checkinRequest) {
    Usuario usuario = usuarioRepository
        .findById(checkinRequest.usuarioId())
        .orElseThrow(() -> new RecursoNaoEncontradoException(
            "Usuário não encontrado com ID: " + checkinRequest.usuarioId()));
    Checkin checkin = checkinMapper.requestToCheckin(checkinRequest, usuario);
    return checkinMapper.checkinToResponse(checkinRepository.save(checkin));
  }

  public List<CheckinResponse> findAll() {
    return checkinRepository.findAll()
        .stream()
        .map(checkinMapper::checkinToResponse)
        .toList();
  }

  public CheckinResponse findById(Long id) {
    return checkinRepository.findById(id)
        .map(checkinMapper::checkinToResponse)
        .orElseThrow(() -> new RecursoNaoEncontradoException("Checkin não encontrado com o ID: " + id));
  }

  public CheckinResponse update(CheckinRequest checkinRequest, Long id) {
    Checkin checkin = checkinRepository.findById(id)
        .orElseThrow(() -> new RecursoNaoEncontradoException("Checkin não encontrado com o ID: " + id));

    checkin.setHumor(checkinRequest.humor());
    checkin.setNivelEstresse(checkinRequest.nivelEstresse());
    checkin.setProdutividade(checkinRequest.produtividade());
    checkin.setTempoAprendizado(checkinRequest.tempoAprendizado());
    checkin.setTempoLazer(checkinRequest.tempoLazer());
    checkin.setTempoTrabalho(checkinRequest.tempoTrabalho());
    checkin.setAnotacoes(checkinRequest.anotacoes());

    return checkinMapper.checkinToResponse(checkinRepository.save(checkin));
  }

  public void delete(Long id) {
    if (!checkinRepository.existsById(id)) {
      throw new RecursoNaoEncontradoException("Checkin não encontrado com o ID: " + id);
    }
    checkinRepository.deleteById(id);
  }
}