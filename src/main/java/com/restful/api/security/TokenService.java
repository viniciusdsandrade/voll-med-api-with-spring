package com.restful.api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.restful.api.entity.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static com.auth0.jwt.algorithms.Algorithm.HMAC256;
import static java.time.LocalDateTime.now;
import static java.time.ZoneId.systemDefault;

/**
 * Serviço responsável pela geração e verificação de tokens JWT.
 * <p>
 * Este serviço utiliza a biblioteca Auth0 JWT para gerar e validar tokens de autenticação baseados em JWT.
 * Ele assina os tokens utilizando um algoritmo HMAC256 com uma chave secreta definida nas propriedades da aplicação.
 * O token gerado contém o login do usuário como subject, uma data de expiração e o ID do usuário.
 * <p>
 * Exemplo de uso:
 * - {@link #gerarToken(Usuario)}: Gera um novo token JWT para o usuário.
 * - {@link #getSubject(String)}: Recupera o subject (login) de um token JWT.
 *
 * @see JWT
 * @see Usuario
 */
@Service("tokenService")
public class TokenService {

    private static final String ISSUER = "API Voll.med"; // Identificador do emissor do token

    @Value("${api.security.token.secret}")
    private String secret; // Chave secreta utilizada para assinar os tokens JWT

    /**
     * Gera um token JWT para o usuário fornecido.
     * <p>
     * O token contém informações sobre o emissor, o login do usuário (como subject), a data de expiração
     * e o ID do usuário como um claim personalizado. O token é assinado com um algoritmo HMAC256 utilizando
     * a chave secreta definida nas propriedades da aplicação.
     *
     * @param usuario O usuário para quem o token será gerado.
     * @return Uma string contendo o token JWT gerado.
     * @throws RuntimeException Se ocorrer um erro durante a geração do token.
     */
    public String gerarToken(Usuario usuario) {

        System.out.println("secret: " + secret);
        try {
            var algorithm = HMAC256(secret);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(dataExpiracao())
                    .withClaim("id", usuario.getId())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token");
        }
    }

    /**
     * Recupera o subject (login) de um token JWT.
     * <p>
     * O metodo valida o token fornecido, verificando sua assinatura e o emissor.
     * Caso o token seja válido, o metodo retorna o subject (login) associado ao token.
     *
     * @param token O token JWT a ser validado e analisado.
     * @return O subject (login) contido no token JWT.
     * @throws RuntimeException Se o token for inválido ou expirado.
     */
    public String getSubject(String token) {
        try {
            var algorithm = HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Token JWT inválido ou expirado");
        }
    }

    /**
     * Gera uma data de expiração para o token JWT.
     * <p>
     * A data de expiração é definida para 365 dias a partir da data atual.
     *
     * @return Um {@link Instant} representando a data de expiração do token.
     */
    private Instant dataExpiracao() {
        return now().plusDays(365).atZone(systemDefault()).toInstant();
    }
}
