package com.restful.api.entity.validation.agendamento;

import com.restful.api.dto.consulta.DadosAgendamentoConsulta;
import com.restful.api.exception.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

/**
 * Validador que verifica se a consulta está marcada dentro do horário de funcionamento da clínica.
 * <p>
 * A clínica funciona de segunda a sábado, das 07h00 às 18h00. Se a consulta for marcada fora
 * desse horário, uma {@link ValidacaoException} será lançada.
 */
@Component("ValidadorHorarioFuncionamentoClinica")
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoConsulta {

    /**
     * Valida se a consulta está marcada dentro do horário de funcionamento da clínica.
     *
     * @param dados Os dados de agendamento da consulta.
     * @throws ValidacaoException Se a consulta for marcada fora do horário de funcionamento.
     */
    public void validar(DadosAgendamentoConsulta dados) {
        LocalDateTime dataConsulta = dados.dataHora();

        boolean domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        boolean antesDaAberturaDaClinica = dataConsulta.getHour() < 7;
        boolean depoisDoFechamentoDaClinica = dataConsulta.getHour() > 18;

        if (domingo || antesDaAberturaDaClinica || depoisDoFechamentoDaClinica)
            throw new ValidacaoException("A clínica não funciona neste horário.");
    }
}
