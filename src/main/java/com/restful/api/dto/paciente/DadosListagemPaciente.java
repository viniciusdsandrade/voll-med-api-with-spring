package com.restful.api.dto.paciente;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.restful.api.entity.Paciente;
import io.swagger.v3.oas.annotations.media.Schema;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Schema(description = "Dados de listagem de um paciente")
public record DadosListagemPaciente(
        @Schema(description = "ID do paciente")
        Long id,

        @Schema(description = "Nome do paciente")
        String nome,

        @Schema(description = "E-mail do paciente")
        String email,

        @JsonFormat(pattern = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", shape = STRING, locale = "pt-BR", timezone = "Brazil/East")
        @Schema(description = "CPF do paciente")
        String cpf
) {
    public DadosListagemPaciente(Paciente paciente) {
        this(
                paciente.getId(),
                paciente.getNome(),
                paciente.getEmail(),
                paciente.getCpf()
        );
    }
}