package br.com.fiap.metaflow.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioRequest(
        @NotBlank(message = "O nome é obrigatório.") String nome,
        @NotBlank(message = "O email é obrigatório.") @Email(message = "Email fora do formato correto.") String email,
        @NotBlank(message = "O título profissional é obrigatório.") String tituloProfissional,
        @NotBlank(message = "O objetivo de carreira é obrigatório.") String objetivoCarreira) {
}