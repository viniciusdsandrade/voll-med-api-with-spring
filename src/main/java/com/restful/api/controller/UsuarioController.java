package com.restful.api.controller;

import com.restful.api.dto.usuario.DadosListagemUsuario;
import com.restful.api.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Usuario Controller", description = "Controller para gerenciamento de usuários")
@Schema(description = "Controller para gerenciamento de usuários.")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @Operation(
            summary = "Listar usuários",
            description = "Retorna uma lista paginada de usuários cadastrados no sistema."
    )
    @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso.")
    public ResponseEntity<Page<DadosListagemUsuario>> listar(
            @PageableDefault(size = 5, sort = {"login"}) Pageable paginacao
    ) {
        Page<DadosListagemUsuario> usuarios = usuarioService.listar(paginacao);
        return ok(usuarios);
    }
}
