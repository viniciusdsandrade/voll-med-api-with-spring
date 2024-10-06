package com.restful.api.service.impl;

import com.restful.api.dto.paciente.DadosAtualizacaoPaciente;
import com.restful.api.dto.paciente.DadosCadastroPaciente;
import com.restful.api.dto.paciente.DadosListagemPaciente;
import com.restful.api.entity.Paciente;
import com.restful.api.exception.DuplicateEntryException;
import com.restful.api.repository.PacienteRepository;
import com.restful.api.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("pacienteService")
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteServiceImpl(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    @Transactional
    public Paciente cadastrar(@Valid DadosCadastroPaciente dadosCadastroPaciente) {

        if (pacienteRepository.existsByEmail(dadosCadastroPaciente.email()))
            throw new DuplicateEntryException("Já existe um paciente cadastrado com o email informado.");

        if (pacienteRepository.existsByCpf(dadosCadastroPaciente.cpf()))
            throw new DuplicateEntryException("Já existe um paciente cadastrado com o CPF informado.");

        Paciente paciente = new Paciente(dadosCadastroPaciente);
        pacienteRepository.save(paciente);
        return paciente;
    }

    @Override
    @Transactional
    public Paciente atualizar(@Valid DadosAtualizacaoPaciente dadosAtualizacaoPaciente) {
        Paciente paciente = pacienteRepository.getReferenceById(dadosAtualizacaoPaciente.id());
        paciente.atualizarInformacoes(dadosAtualizacaoPaciente);
        return paciente;
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        Paciente paciente = pacienteRepository.getReferenceById(id);
        paciente.excluir();
    }

    @Override
    public Paciente buscarPorId(Long id) {
        return pacienteRepository.getReferenceById(id);
    }

    @Override
    public Page<DadosListagemPaciente> listar(Pageable paginacao) {
        Page<Paciente> pacientes = pacienteRepository.findAllByAtivoTrue(paginacao);
        return pacientes.map(DadosListagemPaciente::new);
    }
}
