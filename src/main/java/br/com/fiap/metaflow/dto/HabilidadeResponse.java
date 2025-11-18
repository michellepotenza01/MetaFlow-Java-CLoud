package br.com.fiap.metaflow.dto;

public record HabilidadeResponse(
                Long id,
                String nome,
                String categoria,
                Integer nivelAtual,
                Integer nivelDesejado,
                Boolean emAprendizado,
                Long usuarioId) {
}
