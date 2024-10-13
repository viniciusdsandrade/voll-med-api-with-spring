package com.restful.api.dto.consulta;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.restful.api.entity.Consulta;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Schema(description = "Dados detalhados de uma consulta")
public record DadosDetalhamentoConsulta(

        @Schema(description = "Identificador da consulta")
        Long id,

        @Valid
        @Schema(description = "Médico responsável pela consulta")
        MedicoDetalhes medico,

        @Valid
        @Schema(description = "Paciente atendido na consulta")
        PacienteDetalhes paciente,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = STRING, locale = "pt-BR", timezone = "Brazil/East")
        @Schema(description = "Data e hora da consulta", example = "01/01/2021 14:30:00")
        LocalDateTime dataHora
) {
    public DadosDetalhamentoConsulta(Consulta consulta) {
        this(
                consulta.getId(),
                new MedicoDetalhes(consulta.getMedico()),
                new PacienteDetalhes(consulta.getPaciente()),
                consulta.getDataHora()
        );
    }

    public DadosDetalhamentoConsulta(@Valid DadosDetalhamentoConsulta consulta) {
        this(
                consulta.id(),
                consulta.medico(),
                consulta.paciente(),
                consulta.dataHora()
        );
    }
}