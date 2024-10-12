package com.restful.api.controller;

import com.restful.api.dto.usuario.DadosAutenticacao;
import com.restful.api.dto.usuario.DadosTokenJWT;
import com.restful.api.entity.Usuario;
import com.restful.api.security.TokenService;
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

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    private static final Logger logger = getLogger(AutenticacaoController.class);
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AutenticacaoController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
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