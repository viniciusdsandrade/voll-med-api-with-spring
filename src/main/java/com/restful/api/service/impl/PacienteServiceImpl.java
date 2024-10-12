package com.restful.api.service.impl;

import com.restful.api.dto.paciente.DadosAtualizacaoPaciente;
import com.restful.api.dto.paciente.DadosCadastroPaciente;
import com.restful.api.dto.paciente.DadosListagemPaciente;
import com.restful.api.entity.Paciente;
import com.restful.api.exception.DuplicateEntryException;
import com.restful.api.repository.PacienteRepository;
import com.restful.api.service.PacienteService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.slf4j.LoggerFactory.getLogger;

@Service("pacienteService")
public class PacienteServiceImpl implements PacienteService {

    private static final Logger logger = getLogger(PacienteServiceImpl.class);

    private final PacienteRepository pacienteRepository;

    public PacienteServiceImpl(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    @Transactional
    public Paciente cadastrar(@Valid DadosCadastroPaciente dadosCadastroPaciente) {
        logger.info("Iniciando cadastro de paciente com email: {}", dadosCadastroPaciente.email());

        if (pacienteRepository.existsByEmail(dadosCadastroPaciente.email())) {
            logger.warn("Cadastro de paciente falhou: Email já existente - {}", dadosCadastroPaciente.email());
            throw new DuplicateEntryException("Já existe um paciente cadastrado com o email informado.");
        }

        if (pacienteRepository.existsByCpf(dadosCadastroPaciente.cpf())) {
            logger.warn("Cadastro de paciente falhou: CPF já existente - {}", dadosCadastroPaciente.cpf());
            throw new DuplicateEntryException("Já existe um paciente cadastrado com o CPF informado.");
        }

        Paciente paciente = new Paciente(dadosCadastroPaciente);
        pacienteRepository.save(paciente);

        logger.info("Paciente cadastrado com sucesso: ID {}", paciente.getId());

        return paciente;
    }

    @Override
    @Transactional
    public Paciente atualizar(@Valid DadosAtualizacaoPaciente dadosAtualizacaoPaciente) {
        logger.info("Iniciando atualização de paciente: ID {}", dadosAtualizacaoPaciente.id());

        Paciente paciente = pacienteRepository.getReferenceById(dadosAtualizacaoPaciente.id());
        paciente.atualizarInformacoes(dadosAtualizacaoPaciente);

        logger.info("Paciente atualizado com sucesso: ID {}", paciente.getId());

        return paciente;
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        logger.info("Iniciando exclusão de paciente: ID {}", id);

        Paciente paciente = pacienteRepository.getReferenceById(id);
        paciente.excluir();

        logger.info("Paciente excluído com sucesso: ID {}", id);
    }


    @Override
    public Paciente buscarPorId(Long id) {
        logger.debug("Buscando paciente por ID: {}", id);
        Paciente paciente = pacienteRepository.getReferenceById(id);
        logger.debug("Paciente encontrado: ID {}", id);
        return paciente;
    }

    @Override
    public Page<DadosListagemPaciente> listar(Pageable paginacao) {
        logger.debug("Listando pacientes com paginação: Página {}, Tamanho {}", paginacao.getPageNumber(), paginacao.getPageSize());

        Page<Paciente> pacientes = pacienteRepository.findAllByAtivoTrue(paginacao);
        Page<DadosListagemPaciente> resultado = pacientes.map(DadosListagemPaciente::new);

        logger.debug("Total de pacientes listados: {}", resultado.getTotalElements());

        return resultado;
    }
}
