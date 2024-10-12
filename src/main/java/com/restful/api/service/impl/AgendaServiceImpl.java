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
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service("agendaService")
public class AgendaServiceImpl implements AgendaService {

    private static final Logger logger = getLogger(AgendaServiceImpl.class);

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
    @Transactional
    public DadosDetalhamentoConsulta agendar(@Valid DadosAgendamentoConsulta dados) throws ValidacaoException {
        logger.info("Iniciando agendamento de consulta para o paciente ID: {}", dados.idPaciente());

        try {
            if (!pacienteRepository.existsById(dados.idPaciente())) {
                logger.warn("Agendamento de consulta falhou: Paciente não encontrado - ID {}", dados.idPaciente());
                throw new ValidacaoException("Paciente não encontrado");
            }

            if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
                logger.warn("Agendamento de consulta falhou: Médico não encontrado - ID {}", dados.idMedico());
                throw new ValidacaoException("Médico não encontrado");
            }

            logger.debug("Validando dados de agendamento para o paciente ID: {}", dados.idPaciente());
            validadoresAgendamento.forEach(validador -> validador.validar(dados));

            var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
            var medico = escolherMedico(dados);

            if (medico == null) {
                logger.warn("Agendamento de consulta falhou: Não há médicos disponíveis para a especialidade informada - Especialidade: {}", dados.especialidade());
                throw new ValidacaoException("Não há médicos disponíveis para a especialidade informada");
            }

            var consulta = new Consulta(null, medico, paciente, dados.dataHora(), null);
            consultaRepository.save(consulta);

            logger.info("Consulta agendada com sucesso: Consulta ID {}, Paciente ID {}, Médico ID {}",
                    consulta.getId(), paciente.getId(), medico.getId());

            return new DadosDetalhamentoConsulta(consulta);
        } catch (ValidacaoException e) {
            logger.error("Erro ao agendar consulta para o paciente ID {}: {}", dados.idPaciente(), e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Erro inesperado ao agendar consulta para o paciente ID {}: {}", dados.idPaciente(), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    public void cancelar(@Valid DadosCancelamentoConsulta dados) throws ValidacaoException {
        logger.info("Iniciando cancelamento de consulta: Consulta ID {}", dados.idConsulta());

        try {
            if (!consultaRepository.existsById(dados.idConsulta())) {
                logger.warn("Cancelamento de consulta falhou: Consulta não encontrada - ID {}", dados.idConsulta());
                throw new ValidacaoException("Id da consulta informado não encontrado");
            }

            logger.debug("Validando dados de cancelamento para a consulta ID: {}", dados.idConsulta());
            validadoresCancelamento.forEach(validador -> validador.validar(dados));

            var consulta = consultaRepository.getReferenceById(dados.idConsulta());
            consulta.cancelar(dados.motivoCancelamento());

            logger.info("Consulta cancelada com sucesso: Consulta ID {}, Motivo: {}",
                    dados.idConsulta(), dados.motivoCancelamento());
        } catch (ValidacaoException e) {
            logger.error("Erro ao cancelar consulta ID {}: {}", dados.idConsulta(), e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Erro inesperado ao cancelar consulta ID {}: {}", dados.idConsulta(), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Consulta buscarPorId(Long id) {
        logger.debug("Buscando consulta por ID: {}", id);
        Consulta consulta = consultaRepository.getReferenceById(id);
        logger.debug("Consulta encontrada: ID {}", id);
        return consulta;
    }

    @Override
    public Page<DadosDetalhamentoConsulta> listar(Pageable paginacao) {
        logger.debug("Listando consultas com paginação: Página {}, Tamanho {}", paginacao.getPageNumber(), paginacao.getPageSize());

        Page<Consulta> consultas = consultaRepository.findAll(paginacao);
        Page<DadosDetalhamentoConsulta> resultado = consultas.map(DadosDetalhamentoConsulta::new);

        logger.debug("Total de consultas listadas: {}", resultado.getTotalElements());

        return resultado;
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() != null)
            return medicoRepository.getReferenceById(dados.idMedico());

        if (dados.especialidade() == null)
            throw new ValidacaoException("É necessário informar a especialidade quando o médico não for escolhido");

        return medicoRepository.findFirstByEspecialidadeAndAtivoAndIdNotIn(dados.especialidade(), dados.dataHora());
    }
}
