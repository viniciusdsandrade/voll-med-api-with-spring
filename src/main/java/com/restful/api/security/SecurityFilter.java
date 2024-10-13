package com.restful.api.security;

import com.restful.api.repository.UsuarioRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro de segurança responsável pela validação do token JWT em cada requisição.
 * <p>
 * Este filtro é executado uma vez por requisição (extends {@link OncePerRequestFilter})
 * e pretende validar o token JWT presente no cabeçalho "Authorization".
 * Caso o token seja válido, o filtro autentica o usuário associado ao token e o
 * registra no contexto de segurança do Spring {@link SecurityContextHolder}.
 * <p>
 * O filtro utiliza a {@link TokenService} para extrair o sujeito do token e o
 * {@link UsuarioRepository} para buscar o usuário correspondente no banco de dados.
 *
 * @see OncePerRequestFilter
 * @see SecurityContextHolder
 */
@Component("securityFilter")
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    /**
     * Construtor que injeta as dependências necessárias.
     *
     * @param tokenService Serviço responsável pela validação e extração de informações do token JWT.
     * @param usuarioRepository Repositório de usuários para buscar informações do usuário com base no token JWT.
     */
    public SecurityFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Metodo que intercepta cada requisição HTTP, recupera e valida o token JWT.
     * <p>
     * Caso o token seja válido, o metodo autentica o usuário associado e registra a autenticação no
     * contexto de segurança {@link SecurityContextHolder}. O filtro continua a cadeia de execução chamando
     * {@link FilterChain#doFilter}.
     *
     * @param request Requisição HTTP sendo processada.
     * @param response Resposta HTTP correspondente.
     * @param filterChain Cadeia de filtros a ser continuada após a execução deste filtro.
     * @throws ServletException Em caso de erro durante o processamento da requisição.
     * @throws IOException Em caso de erro de I/O durante o processamento da requisição.
     */
    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {

        var tokenJWT = recuperarToken(request);

        if (tokenJWT != null) {
            var subject = tokenService.getSubject(tokenJWT);
            var usuario = usuarioRepository.findByLogin(subject);

            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

            // Registra a autenticação no contexto de segurança
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Continua a cadeia de filtros
        filterChain.doFilter(request, response);
    }

    /**
     * Metodo auxiliar para recuperar o token JWT do cabeçalho "Authorization" da requisição HTTP.
     * <p>
     * O metodo verifica se o cabeçalho está presente e, caso positivo, remove o prefixo "Bearer " do token.
     *
     * @param request Requisição HTTP contendo o cabeçalho Authorization.
     * @return O token JWT se presente no cabeçalho, ou {@code null} se o cabeçalho não estiver presente.
     */
    private String recuperarToken(HttpServletRequest request) {
        var authorization = request.getHeader("Authorization");
        if (authorization != null) return authorization.replace("Bearer ", "").trim();

        return null;
    }
}
