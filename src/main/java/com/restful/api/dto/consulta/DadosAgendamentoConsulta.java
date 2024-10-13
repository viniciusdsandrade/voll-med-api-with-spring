package com.restful.api.dto.consulta;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.restful.api.entity.enums.Especialidade;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Schema(description = "Dados para agendamento de uma consulta")
public record DadosAgendamentoConsulta(
        Long idMedico,

        @NotNull(message = "O campo idPaciente é obrigatório")
        @Schema(description = "Identificador do paciente")
        Long idPaciente,

        @NotNull(message = "O campo dataHora é obrigatório")
        @Future(message = "A data e hora devem ser futuras")
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        @Schema(description = "Data e hora da consulta")
        LocalDateTime dataHora,

        @Schema(description = "Especialidade do médico")
        @NotNull(message = "A especialidade não pode ser nula")
        Especialidade especialidade
) {
}
