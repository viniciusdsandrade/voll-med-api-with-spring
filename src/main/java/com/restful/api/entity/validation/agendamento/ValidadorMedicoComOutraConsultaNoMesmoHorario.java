package com.restful.api.entity.validation.agendamento;

import com.restful.api.dto.consulta.DadosAgendamentoConsulta;
import com.restful.api.exception.ValidacaoException;
import com.restful.api.repository.ConsultaRepository;
import org.springframework.stereotype.Component;

/**
 * Validador que verifica se o médico tem outra consulta marcada no mesmo horário.
 * <p>
 * Se o médico já tiver uma consulta no horário solicitado, uma {@link ValidacaoException} será lançada.
 */
@Component("ValidadorMedicoComOutraConsultaNoMesmoHorario")
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoConsulta {

    private final ConsultaRepository consultaRepository;

    public ValidadorMedicoComOutraConsultaNoMesmoHorario(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    /**
     * Valida se o médico já possui outra consulta marcada no mesmo horário.
     *
     * @param dados Os dados de agendamento da consulta.
     * @throws ValidacaoException Se o médico já tiver outra consulta no mesmo horário.
     */
    public void validar(DadosAgendamentoConsulta dados) {
        boolean medicoPossuiOutraConsultaNoMesmoHorario = consultaRepository.existsByMedicoIdAndDataHoraAndMotivoCancelamentoIsNull(dados.idMedico(), dados.dataHora());

        if (medicoPossuiOutraConsultaNoMesmoHorario) throw new ValidacaoException("Médico já possui consulta marcada para este dia");
    }
}
