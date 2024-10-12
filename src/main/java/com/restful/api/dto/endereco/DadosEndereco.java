package com.restful.api.dto.endereco;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Modelo de dados para endereço")
public record DadosEndereco(

        @NotBlank(message = "O logradouro não pode ser nulo")
        @Schema(description = "Logradouro do endereço")
        String logradouro,

        @NotBlank(message = "O bairro não pode ser nulo")
        @Schema(description = "Bairro do endereço")
        String bairro,

        @NotBlank(message = "O CEP não pode ser nulo")
        @Pattern(regexp = "^\\d{2}(\\.?\\d{3})[- ]?\\d{3}$")
        @Schema(description = "CEP do endereço")
        String cep,

        @NotBlank(message = "A cidade não pode ser nula")
        @Schema(description = "Cidade do endereço")
        String cidade,

        @NotBlank(message = "O UF não pode ser nulo")
        @Pattern(regexp = "^[A-Z]{2}$")
        @Schema(description = "UF do endereço")
        String uf,

        @Size(max = 255)
        @Schema(description = "Complemento do endereço")
        String complemento,

        @Pattern(regexp = "^[1-9]\\d{0,4}$")
        @Schema(description = "Número do endereço")
        @NotBlank(message = "O número não pode ser nulo")
        String numero
) {
}