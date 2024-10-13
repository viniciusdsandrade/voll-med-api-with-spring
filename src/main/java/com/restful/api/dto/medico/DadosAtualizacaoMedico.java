package com.restful.api.dto.medico;

import com.restful.api.dto.endereco.DadosCadastroEndereco;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados para atualização de um médico")
public record DadosAtualizacaoMedico(
        @NotNull(message = "O ID não pode ser nulo")
        Long id,
        String nome,
        String telefone,
        DadosCadastroEndereco endereco
) {
}
