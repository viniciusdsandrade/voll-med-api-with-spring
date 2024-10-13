package com.restful.api.security;

import com.restful.api.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Serviço de autenticação responsável por carregar os detalhes do usuário.
 * <p>
 * Esta classe implementa a interface {@link UserDetailsService} do Spring Security,
 * fornecendo uma forma de buscar o usuário no banco de dados com base em seu login (username).
 * O serviço utiliza o {@link UsuarioRepository} para buscar os dados do usuário no repositório.
 * <p>
 * Caso o usuário não seja encontrado, a exceção {@link UsernameNotFoundException} é lançada.
 *
 * @see UserDetailsService
 * @see UsuarioRepository
 */
@Service("autenticacaoService")
public class AutenticacaoService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    /**
     * Construtor que injeta o repositório de usuários.
     *
     * @param usuarioRepository O repositório de usuários que será utilizado para buscar o login.
     */
    public AutenticacaoService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Carrega os detalhes do usuário com base no login (username) fornecido.
     * <p>
     * Este metodo busca o usuário no banco de dados usando o login fornecido.
     * Caso o usuário não seja encontrado, uma exceção {@link UsernameNotFoundException} é lançada.
     *
     * @param username O login do usuário a ser buscado.
     * @return Um objeto {@link UserDetails} contendo os detalhes do usuário autenticado.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByLogin(username);
    }
}
