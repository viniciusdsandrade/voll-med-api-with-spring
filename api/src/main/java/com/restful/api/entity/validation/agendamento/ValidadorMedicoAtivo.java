package com.restful.api.entity.validation.agendamento;

import com.restful.api.dto.consulta.DadosAgendamentoConsulta;
import com.restful.api.exception.ValidacaoException;
import com.restful.api.repository.MedicoRepository;
import org.springframework.stereotype.Component;

@Component("ValidadorMedicoAtivo")
public class ValidadorMedicoAtivo implements ValidadorAgendamentoConsulta {

    private final MedicoRepository medicoRepository;

    public ValidadorMedicoAtivo(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    public void validar(DadosAgendamentoConsulta dados) {

        if (dados.idMedico() == null) return;

        boolean isMedicoAtivo = medicoRepository.findAtivoById(dados.idMedico());

        if (!isMedicoAtivo)
            throw new ValidacaoException("O médico informado não está ativo.");

    }
}
