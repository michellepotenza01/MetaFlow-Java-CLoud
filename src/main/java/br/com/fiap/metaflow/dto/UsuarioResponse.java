package br.com.fiap.metaflow.dto;

import java.time.LocalDateTime;
import java.util.List;

public record UsuarioResponse(
                Long id,
                String nome,
                String email,
                String tituloProfissional,
                String objetivoCarreira,
                LocalDateTime dataCriacao,
                List<Long> metasIds,
                List<Long> checkinsIds,
                List<Long> habilidadesIds) {
}
