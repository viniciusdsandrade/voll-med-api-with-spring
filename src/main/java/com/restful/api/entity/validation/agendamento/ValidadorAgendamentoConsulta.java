package com.restful.api.entity.validation.agendamento;

import com.restful.api.dto.consulta.DadosAgendamentoConsulta;

/**
 * Interface para validação de agendamento de consultas.
 * <p>
 * Implementações desta interface validam diferentes aspectos do agendamento de uma consulta médica,
 * lançando exceções em caso de violação de regras específicas.
 */
public interface ValidadorAgendamentoConsulta {

    /**
     * Metodo responsável por validar os dados do agendamento de uma consulta.
     * <p>
     * Implementações deste metodo devem lançar exceções em caso de validação inválida.
     *
     * @param dados Os dados de agendamento da consulta a serem validados.
     */
    void validar(DadosAgendamentoConsulta dados);
}
