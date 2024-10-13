package com.restful.api.controller;

import com.restful.api.dto.consulta.DadosAgendamentoConsulta;
import com.restful.api.dto.consulta.DadosCancelamentoConsulta;
import com.restful.api.dto.consulta.DadosDetalhamentoConsulta;
import com.restful.api.service.AgendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

/**
 * Controller responsável pelo gerenciamento de consultas.
 * <p>
 * Este controller fornece endpoints para agendar, cancelar, listar e detalhar consultas no sistema.
 */
@RestController
@RequestMapping("/api/v1/consulta")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Consulta Controller", description = "Controller para gerenciamento de consultas")
public class ConsultaController {

    private final AgendaService agendaService;

    /**
     * Construtor que injeta o serviço de agenda necessário para as operações do controller.
     *
     * @param agendaService Serviço que gerencia a lógica de negócios das consultas.
     */
    public ConsultaController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    /**
     * Endpoint para agendar uma nova consulta no sistema.
     *
     * @param dados      Os dados de agendamento da consulta.
     * @param uriBuilder Objeto usado para construir a URI de localização do novo recurso.
     * @return Um {@link ResponseEntity} com os detalhes da consulta agendada e a URI de acesso.
     */
    @PostMapping
    @Transactional
    @Operation(
            summary = "Agendar uma nova consulta",
            description = "Endpoint para agendar uma nova consulta no sistema.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Consulta agendada com sucesso."),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos.")
            }
    )
    public ResponseEntity<DadosDetalhamentoConsulta> agendar(
            @RequestBody @Valid DadosAgendamentoConsulta dados,
            UriComponentsBuilder uriBuilder
    ) {
        var consulta = agendaService.agendar(dados);
        URI uri = uriBuilder.path("/api/v1/consulta/{id}").buildAndExpand(consulta.id()).toUri();
        return created(uri).body(new DadosDetalhamentoConsulta(consulta));
    }

    /**
     * Endpoint para cancelar uma consulta existente no sistema.
     *
     * @param dados Os dados de cancelamento da consulta.
     * @return Um {@link ResponseEntity} indicando que a consulta foi cancelada com sucesso.
     */
    @DeleteMapping
    @Transactional
    @Operation(
            summary = "Cancelar uma consulta",
            description = "Endpoint para cancelar uma consulta existente no sistema.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Consulta cancelada com sucesso."),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos."),
                    @ApiResponse(responseCode = "404", description = "Consulta não encontrada.")
            }
    )
    public ResponseEntity<Void> cancelar(
            @RequestBody @Valid DadosCancelamentoConsulta dados
    ) {
        agendaService.cancelar(dados);
        return noContent().build();
    }

    /**
     * Endpoint para listar as consultas agendadas no sistema.
     *
     * @param paginacao Parâmetros de paginação e ordenação.
     * @return Uma lista paginada de consultas agendadas.
     */
    @GetMapping
    @Operation(
            summary = "Listar consultas",
            description = "Retorna uma lista paginada de consultas agendadas no sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de consultas retornada com sucesso.")
            }
    )
    public ResponseEntity<Page<DadosDetalhamentoConsulta>> listar(
            @PageableDefault(size = 5, sort = {"dataHora"}) Pageable paginacao
    ) {
        Page<DadosDetalhamentoConsulta> consultas = agendaService.listar(paginacao);
        return ok(consultas);
    }

    /**
     * Endpoint para obter os detalhes de uma consulta específica pelo ID.
     *
     * @param id O ID da consulta.
     * @return Os detalhes da consulta correspondente ao ID.
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Detalhar consulta",
            description = "Retorna os detalhes de uma consulta específica pelo seu ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Detalhes da consulta retornados com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Consulta não encontrada.")
            }
    )
    public ResponseEntity<DadosDetalhamentoConsulta> detalhar(@PathVariable Long id) {
        var consulta = agendaService.buscarPorId(id);
        return ok(new DadosDetalhamentoConsulta(consulta));
    }
}
