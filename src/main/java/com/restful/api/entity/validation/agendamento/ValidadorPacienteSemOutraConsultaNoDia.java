package com.restful.api.entity.validation.agendamento;

import com.restful.api.dto.consulta.DadosAgendamentoConsulta;
import com.restful.api.exception.ValidacaoException;
import com.restful.api.repository.ConsultaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Validador que verifica se o paciente tem outra consulta marcada no mesmo dia.
 * <p>
 * Se o paciente já tiver uma consulta no mesmo dia, uma {@link ValidacaoException} será lançada.
 */
@Component("ValidadorPacienteSemOutraConsultaNoDia")
public class ValidadorPacienteSemOutraConsultaNoDia implements ValidadorAgendamentoConsulta {

    private final ConsultaRepository consultaRepository;

    public ValidadorPacienteSemOutraConsultaNoDia(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    /**
     * Valida se o paciente já possui outra consulta marcada para o mesmo dia.
     *
     * @param dados Os dados de agendamento da consulta.
     * @throws ValidacaoException Se o paciente já tiver outra consulta no mesmo dia.
     */
    public void validar(DadosAgendamentoConsulta dados) {
        LocalDateTime primeiroHorarioDaClinica = dados.dataHora().withHour(7);
        LocalDateTime ultimoHorarioDaClinica = dados.dataHora().withHour(18);

        boolean pacientePossuiOutraConsultaNoDia = consultaRepository.existsByPacienteIdAndDataHoraBetween(
                dados.idPaciente(),
                primeiroHorarioDaClinica,
                ultimoHorarioDaClinica
        );

        if (pacientePossuiOutraConsultaNoDia) throw new ValidacaoException("Paciente já possui consulta marcada para este dia");
    }
}
