package com.restful.api.entity.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Enumeração que representa os motivos pelos quais uma consulta médica pode ser cancelada.
 * <p>
 * Cada valor desta enum possui uma anotação do Swagger para descrever o motivo específico
 * do cancelamento, facilitando a documentação da API.
 */
@Schema(description = "Motivo do cancelamento da consulta")
public enum MotivoCancelamento {

    /**
     * O paciente desistiu da consulta antes que ela ocorresse.
     */
    @Schema(description = "Paciente desistiu da consulta")
    PACIENTE_DESISTIU,

    /**
     * O médico responsável cancelou a consulta.
     */
    @Schema(description = "Médico cancelou a consulta")
    MEDICO_CANCELOU,

    /**
     * A consulta foi cancelada por outros motivos não especificados.
     */
    @Schema(description = "Outros motivos")
    OUTROS
}
