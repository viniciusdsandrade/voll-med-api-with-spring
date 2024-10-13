package com.restful.api.controller;

import com.restful.api.dto.paciente.DadosAtualizacaoPaciente;
import com.restful.api.dto.paciente.DadosCadastroPaciente;
import com.restful.api.dto.paciente.DadosDetalhamentoPaciente;
import com.restful.api.dto.paciente.DadosListagemPaciente;
import com.restful.api.entity.Paciente;
import com.restful.api.service.PacienteService;
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
 * Controller responsável pelo gerenciamento de pacientes.
 * <p>
 * Este controller fornece endpoints para criar, atualizar, excluir e listar pacientes cadastrados no sistema.
 */
@RestController
@RequestMapping("/api/v1/pacientes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Paciente Controller", description = "Controller para gerenciamento de pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    /**
     * Construtor que injeta o serviço de pacientes necessário para as operações do controller.
     *
     * @param pacienteService Serviço que gerencia a lógica de negócios dos pacientes.
     */
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    /**
     * Endpoint para cadastrar um novo paciente no sistema.
     *
     * @param dados      Os dados de cadastro do paciente.
     * @param uriBuilder Objeto usado para construir a URI de localização do novo recurso.
     * @return Um {@link ResponseEntity} com os detalhes do paciente cadastrado e a URI de acesso.
     */
    @PostMapping
    @Transactional
    @Operation(
            summary = "Cadastrar um novo paciente",
            description = "Endpoint para cadastrar um novo paciente no sistema.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Paciente cadastrado com sucesso."),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos.")
            }
    )
    public ResponseEntity<DadosDetalhamentoPaciente> cadastrar(
            @RequestBody @Valid DadosCadastroPaciente dados,
            UriComponentsBuilder uriBuilder
    ) {
        Paciente saved = pacienteService.cadastrar(dados);
        URI uri = uriBuilder.path("/api/v1/pacientes/{id}").buildAndExpand(saved.getId()).toUri();
        return created(uri).body(new DadosDetalhamentoPaciente(saved));
    }

    /**
     * Endpoint para listar pacientes cadastrados no sistema.
     *
     * @param paginacao Parâmetros de paginação e ordenação.
     * @return Uma lista paginada de pacientes cadastrados.
     */
    @GetMapping
    @Operation(
            summary = "Listar pacientes",
            description = "Retorna uma lista paginada de pacientes cadastrados no sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de pacientes retornada com sucesso.")
            }
    )
    public ResponseEntity<Page<DadosListagemPaciente>> listar(
            @PageableDefault(size = 5, sort = {"nome"}) Pageable paginacao
    ) {
        Page<DadosListagemPaciente> pacientes = pacienteService.listar(paginacao);
        return ok(pacientes);
    }

    /**
     * Endpoint para obter os detalhes de um paciente específico pelo ID.
     *
     * @param id O ID do paciente.
     * @return Os detalhes do paciente correspondente ao ID.
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Detalhar paciente",
            description = "Retorna os detalhes de um paciente específico pelo seu ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Detalhes do paciente retornados com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Paciente não encontrado.")
            }
    )
    public ResponseEntity<DadosDetalhamentoPaciente> detalhar(@PathVariable Long id) {
        Paciente paciente = pacienteService.buscarPorId(id);
        return ok(new DadosDetalhamentoPaciente(paciente));
    }

    /**
     * Endpoint para atualizar as informações de um paciente existente.
     *
     * @param dados Os dados de atualização do paciente.
     * @return Os detalhes do paciente atualizado.
     */
    @PutMapping
    @Transactional
    @Operation(
            summary = "Atualizar paciente",
            description = "Atualiza as informações de um paciente existente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Paciente atualizado com sucesso."),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos."),
                    @ApiResponse(responseCode = "404", description = "Paciente não encontrado.")
            }
    )
    public ResponseEntity<DadosDetalhamentoPaciente> atualizar(
            @RequestBody @Valid DadosAtualizacaoPaciente dados
    ) {
        Paciente paciente = pacienteService.atualizar(dados);
        return ok(new DadosDetalhamentoPaciente(paciente));
    }

    /**
     * Endpoint para excluir um paciente do sistema pelo ID.
     *
     * @param id O ID do paciente a ser excluído.
     * @return Um {@link ResponseEntity} indicando que o paciente foi excluído com sucesso.
     */
    @DeleteMapping("/{id}")
    @Transactional
    @Operation(
            summary = "Excluir paciente",
            description = "Remove um paciente do sistema pelo seu ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Paciente excluído com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Paciente não encontrado.")
            }
    )
    public ResponseEntity<Void> deletarPaciente(@PathVariable Long id) {
        pacienteService.excluir(id);
        return noContent().build();
    }
}
