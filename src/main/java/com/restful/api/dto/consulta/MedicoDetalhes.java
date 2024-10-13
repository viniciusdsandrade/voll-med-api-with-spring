package com.restful.api.dto.consulta;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.restful.api.entity.enums.Especialidade;
import com.restful.api.entity.Medico;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Detalhes de um médico")
public record MedicoDetalhes(

        @Schema(description = "ID do médico")
        Long id,

        @Schema(description = "Nome do médico")
        String nome,

        @Schema(description = "E-mail do médico")
        String email,

        @JsonFormat(pattern = "\\d{6}/[A-Z]{2}", shape = STRING, locale = "pt-BR", timezone = "Brazil/East")
        @Schema(description = "CRM do médico")
        String crm,

        @JsonFormat(pattern = "(##) #####-####", shape = STRING, locale = "pt-BR", timezone = "Brazil/East")
        @Schema(description = "Telefone do médico")
        String telefone,

        @Schema(description = "Especialidade médica")
        Especialidade especialidade
) {
    public MedicoDetalhes(Medico medico) {
        this(
                medico.getId(),
                medico.getNome(),
                medico.getEmail(),
                medico.getCrm(),
                medico.getTelefone(),
                medico.getEspecialidade()
        );
    }
}
