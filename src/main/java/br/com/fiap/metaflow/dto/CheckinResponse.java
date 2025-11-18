package br.com.fiap.metaflow.dto;

import java.time.LocalDate;

public record CheckinResponse(
        Long id,
        LocalDate data,
        Integer humor,
        Integer qualidadeSono,
        Integer nivelEstresse,
        Integer produtividade,
        Integer tempoTrabalho,
        Integer tempoAprendizado,
        Integer tempoLazer,
        String anotacoes,
        Long usuarioId) {
}