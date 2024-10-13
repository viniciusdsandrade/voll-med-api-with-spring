package com.restful.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * Classe de configuração de segurança para a aplicação.
 * <p>
 * Esta classe define as configurações de autenticação e autorização utilizando o Spring Security.
 * Ela desabilita a proteção contra CSRF, define o gerenciamento de sessão como stateless (sem estado)
 * e configura as permissões de acesso para diferentes endpoints da API.
 * <p>
 * A classe também define beans importantes para o funcionamento da segurança, como o filtro de segurança,
 * o gerenciador de autenticação e o codificador de senhas.
 * <p>
 * - Filtro de segurança personalizado {@link SecurityFilter} é adicionado antes do filtro de autenticação padrão.
 * - Autenticação é feita usando um {@link AuthenticationManager}.
 * - Senhas são codificadas usando o {@link BCryptPasswordEncoder}.
 * <p>
 * Exemplos de endpoints públicos incluem:
 * - '/login': acesso permitido para todos os usuários.
 * - '/api/v1/medicos': listagem de médicos acessível a qualquer usuário, autenticado ou não.
 * <p>
 * Outros endpoints exigem autenticação e autorização apropriadas.
 *
 * @see org.springframework.security.web.SecurityFilterChain
 * @see org.springframework.security.authentication.AuthenticationManager
 * @see org.springframework.security.crypto.password.PasswordEncoder
 */
@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    private final SecurityFilter securityFilter;

    /**
     * Construtor que injeta o filtro de segurança customizado.
     *
     * @param securityFilter Filtro de segurança a ser aplicado antes da autenticação padrão do Spring.
     */
    public SecurityConfigurations(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    /**
     * Configura a cadeia de filtros de segurança, desabilitando CSRF, definindo a política de sessão como stateless
     * e configurando permissões de acesso para endpoints específicos.
     *
     * @param http Instância do HttpSecurity utilizada para construir a configuração de segurança.
     * @return Uma instância de SecurityFilterChain contendo as configurações de segurança.
     * @throws Exception Pode lançar exceções relacionadas à configuração de segurança.
     */

    @Bean("securityFilterChain")
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(STATELESS))
                .authorizeHttpRequests(req -> {
                    // Permitir acesso ao Swagger e à documentação da API sem autenticação
                    req.requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll();
                    req.requestMatchers("/login").permitAll(); // Permitir acesso à página de login sem autenticação
                    req.requestMatchers(GET, "/api/v1/medicos").permitAll(); // Permitir listagem de médicos
                    req.requestMatchers(DELETE, "/pacientes").hasRole("ADMIN");
                    req.requestMatchers(DELETE, "/medicos").hasRole("ADMIN");
                    req.anyRequest().authenticated(); // Qualquer outra requisição exige autenticação
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    /**
     * Define o gerenciador de autenticação que será utilizado na aplicação.
     *
     * @param config Configuração de autenticação utilizada para obter o AuthenticationManager.
     * @return Uma instância de AuthenticationManager.
     * @throws Exception Pode lançar exceções relacionadas à configuração de autenticação.
     */
    @Bean("authenticationManager")
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Define o codificador de senhas a ser utilizado, no caso, BCrypt.
     *
     * @return Uma instância de PasswordEncoder que utiliza o algoritmo BCrypt.
     */
    @Bean("passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}