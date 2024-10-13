package com.restful.api.entity.validation.cancelamento;

import com.restful.api.dto.consulta.DadosCancelamentoConsulta;

/**
 * Interface para validação do cancelamento de consultas.
 * <p>
 * Implementações desta interface validam diferentes aspectos do cancelamento de uma consulta médica,
 * lançando exceções em caso de violação de regras específicas.
 */
public interface ValidadorCancelamentoConsulta {

    /**
     * Metodo responsável por validar os dados do cancelamento de uma consulta.
     * <p>
     * Implementações deste metodo devem lançar exceções em caso de validação inválida.
     *
     * @param dados Os dados de cancelamento da consulta a serem validados.
     */
    void validar(DadosCancelamentoConsulta dados);
}
