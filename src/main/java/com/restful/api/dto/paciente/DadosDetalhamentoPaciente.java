package com.restful.api.dto.paciente;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.restful.api.entity.Endereco;
import com.restful.api.entity.Paciente;
import io.swagger.v3.oas.annotations.media.Schema;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Schema(description = "Detalhes de um paciente")
public record DadosDetalhamentoPaciente(
        @Schema(description = "ID do paciente")
        Long id,

        @Schema(description = "Nome do paciente")
        String nome,

        @Schema(description = "E-mail do paciente")
        String email,

        @JsonFormat(pattern = "(##) #####-####", shape = STRING, locale = "pt-BR", timezone = "Brazil/East")
        @Schema(description = "Telefone do paciente")
        String telefone,

        @JsonFormat(pattern = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", shape = STRING, locale = "pt-BR", timezone = "Brazil/East")
        @Schema(description = "CPF do paciente")
        String cpf,


        @Schema(description = "Endereço do paciente")
        Endereco endereco
) {
    public DadosDetalhamentoPaciente(Paciente paciente) {
        this(
                paciente.getId(),
                paciente.getNome(),
                paciente.getEmail(),
                paciente.getTelefone(),
                paciente.getCpf(),
                paciente.getEndereco()
        );
    }
}