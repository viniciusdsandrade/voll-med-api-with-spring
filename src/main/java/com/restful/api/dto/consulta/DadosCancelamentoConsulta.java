package com.restful.api.dto.consulta;

import com.restful.api.entity.enums.MotivoCancelamento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados para cancelamento de uma consulta")
public record DadosCancelamentoConsulta(
        Long idConsulta,

        @NotNull(message = "O motivo do cancelamento não pode ser nulo.")
        MotivoCancelamento motivoCancelamento
) {
}
