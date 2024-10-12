package com.restful.api.entity;

import com.restful.api.dto.paciente.DadosAtualizacaoPaciente;
import com.restful.api.dto.paciente.DadosCadastroPaciente;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;
import static java.util.Optional.ofNullable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity(name = "Paciente")
@Table(
        name = "tb_paciente",
        schema = "db_api_voll_med",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_paciente_email", columnNames = "email"),
                @UniqueConstraint(name = "uk_paciente_cpf", columnNames = "cpf")
        }
)
public class Paciente {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String nome;
    private String telefone;
    private String email;
    private String cpf;
    private Boolean ativo;

    @Embedded
    private Endereco endereco;

    public Paciente(@Valid DadosCadastroPaciente dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.cpf = dados.cpf();
        this.endereco = new Endereco(dados.endereco());
    }

    public void atualizarInformacoes(@Valid DadosAtualizacaoPaciente dados) {
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

        Class<?> thisEffectiveClass = this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
                : this.getClass();

        // Se as classes efetivas forem diferentes, retorna false
        if (thisEffectiveClass != oEffectiveClass) return false;

        Paciente that = (Paciente) o;

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
    public final String toString() {
        return "{\n" +
               "\t\t\"id\": " + id + ",\n" +
               "\t\t\"nome\": \"" + nome + "\",\n" +
               "\t\t\"telefone\": \"" + telefone + "\",\n" +
               "\t\t\"email\": \"" + email + "\",\n" +
               "\t\t\"cpf\": \"" + cpf + "\",\n" +
               "\t\t\"ativo\": " + ativo + ",\n" +
               "\t\t\"endereco\": " + endereco.toString() + "\n" +
               "\t}";
    }
}