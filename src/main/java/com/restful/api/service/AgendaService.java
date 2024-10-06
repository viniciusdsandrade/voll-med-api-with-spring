package com.restful.api.service;

import com.restful.api.dto.consulta.DadosAgendamentoConsulta;
import com.restful.api.dto.consulta.DadosCancelamentoConsulta;
import com.restful.api.dto.consulta.DadosDetalhamentoConsulta;
import com.restful.api.entity.Consulta;
import com.restful.api.exception.ValidacaoException;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Schema(name = "AgendaService")
public interface AgendaService {

    DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) throws ValidacaoException;

    Page<DadosDetalhamentoConsulta> listar(Pageable paginacao);

    void cancelar(DadosCancelamentoConsulta dados) throws ValidacaoException;

    Consulta buscarPorId(Long id);
}
