package com.restful.api.service;

import com.restful.api.dto.paciente.DadosAtualizacaoPaciente;
import com.restful.api.dto.paciente.DadosCadastroPaciente;
import com.restful.api.dto.paciente.DadosListagemPaciente;
import com.restful.api.entity.Paciente;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Schema(name = "PacienteService")
public interface PacienteService {

    @Transactional
    Paciente cadastrar(@Valid DadosCadastroPaciente dadosCadastroPaciente);

    @Transactional
    Paciente atualizar(@Valid DadosAtualizacaoPaciente dadosAtualizacaoPaciente);

    @Transactional
    void excluir(Long id);

    Paciente buscarPorId(Long id);

    Page<DadosListagemPaciente> listar(Pageable paginacao);
}
