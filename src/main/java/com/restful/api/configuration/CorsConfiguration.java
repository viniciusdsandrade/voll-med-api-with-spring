package com.restful.api.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuração para habilitar o suporte a CORS (Cross-Origin Resource Sharing) na aplicação.
 * <p>
 * Esta classe permite que a aplicação aceite requisições de diferentes origens, especificamente
 * do frontend em execução em <code><a href="http://localhost:3000">...</a></code>. Ela define quais métodos HTTP
 * são permitidos para essas requisições.
 * <p>
 * Implementa a interface {@link WebMvcConfigurer} para configurar o comportamento do CORS.
 *
 * @see WebMvcConfigurer
 */
@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    /**
     * Configura o mapeamento do CORS para permitir requisições entre origens diferentes.
     * <p>
     * Aqui, o CORS é habilitado para todas as rotas da API (<code>/**</code>), permitindo
     * que o frontend em <code><a href="http://localhost:3000">...</a></code> faça requisições HTTP para a API,
     * com suporte para métodos como GET, POST, PUT, DELETE, entre outros.
     *
     * @param registry O registro de mapeamento CORS utilizado para definir as regras de CORS.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "http://localhost:8080") // Definir origens permitidas
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT")
                .allowedHeaders("*")
                .allowCredentials(true); // Permitir envio de cookies (se necessário)
    }
}
