package br.com.fiap.metaflow.dto;

import java.time.LocalDate;

import br.com.fiap.metaflow.model.Meta.Categoria;
import br.com.fiap.metaflow.model.Meta.Status;

public record MetaResponse(
                Long id,
                String titulo,
                Categoria categoria,
                Status status,
                String descricao,
                Integer valorAtual,
                Integer valorAlvo,
                LocalDate prazo,
                Long usuarioId) {
}