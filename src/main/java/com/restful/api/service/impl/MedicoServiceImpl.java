package com.restful.api.service.impl;

import com.restful.api.dto.medico.DadosAtualizacaoMedico;
import com.restful.api.dto.medico.DadosCadastroMedico;
import com.restful.api.dto.medico.DadosListagemMedico;
import com.restful.api.exception.DuplicateEntryException;
import jakarta.validation.Valid;
import com.restful.api.entity.Medico;
import com.restful.api.repository.MedicoRepository;
import com.restful.api.service.MedicoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("medicoService")
public class MedicoServiceImpl implements MedicoService {

    private final MedicoRepository medicoRepository;

    public MedicoServiceImpl(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    @Override
    @Transactional
    public Medico cadastrar(@Valid DadosCadastroMedico dadosCadastroMedico) {

        if (medicoRepository.existsByEmail(dadosCadastroMedico.email()))
            throw new DuplicateEntryException("Já existe um médico cadastrado com o email informado.");

        if (medicoRepository.existsByCrm(dadosCadastroMedico.crm()))
            throw new DuplicateEntryException("Já existe um médico cadastrado com o CRM informado.");

        Medico medico = new Medico(dadosCadastroMedico);
        medicoRepository.save(medico);
        return medico;
    }

    @Override
    @Transactional
    public Medico atualizar(@Valid DadosAtualizacaoMedico dadosAtualizacaoMedico) {
        Medico medico = medicoRepository.getReferenceById(dadosAtualizacaoMedico.id());
        medico.atualizarInformacoes(dadosAtualizacaoMedico);
        return medico;
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        medico.excluir();
    }

    @Override
    public Medico buscarPorId(Long id) {
        return medicoRepository.getReferenceById(id);
    }

    @Override
    public Page<DadosListagemMedico> listar(Pageable paginacao) {
        Page<Medico> medicos = medicoRepository.findAllByAtivoTrue(paginacao);
        return medicos.map(DadosListagemMedico::new);
    }
}
