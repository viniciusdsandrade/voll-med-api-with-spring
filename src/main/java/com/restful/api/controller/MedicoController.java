package com.restful.api.controller;

import com.restful.api.dto.medico.DadosAtualizacaoMedico;
import com.restful.api.dto.medico.DadosCadastroMedico;
import com.restful.api.dto.medico.DadosDetalhamentoMedico;
import com.restful.api.dto.medico.DadosListagemMedico;
import com.restful.api.entity.Medico;
import com.restful.api.service.MedicoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/medicos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MedicoController {

    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoMedico> cadastrar(
            @RequestBody @Valid DadosCadastroMedico dados,
            UriComponentsBuilder uriBuilder
    ) {
        Medico saved = medicoService.cadastrar(dados);
        URI uri = uriBuilder.path("/api/v1/medicos/{id}").buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(saved));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        Page<DadosListagemMedico> medicos = medicoService.listar(paginacao);
        return ResponseEntity.ok(medicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoMedico> detalhar(@PathVariable Long id) {
        Medico medico = medicoService.buscarPorId(id);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoMedico> atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        Medico medico = medicoService.atualizar(dados);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        medicoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}