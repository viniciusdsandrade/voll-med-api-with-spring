package com.restful.api.repository;

import com.restful.api.entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository("consultaRepository")
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    /**
     * Verifica se existe uma consulta marcada para um paciente com o ID fornecido num intervalo de tempo especificado.
     *
     * @param idPaciente      O ID do paciente.
     * @param dataHoraInicio  A data e hora de início do intervalo.
     * @param dataHoraFim     A data e hora de término do intervalo.
     * @return True se existe uma consulta marcada para o paciente dentro do intervalo de tempo, caso contrário False.
     */
    boolean existsByPacienteIdAndDataHoraBetween(Long idPaciente, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim);

    /**
     * Verifica se existe uma consulta marcada para um médico com o ID fornecido na data e hora especificadas e se não foi cancelada.
     *
     * @param idMedico    O ID do médico.
     * @param dataHora    A data e hora da consulta.
     * @return True se existe uma consulta marcada para o médico na data e hora especificadas e se não foi cancelada, caso contrário False.
     */
    boolean existsByMedicoIdAndDataHoraAndMotivoCancelamentoIsNull(Long idMedico, LocalDateTime dataHora);

}


