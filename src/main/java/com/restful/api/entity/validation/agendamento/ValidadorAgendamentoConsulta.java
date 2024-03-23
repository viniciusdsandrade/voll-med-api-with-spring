package com.restful.api.entity.validation.agendamento;

import com.restful.api.dto.consulta.DadosAgendamentoConsulta;

public interface ValidadorAgendamentoConsulta {
    void validar(DadosAgendamentoConsulta dados);
}
