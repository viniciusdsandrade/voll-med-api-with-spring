package com.restful.api.dto.paciente;

import com.restful.api.dto.endereco.DadosEndereco;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoPaciente(
        @NotNull(message = "O ID não pode ser nulo")
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco
) {
}