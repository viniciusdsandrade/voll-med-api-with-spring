package com.restful.api.entity.validation.agendamento;

import com.restful.api.dto.consulta.DadosAgendamentoConsulta;
import com.restful.api.exception.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Validador que verifica se a consulta foi agendada com pelo menos 30 minutos de antecedência.
 * <p>
 * Se o agendamento for para um horário muito próximo, lança uma {@link ValidacaoException}.
 */
@Component("ValidadorHorarioAntecedenciaAgendamento")
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoConsulta {

    /**
     * Valida se o horário da consulta foi agendado com pelo menos 30 minutos de antecedência.
     *
     * @param dados Os dados de agendamento da consulta.
     * @throws ValidacaoException Se a consulta não tiver antecedência mínima de 30 minutos.
     */
    public void validar(DadosAgendamentoConsulta dados) {
        LocalDateTime dataConsulta = dados.dataHora();
        LocalDateTime agora = LocalDateTime.now();
        long diferencaEmMinutos = Duration.between(agora, dataConsulta).toMinutes();

        if (diferencaEmMinutos < 30) throw new ValidacaoException("A consulta deve ser marcada com pelo menos 30 minutos de antecedência.");
    }
}
