package br.com.fiap.metaflow.mapper;

import java.time.LocalDateTime;
import java.util.List;

import br.com.fiap.metaflow.dto.UsuarioRequest;
import br.com.fiap.metaflow.dto.UsuarioResponse;
import br.com.fiap.metaflow.model.Checkin;
import br.com.fiap.metaflow.model.Habilidade;
import br.com.fiap.metaflow.model.Meta;
import br.com.fiap.metaflow.model.Usuario;

public class UsuarioMapper {

  public Usuario requestToUsuario(UsuarioRequest usuarioRequest) {
    Usuario usuario = new Usuario();
    usuario.setNome(usuarioRequest.nome());
    usuario.setEmail(usuarioRequest.email());
    usuario.setTituloProfissional(usuarioRequest.tituloProfissional());
    usuario.setObjetivoCarreira(usuarioRequest.objetivoCarreira());
    usuario.setDataCriacao(LocalDateTime.now());
    return usuario;
  }

  public UsuarioResponse usuarioToResponse(Usuario usuario) {
    List<Long> checkinsIds = usuario.getCheckins()
        .stream()
        .map(Checkin::getId)
        .toList();

    List<Long> metasIds = usuario.getMetas()
        .stream()
        .map(Meta::getId)
        .toList();

    List<Long> habilidadesIds = usuario.getHabilidades()
        .stream()
        .map(Habilidade::getId)
        .toList();

    return new UsuarioResponse(
        usuario.getId(),
        usuario.getNome(),
        usuario.getEmail(),
        usuario.getTituloProfissional(),
        usuario.getObjetivoCarreira(),
        usuario.getDataCriacao(),
        metasIds,
        checkinsIds,
        habilidadesIds);
  }
}