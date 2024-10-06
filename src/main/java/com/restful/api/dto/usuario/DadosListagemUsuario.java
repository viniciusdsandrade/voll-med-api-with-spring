package com.restful.api.dto.usuario;

import com.restful.api.entity.Usuario;

public record DadosListagemUsuario(
        Long id,
        String login
) {
    public DadosListagemUsuario(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getLogin()
        );
    }
}
