package com.restful.api.dto.medico;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.restful.api.entity.enums.Especialidade;
import com.restful.api.entity.Medico;
import io.swagger.v3.oas.annotations.media.Schema;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Schema(description = "Modelo de dados para listagem de médico")
public record DadosListagemMedico(

        @Schema(description = "Nome do médico")
        String nome,

        @Schema(description = "Email do médico")
        String email,

        @JsonFormat(shape = STRING, pattern = "\\d{4,6}")
        @Schema(description = "CRM do médico")
        String crm,

        @Schema(description = "Especialidade do médico")
        Especialidade especialidade
) {
    public DadosListagemMedico(Medico medico) {
        this(
                medico.getNome(),
                medico.getEmail(),
                medico.getCrm(),
                medico.getEspecialidade()
        );
    }
}