package com.restful.api.entity.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Motivo do cancelamento da consulta")
public enum MotivoCancelamento {

    @Schema(description = "Paciente desistiu da consulta")
    PACIENTE_DESISTIU,

    @Schema(description = "Médico cancelou a consulta")
    MEDICO_CANCELOU,

    @Schema(description = "Outros motivos")
    OUTROS
}