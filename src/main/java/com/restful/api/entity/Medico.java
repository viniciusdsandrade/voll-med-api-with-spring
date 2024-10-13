package com.restful.api.entity;

import com.restful.api.dto.medico.DadosAtualizacaoMedico;
import com.restful.api.dto.medico.DadosCadastroMedico;
import com.restful.api.entity.enums.Especialidade;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static java.util.Optional.ofNullable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "Medico")
@Table(
        name = "tb_medico",
        schema = "db_api_voll_med",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_medico_email", columnNames = "email"),
                @UniqueConstraint(name = "uk_medico_crm", columnNames = "crm")
        }
)
public class Medico {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String crm;
    private String telefone;
    private Boolean ativo;

    @Embedded
    private Endereco endereco;

    @Enumerated(STRING)
    private Especialidade especialidade;

    public Medico(@Valid DadosCadastroMedico medico) {
        this.ativo = true;
        this.nome = medico.nome();
        this.email = medico.email();
        this.crm = medico.crm();
        this.telefone = medico.telefone();
        this.especialidade = medico.especialidade();
        this.endereco = new Endereco(medico.endereco());
    }

    public void atualizarInformacoes(@Valid DadosAtualizacaoMedico dados) {
        ofNullable(dados.nome()).ifPresent(value -> this.nome = value);
        ofNullable(dados.telefone()).ifPresent(value -> this.telefone = value);
        ofNullable(dados.endereco()).ifPresent(value -> this.endereco.atualizarInformacoes(value));
    }

    public void excluir() {
        this.ativo = false;
    }

    /**
     * Sobrescreve o metodo {@code equals(Object o)} para comparar entidades do tipo {@code Medico} levando em conta proxies do Hibernate.
     * <p>
     * Este metodo garante que a comparação entre duas instâncias de {@code Medico} seja realizada corretamente,
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

        // Faz o cast do objeto para a classe Medico para acessar seu ID
        Medico that = (Medico) o;

        // Verifica se o ID não é nulo e se os IDs são iguais
        return this.getId() != null &&
               Objects.equals(this.getId(), that.getId());
    }

    /**
     * Sobrescreve o metodo {@code hashCode()} para lidar com proxies do Hibernate na entidade {@code Medico}.
     * <p>
     * Este metodo garante que o código hash da entidade {@code Medico} seja calculado corretamente, tanto
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
    public String toString() {
        return "{\n" +
               "\t\t\"id\": \"" + id + "\",\n" +
               "\t\t\"nome\": \"" + nome + "\",\n" +
               "\t\t\"email\": \"" + email + "\",\n" +
               "\t\t\"crm\": \"" + crm + "\",\n" +
               "\t\t\"telefone\": \"" + telefone + "\",\n" +
               "\t\t\"ativo\": \"" + ativo + "\",\n" +
               "\t\t\"endereco\": " + endereco.toString() + ",\n" +  // Chamando o toString() da entidade Endereco
               "\t\t\"especialidade\": \"" + especialidade + "\"\n" +
               "\t}";
    }
}