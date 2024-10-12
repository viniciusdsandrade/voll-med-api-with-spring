package com.restful.api.service;

import com.restful.api.dto.consulta.DadosAgendamentoConsulta;
import com.restful.api.dto.consulta.DadosCancelamentoConsulta;
import com.restful.api.dto.consulta.DadosDetalhamentoConsulta;
import com.restful.api.entity.Consulta;
import com.restful.api.exception.ValidacaoException;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Schema(name = "AgendaService")
public interface AgendaService {

    @Transactional
    DadosDetalhamentoConsulta agendar(@Valid DadosAgendamentoConsulta dados) throws ValidacaoException;

    Page<DadosDetalhamentoConsulta> listar(Pageable paginacao);

    @Transactional
    void cancelar(@Valid DadosCancelamentoConsulta dados) throws ValidacaoException;

    Consulta buscarPorId(Long id);
}
