package com.restful.api.service.impl;

import com.restful.api.dto.medico.DadosAtualizacaoMedico;
import com.restful.api.dto.medico.DadosCadastroMedico;
import com.restful.api.dto.medico.DadosListagemMedico;
import com.restful.api.exception.DuplicateEntryException;
import jakarta.validation.Valid;
import com.restful.api.entity.Medico;
import com.restful.api.repository.MedicoRepository;
import com.restful.api.service.MedicoService;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.slf4j.LoggerFactory.getLogger;

@Service("medicoService")
public class MedicoServiceImpl implements MedicoService {

    private static final Logger logger = getLogger(MedicoServiceImpl.class);
    private final MedicoRepository medicoRepository;

    public MedicoServiceImpl(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    @Override
    @Transactional
    public Medico cadastrar(@Valid DadosCadastroMedico dadosCadastroMedico) {
        logger.info("Iniciando cadastro de médico com email: {}", dadosCadastroMedico.email());

        if (medicoRepository.existsByEmail(dadosCadastroMedico.email())) {
            logger.warn("Cadastro de médico falhou: Email já existente - {}", dadosCadastroMedico.email());
            throw new DuplicateEntryException("Já existe um médico cadastrado com o email informado.");
        }

        if (medicoRepository.existsByCrm(dadosCadastroMedico.crm())) {
            logger.warn("Cadastro de médico falhou: CRM já existente - {}", dadosCadastroMedico.crm());
            throw new DuplicateEntryException("Já existe um médico cadastrado com o CRM informado.");
        }

        Medico medico = new Medico(dadosCadastroMedico);
        medicoRepository.save(medico);

        logger.info("Médico cadastrado com sucesso: ID {}", medico.getId());

        return medico;
    }

    @Override
    @Transactional
    public Medico atualizar(@Valid DadosAtualizacaoMedico dadosAtualizacaoMedico) {
        logger.info("Iniciando atualização de médico: ID {}", dadosAtualizacaoMedico.id());

        Medico medico = medicoRepository.getReferenceById(dadosAtualizacaoMedico.id());
        medico.atualizarInformacoes(dadosAtualizacaoMedico);

        logger.info("Médico atualizado com sucesso: ID {}", medico.getId());

        return medico;
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        logger.info("Iniciando exclusão de médico: ID {}", id);

        Medico medico = medicoRepository.getReferenceById(id);
        medico.excluir();

        logger.info("Médico excluído com sucesso: ID {}", id);
    }

    @Override
    public Medico buscarPorId(Long id) {
        logger.debug("Buscando médico por ID: {}", id);
        Medico medico = medicoRepository.getReferenceById(id);
        logger.debug("Médico encontrado: ID {}", id);
        return medico;
    }

    @Override
    public Page<DadosListagemMedico> listar(Pageable paginacao) {
        logger.debug("Listando médicos com paginação: Página {}, Tamanho {}", paginacao.getPageNumber(), paginacao.getPageSize());

        Page<Medico> medicos = medicoRepository.findAllByAtivoTrue(paginacao);
        Page<DadosListagemMedico> resultado = medicos.map(DadosListagemMedico::new);

        logger.debug("Total de médicos listados: {}", resultado.getTotalElements());

        return resultado;
    }
}
