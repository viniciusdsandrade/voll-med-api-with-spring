package com.restful.api.entity.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Enumeração que representa as especialidades médicas disponíveis.
 * <p>
 * Cada especialidade médica é documentada com uma descrição do Swagger, associada à área de atuação
 * do médico, como Ortopedia, Cardiologia, Ginecologia e Dermatologia.
 */
@Schema(description = "Especialidade do médico")
public enum Especialidade {

    /**
     * Especialidade em ortopedia.
     */
    @Schema(description = "Ortopedia")
    ORTOPEDIA,

    /**
     * Especialidade em cardiologia.
     */
    @Schema(description = "Cardiologia")
    CARDIOLOGIA,

    /**
     * Especialidade em ginecologia.
     */
    @Schema(description = "Ginecologia")
    GINECOLOGIA,

    /**
     * Especialidade em dermatologia.
     */
    @Schema(description = "Dermatologia")
    DERMATOLOGIA
}
