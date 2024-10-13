package com.restful.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "Usuario")
@Table(
        name = "tb_usuario",
        schema = "db_api_voll_med",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_usuario_login", columnNames = "login")
        }
)
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String login;
    private String senha;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Sobrescreve o metodo {@code equals(Object o)} para comparar entidades do tipo {@code Usuario}, levando em conta proxies do Hibernate.
     * <p>
     * Este metodo garante que a comparação entre duas instâncias de {@code Usuario} seja realizada corretamente,
     * mesmo se uma ou ambas as instâncias forem proxies gerados pelo Hibernate. Ele compara as classes efetivas
     * das instâncias e, se forem da mesma classe, verifica a igualdade com base no ID da entidade.
     *
     * @param o O objeto a ser comparado com a instância atual.
     * @return {@code true} se as instâncias forem consideradas iguais; {@code false} caso contrário.
     */
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true; // Verifica se o objeto atual é o mesmo que está sendo comparado
        if (o == null) return false; // Verifica se o objeto comparado é nulo

        // Verifica se o objeto passado é um proxy do Hibernate e obtém a classe efetiva
        Class<?> oEffectiveClass = o instanceof HibernateProxy
                ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
                : o.getClass(); // Se não for proxy, usa a classe do objeto diretamente

        // Obtém a classe efetiva da instância atual (se for proxy, pega a classe real)
        Class<?> thisEffectiveClass = this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
                : this.getClass(); // Se não for proxy, usa a classe da própria instância

        // Compara as classes efetivas; se forem diferentes, as entidades não são iguais
        if (thisEffectiveClass != oEffectiveClass) return false;

        // Faz o cast do objeto para a classe Usuario para acessar seu ID
        Usuario that = (Usuario) o;

        // Verifica se o ID não é nulo e se os IDs são iguais
        return this.getId() != null &&
               Objects.equals(this.getId(), that.getId());
    }

    /**
     * Sobrescreve o metodo {@code hashCode()} para lidar com proxies do Hibernate na entidade {@code Usuario}.
     * <p>
     * Este metodo garante que o código hash da entidade {@code Usuario} seja calculado corretamente, tanto
     * no caso de a entidade ser um proxy gerado pelo Hibernate (para lazy loading), quanto no caso de ser
     * a instância direta da entidade. Isso é importante para garantir a consistência em coleções que dependem
     * do código hash, como {@code HashSet} e {@code HashMap}.
     *
     * @return O código hash da classe da entidade ou da classe persistente em caso de proxy.
     */
    @Override
    public final int hashCode() {

        // Verifica se a instância atual é um proxy gerado pelo Hibernate
        return this instanceof HibernateProxy

                // Se for um proxy, obtém a classe persistente real e retorna seu hashCode
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()

                // Se não for um proxy, retorna o hashCode da classe da própria instância
                : getClass().hashCode();
    }


    @Override
    public final String toString() {
        return "{\n" +
               "\t\t\"id\": " + id + ",\n" +
               "\t\t\"login\": \"" + login + "\",\n" +
               "\t\t\"senha\": \"" + senha + "\"\n" +
               "\t}";
    }
}