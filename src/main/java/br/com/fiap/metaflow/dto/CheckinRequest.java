package br.com.fiap.metaflow.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CheckinRequest(
        @NotNull(message = "O ID do usuário é obrigatório.") Long usuarioId,
        @NotNull(message = "O nível de humor é obrigatório.") Integer humor,
        @NotNull(message = "O nível qualidade de sono é obrigatória.") Integer qualidadeSono,
        @NotNull(message = "O nível de estresse é obrigatório.") Integer nivelEstresse,
        @NotNull(message = "O nível de produtividade é obrigatório.") Integer produtividade,
        Integer tempoTrabalho,
        Integer tempoAprendizado,
        Integer tempoLazer,
        @Size(max = 200, message = "As anotações não podem exceder 200 caracteres") String anotacoes) {
}