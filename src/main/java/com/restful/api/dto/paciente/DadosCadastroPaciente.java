package com.restful.api.dto.paciente;

import com.restful.api.dto.endereco.DadosCadastroEndereco;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

@Schema(description = "Dados para cadastro de um paciente")
public record DadosCadastroPaciente(

        @NotBlank(message = "O nome não pode ser nulo")
        @Size(min = 3, max = 255, message = "O nome deve ter entre 3 e 255 caracteres")
        @Schema(description = "Nome do paciente")
        String nome,

        @Column(unique = true)
        @Email(message = "O email é inválido")
        @NotBlank(message = "O email não pode ser nulo")
        @Schema(description = "Email do paciente")
        String email,

        @NotBlank(message = "O telefone não pode ser nulo")
        @Pattern(regexp = "(?:(?:\\+|00)\\d{1,3}[- ]?)?(?:\\(\\d{2,3}\\)|\\d{2,3})[- ]?\\d{4,5}[- ]?\\d{4}")
        @Schema(description = "Telefone do paciente")
        String telefone,

        @CPF(message = "O CPF é inválido")
        @NotBlank(message = "O CPF não pode ser nulo")
        @Schema(description = "CPF do paciente")
        String cpf,

        @Valid
        @NotNull(message = "O endereço não pode ser nulo")
        @Schema(description = "Endereço do paciente")
        DadosCadastroEndereco endereco
) {
}