package com.restful.api.dto.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco(

        @NotBlank(message = "O logradouro não pode ser nulo")
        String logradouro,

        @NotBlank(message = "O bairro não pode ser nulo")
        String bairro,

        @NotBlank(message = "O CEP não pode ser nulo")
        @Pattern(regexp = "^\\d{2}(\\.?\\d{3})[- ]?\\d{3}$")
        String cep,

        @NotBlank(message = "A cidade não pode ser nula")
        String cidade,

        @NotBlank(message = "O UF não pode ser nulo")
        @Pattern(regexp = "^[A-Z]{2}$")
        String uf,

        String complemento,

        @Pattern(regexp = "^[1-9]\\d{0,4}$")
        String numero
) {
}