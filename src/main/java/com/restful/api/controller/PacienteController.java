package com.restful.api.controller;

import com.restful.api.dto.paciente.DadosAtualizacaoPaciente;
import com.restful.api.dto.paciente.DadosCadastroPaciente;
import com.restful.api.dto.paciente.DadosDetalhamentoPaciente;
import com.restful.api.dto.paciente.DadosListagemPaciente;
import com.restful.api.entity.Paciente;
import com.restful.api.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
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

@RestController
@RequestMapping("/api/v1/pacientes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Paciente Controller", description = "Controller para gerenciamento de pacientes")
@Schema(description = "Controller para gerenciamento de pacientes.")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    @Transactional
    @Operation(
            summary = "Cadastrar um novo paciente",
            description = "Endpoint para cadastrar um novo paciente no sistema."
    )
    @ApiResponse(responseCode = "201", description = "Paciente cadastrado com sucesso.")
    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos.")
    public ResponseEntity<DadosDetalhamentoPaciente> cadastrar(
            @RequestBody @Valid DadosCadastroPaciente dados,
            UriComponentsBuilder uriBuilder
    ) {
        Paciente saved = pacienteService.cadastrar(dados);
        URI uri = uriBuilder.path("/api/v1/pacientes/{id}").buildAndExpand(saved.getId()).toUri();
        return created(uri).body(new DadosDetalhamentoPaciente(saved));
    }

    @GetMapping
    @Operation(
            summary = "Listar pacientes",
            description = "Retorna uma lista paginada de pacientes cadastrados no sistema."
    )
    @ApiResponse(responseCode = "200", description = "Lista de pacientes retornada com sucesso.")
    public ResponseEntity<Page<DadosListagemPaciente>> listar(
            @PageableDefault(size = 5, sort = {"nome"}) Pageable paginacao
    ) {
        Page<DadosListagemPaciente> pacientes = pacienteService.listar(paginacao);
        return ok(pacientes);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Detalhar paciente",
            description = "Retorna os detalhes de um paciente específico pelo seu ID."
    )
    @ApiResponse(responseCode = "200", description = "Detalhes do paciente retornados com sucesso.")
    @ApiResponse(responseCode = "404", description = "Paciente não encontrado.")
    public ResponseEntity<DadosDetalhamentoPaciente> detalhar(@PathVariable Long id) {
        Paciente paciente = pacienteService.buscarPorId(id);
        return ok(new DadosDetalhamentoPaciente(paciente));
    }

    @PutMapping
    @Transactional
    @Operation(
            summary = "Atualizar paciente",
            description = "Atualiza as informações de um paciente existente."
    )
    @ApiResponse(responseCode = "200", description = "Paciente atualizado com sucesso.")
    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos.")
    @ApiResponse(responseCode = "404", description = "Paciente não encontrado.")
    public ResponseEntity<DadosDetalhamentoPaciente> atualizar(
            @RequestBody @Valid DadosAtualizacaoPaciente dados
    ) {
        Paciente paciente = pacienteService.atualizar(dados);
        return ok(new DadosDetalhamentoPaciente(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(
            summary = "Excluir paciente",
            description = "Remove um paciente do sistema pelo seu ID."
    )
    @ApiResponse(responseCode = "204", description = "Paciente excluído com sucesso.")
    @ApiResponse(responseCode = "404", description = "Paciente não encontrado.")
    public ResponseEntity<Void> deletarPaciente(@PathVariable Long id) {
        pacienteService.excluir(id);
        return noContent().build();
    }
}
