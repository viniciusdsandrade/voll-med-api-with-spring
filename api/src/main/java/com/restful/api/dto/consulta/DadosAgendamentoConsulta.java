package com.restful.api.dto.consulta;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.restful.api.entity.enums.Especialidade;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(
        Long idMedico,

        @NotNull(message = "O campo idPaciente é obrigatório")
        Long idPaciente,

        @NotNull(message = "O campo dataHora é obrigatório")
        @Future(message = "A data e hora devem ser futuras")
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataHora,

        Especialidade especialidade
) {
}
