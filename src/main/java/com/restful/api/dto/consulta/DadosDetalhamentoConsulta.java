package com.restful.api.dto.consulta;

import com.restful.api.entity.Consulta;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(
        Long id,
        MedicoDetalhes medico,
        PacienteDetalhes paciente,
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

    public DadosDetalhamentoConsulta(DadosDetalhamentoConsulta consulta) {
        this(
                consulta.id(),
                consulta.medico(),
                consulta.paciente(),
                consulta.dataHora()
        );
    }
}