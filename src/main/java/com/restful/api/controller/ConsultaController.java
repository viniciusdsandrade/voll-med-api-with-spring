package com.restful.api.controller;

import com.restful.api.dto.consulta.DadosAgendamentoConsulta;
import com.restful.api.dto.consulta.DadosCancelamentoConsulta;
import com.restful.api.dto.consulta.DadosDetalhamentoConsulta;
import com.restful.api.service.AgendaService;
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
@RequestMapping("/api/v1/consulta")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ConsultaController {

    private final AgendaService agendaService;

    public ConsultaController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoConsulta> agendar(
            @RequestBody @Valid DadosAgendamentoConsulta dados,
            UriComponentsBuilder uriBuilder) {
        var consulta = agendaService.agendar(dados);
        URI uri = uriBuilder.path("/api/v1/consulta/{id}").buildAndExpand(consulta.id()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoConsulta(consulta));
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<Void> cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados) {
        agendaService.cancelar(dados);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoConsulta>> listar(@PageableDefault(size = 10, sort = {"dataHora"}) Pageable paginacao) {
        Page<DadosDetalhamentoConsulta> consultas = agendaService.listar(paginacao);
        return ResponseEntity.ok(consultas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoConsulta> detalhar(@PathVariable Long id) {
        var consulta = agendaService.buscarPorId(id);
        return ResponseEntity.ok(new DadosDetalhamentoConsulta(consulta));
    }
}
