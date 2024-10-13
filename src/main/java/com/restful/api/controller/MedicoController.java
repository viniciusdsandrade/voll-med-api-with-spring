package com.restful.api.controller;

import com.restful.api.dto.medico.DadosAtualizacaoMedico;
import com.restful.api.dto.medico.DadosCadastroMedico;
import com.restful.api.dto.medico.DadosDetalhamentoMedico;
import com.restful.api.dto.medico.DadosListagemMedico;
import com.restful.api.entity.Medico;
import com.restful.api.service.MedicoService;
import io.swagger.v3.oas.annotations.Operation;
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

/**
 * Controller responsável pelo gerenciamento de médicos.
 * <p>
 * Este controller fornece endpoints para criar, atualizar, excluir e listar médicos cadastrados no sistema.
 */
@RestController
@RequestMapping("/api/v1/medicos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Medico Controller", description = "Controller para gerenciamento de médicos")
public class MedicoController {

    private final MedicoService medicoService;

    /**
     * Construtor que injeta o serviço de médicos necessário para as operações do controller.
     *
     * @param medicoService Serviço que gerencia a lógica de negócios dos médicos.
     */
    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    /**
     * Endpoint para cadastrar um novo médico no sistema.
     *
     * @param dados      Os dados de cadastro do médico.
     * @param uriBuilder Objeto usado para construir a URI de localização do novo recurso.
     * @return Um {@link ResponseEntity} com os detalhes do médico cadastrado e a URI de acesso.
     */
    @PostMapping
    @Transactional
    @Operation(
            summary = "Cadastrar um novo médico",
            description = "Endpoint para cadastrar um novo médico no sistema.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Médico cadastrado com sucesso."),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos.")
            }
    )
    public ResponseEntity<DadosDetalhamentoMedico> cadastrar(
            @RequestBody @Valid DadosCadastroMedico dados,
            UriComponentsBuilder uriBuilder
    ) {
        Medico saved = medicoService.cadastrar(dados);
        URI uri = uriBuilder.path("/api/v1/medicos/{id}").buildAndExpand(saved.getId()).toUri();
        return created(uri).body(new DadosDetalhamentoMedico(saved));
    }

    /**
     * Endpoint para listar médicos cadastrados no sistema.
     *
     * @param paginacao Parâmetros de paginação e ordenação.
     * @return Uma lista paginada de médicos cadastrados.
     */
    @GetMapping
    @Operation(
            summary = "Listar médicos",
            description = "Retorna uma lista paginada de médicos cadastrados no sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de médicos retornada com sucesso.")
            }
    )
    public ResponseEntity<Page<DadosListagemMedico>> listar(
            @PageableDefault(size = 5, sort = {"nome"}) Pageable paginacao
    ) {
        Page<DadosListagemMedico> medicos = medicoService.listar(paginacao);
        return ok(medicos);
    }

    /**
     * Endpoint para obter os detalhes de um médico específico pelo ID.
     *
     * @param id O ID do médico.
     * @return Os detalhes do médico correspondente ao ID.
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Detalhar médico",
            description = "Retorna os detalhes de um médico específico pelo seu ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Detalhes do médico retornados com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Médico não encontrado.")
            }
    )
    public ResponseEntity<DadosDetalhamentoMedico> detalhar(@PathVariable Long id) {
        Medico medico = medicoService.buscarPorId(id);
        return ok(new DadosDetalhamentoMedico(medico));
    }

    /**
     * Endpoint para atualizar as informações de um médico existente.
     *
     * @param dados Os dados de atualização do médico.
     * @return Os detalhes do médico atualizado.
     */
    @PutMapping
    @Transactional
    @Operation(
            summary = "Atualizar médico",
            description = "Atualiza as informações de um médico existente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Médico atualizado com sucesso."),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos."),
                    @ApiResponse(responseCode = "404", description = "Médico não encontrado.")
            }
    )
    public ResponseEntity<DadosDetalhamentoMedico> atualizar(
            @RequestBody @Valid DadosAtualizacaoMedico dados
    ) {
        Medico medico = medicoService.atualizar(dados);
        return ok(new DadosDetalhamentoMedico(medico));
    }

    /**
     * Endpoint para excluir um médico do sistema pelo ID.
     *
     * @param id O ID do médico a ser excluído.
     * @return Um {@link ResponseEntity} indicando que o médico foi excluído com sucesso.
     */
    @DeleteMapping("/{id}")
    @Transactional
    @Operation(
            summary = "Excluir médico",
            description = "Remove um médico do sistema pelo seu ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Médico excluído com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Médico não encontrado.")
            }
    )
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        medicoService.excluir(id);
        return noContent().build();
    }
}
