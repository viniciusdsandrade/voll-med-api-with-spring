package com.restful.api.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;

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
@OpenAPIDefinition(
        info = @Info(
                title = "Voll Med",
                description = "API RestFul para gerenciamento de consultas médicas.",
                termsOfService = "https://github.com/viniciusdsandrade/voll-med-api-with-spring",
                contact = @Contact(
                        name = "Vinícius dos Santos Andrade",
                        email = "vinicius_andrade2010@hotmail.com",
                        url = "https://www.linkedin.com/in/viniciusdsandrade"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                ),
                version = "v2.0.0"
        )
)
public class SwaggerConfig {
}
