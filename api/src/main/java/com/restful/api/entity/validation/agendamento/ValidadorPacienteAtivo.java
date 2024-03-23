package com.restful.api.entity.validation.agendamento;

import com.restful.api.dto.consulta.DadosAgendamentoConsulta;
import com.restful.api.repository.PacienteRepository;
import org.springframework.stereotype.Component;

@Component("ValidadorPacienteAtivo")
public class ValidadorPacienteAtivo implements ValidadorAgendamentoConsulta {

    private final PacienteRepository pacienteRepository;

    public ValidadorPacienteAtivo(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public void validar(DadosAgendamentoConsulta dados) {
        boolean pacienteEstaAtivo = pacienteRepository.findAtivoById(dados.idPaciente());
        if (!pacienteEstaAtivo) {
            throw new RuntimeException("O paciente não está ativo.");
        }
    }
}
