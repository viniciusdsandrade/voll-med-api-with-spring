package com.restful.api.dto.medico;

import com.restful.api.dto.endereco.DadosEndereco;
import com.restful.api.entity.enums.Especialidade;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Schema(description = "Modelo de dados para cadastro de médico")
public record DadosCadastroMedico(

        @NotNull(message = "O nome não pode ser nulo")
        @Size(min = 3, max = 255, message = "O nome deve ter entre 3 e 255 caracteres")
        @Schema(description = "Nome do médico")
        String nome,

        @NotBlank(message = "O email não pode ser nulo")
        @Email(message = "O email é inválido")
        @Column(unique = true)
        @Schema(description = "Email do médico")
        String email,

        @NotBlank(message = "O CRM não pode ser nulo")
        @Pattern(regexp = "\\d{4,6}")
        @Column(unique = true)
        @Schema(description = "CRM do médico")
        String crm,

        @NotBlank(message = "O telefone não pode ser nulo")
        @Pattern(regexp = "(?:(?:\\+|00)\\d{1,3}[- ]?)?(?:\\(\\d{2,3}\\)|\\d{2,3})[- ]?\\d{4,5}[- ]?\\d{4}")
        @Schema(description = "Telefone do médico")
        String telefone,

        @Valid
        @NotNull(message = "A especialidade não pode ser nula")
        @Schema(description = "Especialidade do médico")
        Especialidade especialidade,

        @Valid
        @NotNull(message = "O endereço não pode ser nulo")
        @Schema(description = "Endereço do médico")
        DadosEndereco endereco
) {
}