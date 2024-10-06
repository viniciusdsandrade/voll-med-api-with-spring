package com.restful.api.repository;

import com.restful.api.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository("usuarioRepository")
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca um UserDetails pelo login do usuário.
     *
     * @param login O login do usuário.
     * @return O UserDetails correspondente ao login fornecido, ou null se não encontrado.
     */
    UserDetails findByLogin(String login);
}

