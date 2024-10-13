package com.restful.api.controller;

import com.restful.api.dto.usuario.DadosAutenticacao;
import com.restful.api.dto.usuario.DadosTokenJWT;
import com.restful.api.entity.Usuario;
import com.restful.api.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

/**
 * Controller responsável pela autenticação de usuários.
 * <p>
 * Este controller gerencia o processo de login dos usuários, realizando a autenticação
 * e gerando tokens JWT para sessões autenticadas.
 */
@RestController
@RequestMapping("/login")
@Tag(name = "Autenticação", description = "Controller para login e geração de tokens JWT")
@Schema(description = "Controller para autenticação de usuários.")
public class AutenticacaoController {

    private static final Logger logger = getLogger(AutenticacaoController.class);
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    /**
     * Construtor que injeta as dependências necessárias para autenticação.
     *
     * @param authenticationManager Gerenciador de autenticação do Spring Security.
     * @param tokenService          Serviço responsável pela geração de tokens JWT.
     */
    public AutenticacaoController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    /**
     * Realiza o login de um usuário.
     * <p>
     * Este metodo recebe as credenciais de login, realiza a autenticação e, caso tenha
     * sucesso, gera um token JWT para a sessão autenticada. Se a autenticação falhar,
     * retorna uma resposta de erro.
     *
     * @param dados Objeto contendo os dados de autenticação (login e senha).
     * @return Retorna um {@link ResponseEntity} com o token JWT gerado em caso de sucesso,
     * ou uma mensagem de erro em caso de falha na autenticação.
     */
    @PostMapping
    @Operation(
            summary = "Autenticar usuário",
            description = "Endpoint para autenticação de usuários e geração de um token JWT",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Autenticação realizada com sucesso, token JWT retornado."),
                    @ApiResponse(responseCode = "400", description = "Falha na autenticação, credenciais inválidas ou erro no processo.")
            }
    )
    public ResponseEntity<Object> efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
            var authentication = authenticationManager.authenticate(authenticationToken);
            var tokenGerado = tokenService.gerarToken((Usuario) authentication.getPrincipal());

            return ok(new DadosTokenJWT(tokenGerado));
        } catch (Exception e) {
            logger.error("Authentication failed for user: {}", dados.login(), e);
            return badRequest().body(e.getMessage());
        }
    }
}
