package br.com.fiap.metaflow.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record HabilidadeRequest(
        @NotNull(message = "O ID do usuário é obrigatório.") Long usuarioId,
        @NotBlank(message = "O nome da habilidade é obrigatório.") 
        @Size(max = 50, min = 2, message = "Seu nome da habilidade precisa ter entre 2 e 50 caracteres.") 
        String nome,
        @NotBlank(message = "Sua categoria da habilidade é obrigatória.") 
        @Size(max = 50, min = 2, message = "Sua categoria da habilidade precisa ter entre 2 e 50 caracteres.") 
        String categoria,
        @NotNull(message = "Seu nível atual da habilidade é obrigatório.") 
        @Max(value = 5, message = "Seu nível atual deve estar em um intervalo entre 0 - 5.") 
        Integer nivelAtual,
        @NotNull(message = "Seu nível desejado da habilidade é obrigatório.") 
        @Max(value = 5, message = "Seu nível alvo deve estar em um intervalo entre 0 - 5.") 
        Integer nivelDesejado,
        @NotNull(message = "O status de aprendizado é obrigatório.") 
        Boolean emAprendizado) {
}