package com.restful.api.controller;

import com.restful.api.dto.paciente.DadosAtualizacaoPaciente;
import com.restful.api.dto.paciente.DadosCadastroPaciente;
import com.restful.api.dto.paciente.DadosDetalhamentoPaciente;
import com.restful.api.dto.paciente.DadosListagemPaciente;
import com.restful.api.entity.Paciente;
import com.restful.api.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/pacientes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoPaciente> cadastrar(
            @RequestBody @Valid DadosCadastroPaciente dados,
            UriComponentsBuilder uriBuilder
    ) {
        Paciente saved = pacienteService.cadastrar(dados);
        URI uri = uriBuilder.path("/api/v1/pacientes/{id}").buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(saved));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var pacientes = pacienteService.listar(paginacao);
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoPaciente> detalhar(@PathVariable Long id) {
        var paciente = pacienteService.buscarPorId(id);
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }

    @Transactional
    @PutMapping
    public ResponseEntity<DadosDetalhamentoPaciente> atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dados) {
        Paciente paciente = pacienteService.atualizar(dados);
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPaciente(@PathVariable Long id) {
        pacienteService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}