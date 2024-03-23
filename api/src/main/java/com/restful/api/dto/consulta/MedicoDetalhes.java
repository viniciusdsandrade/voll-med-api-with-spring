package com.restful.api.dto.consulta;

import com.restful.api.entity.enums.Especialidade;
import com.restful.api.entity.Medico;

public record MedicoDetalhes(
        Long id,
        String nome,
        String email,
        String crm,
        String telefone,
        Especialidade especialidade
) {
    public MedicoDetalhes(Medico medico) {
        this(
                medico.getId(),
                medico.getNome(),
                medico.getEmail(),
                medico.getCrm(),
                medico.getTelefone(),
                medico.getEspecialidade()
        );
    }
}
