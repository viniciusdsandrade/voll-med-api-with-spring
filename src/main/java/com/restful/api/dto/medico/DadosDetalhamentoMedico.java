package com.restful.api.dto.medico;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.restful.api.entity.Endereco;
import com.restful.api.entity.enums.Especialidade;
import com.restful.api.entity.Medico;
import io.swagger.v3.oas.annotations.media.Schema;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Schema(description = "Detalhes de um médico")
public record DadosDetalhamentoMedico(

        @Schema(description = "Identificador do médico")
        Long id,

        @Schema(description = "Nome do médico")
        String nome,

        @Schema(description = "Email do médico")
        String email,

        @JsonFormat(shape = STRING, pattern = "\\d{4,6}")
        @Schema(description = "CRM do médico")
        String crm,

        @Schema(description = "Telefone do médico")
        @JsonFormat(shape = STRING, pattern = "\\d{10,11}")
        String telefone,

        @Schema(description = "Especialidade do médico")
        Especialidade especialidade,

        @Schema(description = "Endereço do médico")
        Endereco endereco
) {
    public DadosDetalhamentoMedico(Medico medico) {
        this(
                medico.getId(),
                medico.getNome(),
                medico.getEmail(),
                medico.getCrm(),
                medico.getTelefone(),
                medico.getEspecialidade(),
                medico.getEndereco()
        );
    }
}