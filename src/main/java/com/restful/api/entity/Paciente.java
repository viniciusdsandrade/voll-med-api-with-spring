package com.restful.api.entity;

import com.restful.api.dto.paciente.DadosAtualizacaoPaciente;
import com.restful.api.dto.paciente.DadosCadastroPaciente;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

import static java.util.Optional.ofNullable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String nome;
    private String telefone;
    private String email;
    private String cpf;
    private Boolean ativo;

    @Embedded
    private Endereco endereco;

    public Paciente(DadosCadastroPaciente dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.cpf = dados.cpf();
        this.endereco = new Endereco(dados.endereco());
    }

    public void atualizarInformacoes(DadosAtualizacaoPaciente dados) {
        ofNullable(dados.nome()).ifPresent(value -> this.nome = value);
        ofNullable(dados.telefone()).ifPresent(value -> this.telefone = value);
        ofNullable(dados.endereco()).ifPresent(value -> this.endereco.atualizarInformacoes(value));
    }

    public void excluir() {
        this.ativo = false;
    }

    @Override
    public String toString() {
        return "{\n" +
               "    \"id\": " + id + ",\n" +
               "    \"nome\": \"" + nome + "\",\n" +
               "    \"telefone\": \"" + telefone + "\",\n" +
               "    \"email\": \"" + email + "\",\n" +
               "    \"cpf\": \"" + cpf + "\",\n" +
               "    \"ativo\": " + ativo + ",\n" +
               "    \"endereco\": " + endereco.toString() + "\n" +
               "}";
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Paciente paciente = (Paciente) o;
        return getId() != null && Objects.equals(getId(), paciente.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}