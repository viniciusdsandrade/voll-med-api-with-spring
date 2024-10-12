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

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        // Verifica se o objeto é um proxy do Hibernate e obtém a classe efetiva
        Class<?> oEffectiveClass = o instanceof HibernateProxy
                ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
                : o.getClass();

        // Se as classes efetivas forem diferentes, retorna false
        Class<?> thisEffectiveClass = this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
                : this.getClass();

        if (thisEffectiveClass != oEffectiveClass) return false;

        Medico that = (Medico) o;

        return this.getId() != null &&
               Objects.equals(this.getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
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