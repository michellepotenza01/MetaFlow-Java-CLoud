package br.com.fiap.metaflow.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.metaflow.dto.UsuarioRequest;
import br.com.fiap.metaflow.dto.UsuarioResponse;
import br.com.fiap.metaflow.exception.RecursoNaoEncontradoException;
import br.com.fiap.metaflow.exception.RegraDeNegocioVioladaException;
import br.com.fiap.metaflow.mapper.UsuarioMapper;
import br.com.fiap.metaflow.model.Usuario;
import br.com.fiap.metaflow.repository.UsuarioRepository;

@Service
public class UsuarioService {
  private final UsuarioRepository usuarioRepository;
  private final UsuarioMapper usuarioMapper = new UsuarioMapper();

  @Autowired
  public UsuarioService(UsuarioRepository usuarioRepository) {
    this.usuarioRepository = usuarioRepository;
  }

  private void validarUnicidade(UsuarioRequest usuarioRequest, Long usuarioId) {
    if (usuarioRepository.existsByEmailAndIdNot(usuarioRequest.email(), usuarioId)) {
      throw new RegraDeNegocioVioladaException("Já existe um usuário com este e-mail.");
    }
  }

  public UsuarioResponse save(UsuarioRequest usuarioRequest) {
    validarUnicidade(usuarioRequest, null);
    Usuario usuario = usuarioMapper.requestToUsuario(usuarioRequest);
    return usuarioMapper.usuarioToResponse(usuarioRepository.save(usuario));
  }

  public List<UsuarioResponse> findAll() {
    return usuarioRepository.findAll()
        .stream()
        .map(usuarioMapper::usuarioToResponse)
        .toList();
  }

  public UsuarioResponse findById(Long id) {
    return usuarioRepository.findById(id)
        .map(usuarioMapper::usuarioToResponse)
        .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com o ID: " + id));
  }

  public UsuarioResponse findByEmail(String email) {
    return usuarioRepository.findByEmail(email)
        .map(usuarioMapper::usuarioToResponse)
        .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com o e-mail: " + email));
  }

  public UsuarioResponse update(UsuarioRequest usuarioRequest, Long id) {
    Usuario usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com o ID: " + id));

    usuario.setNome(usuarioRequest.nome());
    usuario.setEmail(usuarioRequest.email());
    usuario.setTituloProfissional(usuarioRequest.tituloProfissional());
    usuario.setObjetivoCarreira(usuarioRequest.objetivoCarreira());

    return usuarioMapper.usuarioToResponse(usuarioRepository.save(usuario));
  }

  public void delete(Long id) {
    if (!usuarioRepository.existsById(id)) {
      throw new RecursoNaoEncontradoException("Usuário não encontrado com o ID: " + id);
    }
    usuarioRepository.deleteById(id);
  }
}