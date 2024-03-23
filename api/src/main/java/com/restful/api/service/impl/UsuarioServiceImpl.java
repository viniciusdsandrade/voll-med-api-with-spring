package com.restful.api.service.impl;

import com.restful.api.dto.usuario.DadosListagemUsuario;
import com.restful.api.entity.Usuario;
import com.restful.api.repository.UsuarioRepository;
import com.restful.api.service.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioService) {
        this.usuarioRepository = usuarioService;
    }

    @Override
    public Page<DadosListagemUsuario> listar(Pageable paginacao) {
        Page<Usuario> usuarios = usuarioRepository.findAll(paginacao);
        return usuarios.map(DadosListagemUsuario::new);
    }
}