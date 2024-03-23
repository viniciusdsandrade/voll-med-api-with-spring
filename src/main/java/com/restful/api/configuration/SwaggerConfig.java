package com.restful.api.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;

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

}