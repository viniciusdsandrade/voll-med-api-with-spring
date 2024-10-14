package com.restful.api.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do Swagger para documentação da API RESTFul.
 * <p>
 * Esta classe define as configurações de metadados do Swagger/OpenAPI, como o título, descrição,
 * termos de uso, informações de contato, licença, e versão da API.
 * <p>
 * O Swagger facilita a visualização e interação com os endpoints da API, gerando uma
 * documentação interativa que pode ser acessada via uma interface web.
 *
 * @see io.swagger.v3.oas.annotations.OpenAPIDefinition
 * @see io.swagger.v3.oas.annotations.info.Info
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Voll Med API",
                description = "API RestFul para gerenciamento de consultas médicas.",
                version = "v2.0.0",
                termsOfService = "https://github.com/viniciusdsandrade/voll-med-api-with-spring", // Repositório da aplicação
                contact = @Contact(
                        name = "Vinícius dos Santos Andrade",
                        email = "vinicius_andrade2010@hotmail.com",
                        url = "https://www.linkedin.com/in/viniciusdsandrade"
                )
        )
)
public class SwaggerConfig {
}