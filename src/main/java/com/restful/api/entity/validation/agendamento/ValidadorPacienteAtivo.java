package com.restful.api.entity.validation.agendamento;

import com.restful.api.dto.consulta.DadosAgendamentoConsulta;
import com.restful.api.exception.ValidacaoException;
import com.restful.api.repository.PacienteRepository;
import org.springframework.stereotype.Component;

/**
 * Validador que verifica se o paciente está ativo no sistema.
 * <p>
 * Se o paciente estiver inativo, uma exceção será lançada, impedindo o agendamento da consulta.
 */
@Component("ValidadorPacienteAtivo")
public class ValidadorPacienteAtivo implements ValidadorAgendamentoConsulta {

    private final PacienteRepository pacienteRepository;

    public ValidadorPacienteAtivo(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    /**
     * Valida se o paciente informado no agendamento está ativo.
     *
     * @param dados Os dados de agendamento da consulta.
     * @throws RuntimeException Se o paciente estiver inativo.
     */
    public void validar(DadosAgendamentoConsulta dados) {
        boolean pacienteEstaAtivo = pacienteRepository.findAtivoById(dados.idPaciente());
        if (!pacienteEstaAtivo) throw new ValidacaoException("O paciente não está ativo.");
    }
}
