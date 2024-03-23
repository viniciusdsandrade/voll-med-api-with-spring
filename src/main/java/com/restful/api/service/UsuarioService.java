package com.restful.api.service;

import com.restful.api.dto.usuario.DadosListagemUsuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioService {

    Page<DadosListagemUsuario> listar(Pageable paginacao);
}
