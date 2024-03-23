package com.restful.api.dto.consulta;

import com.restful.api.entity.Paciente;

public record PacienteDetalhes(
        Long id,
        String nome,
        String cpf,
        String telefone,
        String email
) {
    public PacienteDetalhes(Paciente paciente) {
        this(
                paciente.getId(),
                paciente.getNome(),
                paciente.getCpf(),
                paciente.getTelefone(),
                paciente.getEmail()
        );
    }
}
