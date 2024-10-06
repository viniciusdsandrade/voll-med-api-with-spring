package com.restful.api.service;

import com.restful.api.dto.medico.DadosAtualizacaoMedico;
import com.restful.api.dto.medico.DadosCadastroMedico;
import com.restful.api.dto.medico.DadosListagemMedico;
import com.restful.api.entity.Medico;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Schema(name = "MedicoService")
public interface MedicoService {

    @Transactional
    Medico cadastrar(@Valid DadosCadastroMedico dadosCadastroMedico);

    @Transactional
    Medico atualizar(@Valid DadosAtualizacaoMedico dadosAtualizacaoMedico);

    @Transactional
    void excluir(Long id);

    Medico buscarPorId(Long id);

    Page<DadosListagemMedico> listar(Pageable paginacao);
}
