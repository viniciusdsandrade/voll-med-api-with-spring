package com.restful.api.controller;

import com.restful.api.dto.medico.DadosAtualizacaoMedico;
import com.restful.api.dto.medico.DadosCadastroMedico;
import com.restful.api.dto.medico.DadosDetalhamentoMedico;
import com.restful.api.dto.medico.DadosListagemMedico;
import com.restful.api.entity.Medico;
import com.restful.api.service.MedicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.created;

@RestController
@RequestMapping("/api/v1/medicos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Medico Controller", description = "Controller para gerenciamento de médicos")
@Schema(description = "Controller para gerenciamento de médicos.")
public class MedicoController {

    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @PostMapping
    @Transactional
    @Operation(
            summary = "Cadastrar um novo médico",
            description = "Endpoint para cadastrar um novo médico no sistema."
    )
    @ApiResponse(responseCode = "201", description = "Médico cadastrado com sucesso.")
    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos.")
    public ResponseEntity<DadosDetalhamentoMedico> cadastrar(
            @RequestBody @Valid DadosCadastroMedico dados,
            UriComponentsBuilder uriBuilder
    ) {
        Medico saved = medicoService.cadastrar(dados);
        URI uri = uriBuilder.path("/api/v1/medicos/{id}").buildAndExpand(saved.getId()).toUri();
        return created(uri).body(new DadosDetalhamentoMedico(saved));
    }

    @GetMapping
    @Operation(
            summary = "Listar médicos",
            description = "Retorna uma lista paginada de médicos cadastrados no sistema."
    )
    @ApiResponse(responseCode = "200", description = "Lista de médicos retornada com sucesso.")
    public ResponseEntity<Page<DadosListagemMedico>> listar(
            @PageableDefault(size = 5, sort = {"nome"}) Pageable paginacao
    ) {
        Page<DadosListagemMedico> medicos = medicoService.listar(paginacao);
        return ok(medicos);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Detalhar médico",
            description = "Retorna os detalhes de um médico específico pelo seu ID."
    )
    @ApiResponse(responseCode = "200", description = "Detalhes do médico retornados com sucesso.")
    @ApiResponse(responseCode = "404", description = "Médico não encontrado.")
    public ResponseEntity<DadosDetalhamentoMedico> detalhar(@PathVariable Long id) {
        Medico medico = medicoService.buscarPorId(id);
        return ok(new DadosDetalhamentoMedico(medico));
    }

    @PutMapping
    @Transactional
    @Operation(
            summary = "Atualizar médico",
            description = "Atualiza as informações de um médico existente."
    )
    @ApiResponse(responseCode = "200", description = "Médico atualizado com sucesso.")
    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos.")
    @ApiResponse(responseCode = "404", description = "Médico não encontrado.")
    public ResponseEntity<DadosDetalhamentoMedico> atualizar(
            @RequestBody @Valid DadosAtualizacaoMedico dados
    ) {
        Medico medico = medicoService.atualizar(dados);
        return ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(
            summary = "Excluir médico",
            description = "Remove um médico do sistema pelo seu ID."
    )
    @ApiResponse(responseCode = "204", description = "Médico excluído com sucesso.")
    @ApiResponse(responseCode = "404", description = "Médico não encontrado.")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        medicoService.excluir(id);
        return noContent().build();
    }
}