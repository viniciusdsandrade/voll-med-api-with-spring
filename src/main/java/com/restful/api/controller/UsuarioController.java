package com.restful.api.controller;

import com.restful.api.dto.usuario.DadosListagemUsuario;
import com.restful.api.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Controller responsável pelo gerenciamento de usuários.
 * <p>
 * Este controller fornece endpoints para listar os usuários cadastrados no sistema.
 */
@RestController
@RequestMapping("/api/v1/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Usuario Controller", description = "Controller para gerenciamento de usuários")
public class UsuarioController {

    private final UsuarioService usuarioService;

    /**
     * Construtor que injeta o serviço de usuários necessário para as operações do controller.
     *
     * @param usuarioService Serviço que gerencia a lógica de negócios dos usuários.
     */
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Endpoint para listar os usuários cadastrados no sistema.
     *
     * @param paginacao Parâmetros de paginação e ordenação.
     * @return Uma lista paginada de usuários cadastrados.
     */
    @GetMapping
    @Operation(
            summary = "Listar usuários",
            description = "Retorna uma lista paginada de usuários cadastrados no sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso.")
            }
    )
    public ResponseEntity<Page<DadosListagemUsuario>> listar(
            @PageableDefault(size = 5, sort = {"login"}) Pageable paginacao
    ) {
        Page<DadosListagemUsuario> usuarios = usuarioService.listar(paginacao);
        return ok(usuarios);
    }
}
