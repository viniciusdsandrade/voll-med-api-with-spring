package com.restful.api.service.impl;

import com.restful.api.dto.consulta.DadosAgendamentoConsulta;
import com.restful.api.dto.consulta.DadosCancelamentoConsulta;
import com.restful.api.dto.consulta.DadosDetalhamentoConsulta;
import com.restful.api.entity.Consulta;
import com.restful.api.entity.Medico;
import com.restful.api.entity.validation.agendamento.ValidadorAgendamentoConsulta;
import com.restful.api.entity.validation.cancelamento.ValidadorCancelamentoConsulta;
import com.restful.api.exception.ValidacaoException;
import com.restful.api.repository.ConsultaRepository;
import com.restful.api.repository.MedicoRepository;
import com.restful.api.repository.PacienteRepository;
import com.restful.api.service.AgendaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("agendaService")
public class AgendaServiceImpl implements AgendaService {

    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    private final List<ValidadorAgendamentoConsulta> validadoresAgendamento;
    private final List<ValidadorCancelamentoConsulta> validadoresCancelamento;

    public AgendaServiceImpl(ConsultaRepository consultaRepository,
                             MedicoRepository medicoRepository,
                             PacienteRepository pacienteRepository,
                             List<ValidadorAgendamentoConsulta> validadoresAgendamento,
                             List<ValidadorCancelamentoConsulta> validadoresCancelamento) {
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
        this.validadoresAgendamento = validadoresAgendamento;
        this.validadoresCancelamento = validadoresCancelamento;
    }

    @Override
    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) throws ValidacaoException {

        if (!pacienteRepository.existsById(dados.idPaciente()))
            throw new ValidacaoException("Paciente não encontrado");

        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico()))
            throw new ValidacaoException("Médico não encontrado");

        validadoresAgendamento.forEach(validador -> validador.validar(dados));

        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var medico = escolherMedico(dados);

        if (medico == null)
            throw new ValidacaoException("Não há médicos disponíveis para a especialidade informada");

        var consulta = new Consulta(null, medico, paciente, dados.dataHora(), null);
        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    @Override
    public void cancelar(DadosCancelamentoConsulta dados) throws ValidacaoException {
        if (!consultaRepository.existsById(dados.idConsulta()))
            throw new ValidacaoException("Id da consulta informado não encontrado");

        validadoresCancelamento.forEach(validador -> validador.validar(dados));

        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivoCancelamento());
    }

    @Override
    public Consulta buscarPorId(Long id) {
        return consultaRepository.getReferenceById(id);
    }


    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() != null)
            return medicoRepository.getReferenceById(dados.idMedico());

        if (dados.especialidade() == null)
            throw new ValidacaoException("É necessário informar a especialidade quando o médico não for escolhido");

        return medicoRepository.findFirstByEspecialidadeAndAtivoAndIdNotIn(dados.especialidade(), dados.dataHora());
    }

    @Override
    public Page<DadosDetalhamentoConsulta> listar(Pageable paginacao) {
        Page<Consulta> consultas = consultaRepository.findAll(paginacao);
        return consultas.map(DadosDetalhamentoConsulta::new);
    }
}
