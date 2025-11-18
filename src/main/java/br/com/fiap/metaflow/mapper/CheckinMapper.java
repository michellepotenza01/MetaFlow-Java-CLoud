package br.com.fiap.metaflow.mapper;

import java.time.LocalDate;
import java.util.Optional;

import br.com.fiap.metaflow.dto.CheckinRequest;
import br.com.fiap.metaflow.dto.CheckinResponse;
import br.com.fiap.metaflow.model.Checkin;
import br.com.fiap.metaflow.model.Usuario;

public class CheckinMapper {

  public Checkin requestToCheckin(CheckinRequest checkinRequest, Usuario usuario) {
    Checkin checkin = new Checkin();
    checkin.setHumor(checkinRequest.humor());
    checkin.setQualidadeSono(checkinRequest.qualidadeSono()); 
    checkin.setNivelEstresse(checkinRequest.nivelEstresse());
    checkin.setProdutividade(checkinRequest.produtividade());
    checkin.setTempoAprendizado(checkinRequest.tempoAprendizado());
    checkin.setTempoLazer(checkinRequest.tempoLazer());
    checkin.setTempoTrabalho(checkinRequest.tempoTrabalho());
    checkin.setData(LocalDate.now());
    checkin.setAnotacoes(checkinRequest.anotacoes());
    checkin.setUsuario(usuario);
    return checkin;
  }

  public CheckinResponse checkinToResponse(Checkin checkin) {
    Long usuarioId = Optional.ofNullable(checkin.getUsuario())
        .map(Usuario::getId)
        .orElse(null);

    return new CheckinResponse(
        checkin.getId(),
        checkin.getData(),
        checkin.getHumor(),
        checkin.getQualidadeSono(),
        checkin.getNivelEstresse(),
        checkin.getProdutividade(),
        checkin.getTempoTrabalho(),
        checkin.getTempoAprendizado(),
        checkin.getTempoLazer(),
        checkin.getAnotacoes(),
        usuarioId);
  }
}