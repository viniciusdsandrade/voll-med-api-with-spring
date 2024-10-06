package com.restful.api.service;

import com.restful.api.dto.usuario.DadosListagemUsuario;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Schema(name = "UsuarioService")
public interface UsuarioService {

    Page<DadosListagemUsuario> listar(Pageable paginacao);
}
