package br.com.fiap.metaflow.dto;

import java.time.LocalDate;

import br.com.fiap.metaflow.model.Meta.Categoria;
import br.com.fiap.metaflow.model.Meta.Status;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record MetaRequest(
    @NotNull(message = "O ID do usuário é obrigatório.") Long usuarioId,
    @NotBlank(message = "O título da meta é obrigatório.") 
    @Size(max = 50, min = 2, message = "Seu título da meta precisa ter entre 2 e 50 caracteres.") 
    String titulo,
    @NotNull(message = "Sua categoria da meta é obrigatória.") Categoria categoria,
    @NotNull(message = "Seu status da meta é obrigatório.") Status status,
    @Size(max = 200, message = "Sua descrição da meta não pode exceder 200 caracteres") 
    String descricao,
    @NotNull(message = "Seu nível atual da habilidade é obrigatório.") 
    @Max(value = 5, message = "Seu nível atual deve estar em um intervalo entre 0 - 5.") 
    @PositiveOrZero(message = "Seu nível atual deve ser positivo ou zero.") 
    Integer valorAtual,
    @NotNull(message = "Seu nível desejado da habilidade é obrigatório.") 
    @Max(value = 5, message = "Seu nível alvo deve estar em um intervalo entre 0 - 5.") 
    @PositiveOrZero(message = "Seu nível alvo deve ser positivo ou zero.") 
    Integer valorAlvo,
    @NotNull(message = "O prazo de meta é obrigatório.") 
    @Future(message = "O prazo deve ser uma data futura.") 
    LocalDate prazo) {
}