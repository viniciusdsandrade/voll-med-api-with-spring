package com.restful.api.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.annotations.info.Contact;

import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;

@OpenAPIDefinition(
        info = @Info(
                title = "Spring VollMed API",
                description = "API Restful com pokeapi.co",
                version = "v1.0.0",
                termsOfService = "https://github.com/viniciusdsandrade/triagil-challenge",
                contact = @Contact(
                        name = "Vinícius dos Santos Andrade",
                        email = "vinicius_andrade2010@hotmail.com",
                        url = "https://www.linkedin.com/in/viniciusdsandrade"
                )
        )
)
public class SwaggerConfig {

    @Bean
    OpenAPI springVollMedOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring VollMed API")
                        .description("API para o projeto Spring VollMed")
                        .version("v0.0.1")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org"))
                        .contact(new Contact()
                                .name("Spring VollMed")
                                .url("")
                                .email("vinicius_andrade2010@hotmail.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentação do Projeto")
                        .url("https://github.com/viniciusdsandrade/spring-start/tree/main/alura-java-spring-boot/api"));
    }

    @Bean
    OpenApiCustomizer customerGlobalHeaderOpenApiCustomizer() {

        return openApi -> openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {
            ApiResponses apiResponses = operation.getResponses();

            apiResponses.addApiResponse("200", createApiResponse("Sucesso!"));
            apiResponses.addApiResponse("201", createApiResponse("Objeto Persistido!"));
            apiResponses.addApiResponse("204", createApiResponse("Objeto Excluído!"));
            apiResponses.addApiResponse("400", createApiResponse("Erro na Requisição!"));
            apiResponses.addApiResponse("401", createApiResponse("Acesso Não Autorizado!"));
            apiResponses.addApiResponse("403", createApiResponse("Acesso Proibido!"));
            apiResponses.addApiResponse("404", createApiResponse("Objeto Não Encontrado!"));
            apiResponses.addApiResponse("405", createApiResponse("Método Não Permitido!"));
            apiResponses.addApiResponse("406", createApiResponse("Não Aceitável!"));
            apiResponses.addApiResponse("409", createApiResponse("Conflito!"));
            apiResponses.addApiResponse("500", createApiResponse("Erro na Aplicação!"));
        }));
    }

    private ApiResponse createApiResponse(String message) {
        return new ApiResponse().description(message);
    }
}