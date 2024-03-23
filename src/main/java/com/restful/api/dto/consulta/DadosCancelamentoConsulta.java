package com.restful.api.dto.consulta;

import com.restful.api.entity.enums.MotivoCancelamento;
import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoConsulta(
        Long idConsulta,

        @NotNull(message = "O motivo do cancelamento não pode ser nulo.")
        MotivoCancelamento motivoCancelamento
) {
}
