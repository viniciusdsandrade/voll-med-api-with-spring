package com.restful.api.entity.validation.agendamento;

import com.restful.api.dto.consulta.DadosAgendamentoConsulta;
import com.restful.api.exception.ValidacaoException;
import com.restful.api.repository.ConsultaRepository;
import org.springframework.stereotype.Component;

@Component("ValidadorMedicoComOutraConsultaNoMesmoHorario")
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoConsulta {

    private final ConsultaRepository consultaRepository;

    public ValidadorMedicoComOutraConsultaNoMesmoHorario(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    public void validar(DadosAgendamentoConsulta dados) {
        boolean medicoPossuiOutraConsultaNoMesmoHorario = consultaRepository.existsByMedicoIdAndDataHoraAndMotivoCancelamentoIsNull(dados.idMedico(), dados.dataHora());

        if (medicoPossuiOutraConsultaNoMesmoHorario) {
            throw new ValidacaoException("Médico já possui consulta marcada para este dia");
        }
    }
}
