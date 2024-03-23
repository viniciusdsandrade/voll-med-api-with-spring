package com.restful.api.entity.validation.agendamento;

import com.restful.api.dto.consulta.DadosAgendamentoConsulta;
import com.restful.api.exception.ValidacaoException;
import com.restful.api.repository.ConsultaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component("ValidadorPacienteSemOutraConsultaNoDia")
public class ValidadorPacienteSemOutraConsultaNoDia implements ValidadorAgendamentoConsulta {

    private final ConsultaRepository consultaRepository;

    public ValidadorPacienteSemOutraConsultaNoDia(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    public void validar(DadosAgendamentoConsulta dados) {
        LocalDateTime primeiroHorarioDaClinica = dados.dataHora().withHour(7);
        LocalDateTime ultimoHorarioDaClinica = dados.dataHora().withHour(18);

        boolean pacientePossuiOutraConsultaNoDia = consultaRepository.existsByPacienteIdAndDataHoraBetween(
                dados.idPaciente(),
                primeiroHorarioDaClinica,
                ultimoHorarioDaClinica
        );

        if (pacientePossuiOutraConsultaNoDia) {
            throw new ValidacaoException("Paciente já possui consulta marcada para este dia");
        }
    }
}
