package com.restful.api.dto.paciente;

import com.restful.api.dto.endereco.DadosEndereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;

public record DadosCadastroPaciente(

        @NotBlank(message = "O nome não pode ser nulo")
        String nome,

        @Email(message = "O email é inválido")
        @NotBlank(message = "O email não pode ser nulo")
        String email,

        @NotBlank(message = "O telefone não pode ser nulo")
        @Pattern(regexp = "(?:(?:\\+|00)\\d{1,3}[- ]?)?(?:\\(\\d{2,3}\\)|\\d{2,3})[- ]?\\d{4,5}[- ]?\\d{4}")
        String telefone,

        @CPF(message = "O CPF é inválido")
        @NotBlank(message = "O CPF não pode ser nulo")
        String cpf,

        @Valid
        @NotNull(message = "O endereço não pode ser nulo")
        DadosEndereco endereco
) {
}