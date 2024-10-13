package com.restful.api.dto.consulta;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.restful.api.entity.Paciente;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Detalhes de um paciente")
public record PacienteDetalhes(

        @Schema(description = "ID do paciente")
        Long id,

        @Schema(description = "Nome do paciente")
        String nome,

        @JsonFormat(pattern = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", shape = STRING, locale = "pt-BR", timezone = "Brazil/East")
        @Schema(description = "CPF do paciente")
        String cpf,

        @JsonFormat(pattern = "(##) #####-####", shape = STRING, locale = "pt-BR", timezone = "Brazil/East")
        @Schema(description = "Telefone do paciente")
        String telefone,

        @Schema(description = "E-mail do paciente")
        String email
) {
    public PacienteDetalhes(Paciente paciente) {
        this(
                paciente.getId(),
                paciente.getNome(),
                paciente.getCpf(),
                paciente.getTelefone(),
                paciente.getEmail()
        );
    }
}
