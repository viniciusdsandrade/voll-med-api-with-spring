package com.restful.api.dto.medico;

import com.restful.api.dto.endereco.DadosEndereco;
import com.restful.api.entity.enums.Especialidade;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroMedico(

        @NotNull(message = "O nome não pode ser nulo")
        String nome,

        @Email(message = "O email é inválido")
        @NotBlank(message = "O email não pode ser nulo")
        @Column(unique = true)
        String email,

        @NotBlank(message = "O CRM não pode ser nulo")
        @Pattern(regexp = "\\d{4,6}")
        @Column(unique = true)
        String crm,

        @NotBlank(message = "O telefone não pode ser nulo")
        @Pattern(regexp = "(?:(?:\\+|00)\\d{1,3}[- ]?)?(?:\\(\\d{2,3}\\)|\\d{2,3})[- ]?\\d{4,5}[- ]?\\d{4}")
        String telefone,

        @Valid
        @NotNull(message = "A especialidade não pode ser nula")
        Especialidade especialidade,

        @Valid
        @NotNull(message = "O endereço não pode ser nulo")
        DadosEndereco endereco
) {
}