package com.restful.api.entity.validation.agendamento;

import com.restful.api.dto.consulta.DadosAgendamentoConsulta;
import com.restful.api.exception.ValidacaoException;
import com.restful.api.repository.MedicoRepository;
import org.springframework.stereotype.Component;

/**
 * Validador que verifica se o médico informado no agendamento está ativo no sistema.
 * <p>
 * Se o médico estiver inativo, uma {@link ValidacaoException} será lançada.
 */
@Component("ValidadorMedicoAtivo")
public class ValidadorMedicoAtivo implements ValidadorAgendamentoConsulta {

    private final MedicoRepository medicoRepository;

    public ValidadorMedicoAtivo(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    /**
     * Valida se o médico informado no agendamento está ativo.
     *
     * @param dados Os dados de agendamento da consulta.
     * @throws ValidacaoException Se o médico estiver inativo.
     */
    public void validar(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() == null) return;

        boolean isMedicoAtivo = medicoRepository.findAtivoById(dados.idMedico());

        if (!isMedicoAtivo) throw new ValidacaoException("O médico informado não está ativo.");
    }
}
