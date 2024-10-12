package com.restful.api.entity.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Especialidade do médico")
public enum Especialidade {

    @Schema(description = "Clínico Geral")
    ORTOPEDIA,

    @Schema(description = "Clínico Geral")
    CARDIOLOGIA,

    @Schema(description = "Clínico Geral")
    GINECOLOGIA,

    @Schema(description = "Clínico Geral")
    DERMATOLOGIA
}