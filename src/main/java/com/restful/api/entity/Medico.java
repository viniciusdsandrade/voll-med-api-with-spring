package com.restful.api.entity;

import com.restful.api.dto.medico.DadosAtualizacaoMedico;
import com.restful.api.dto.medico.DadosCadastroMedico;
import com.restful.api.entity.enums.Especialidade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String nome;
    private String email;
    private String crm;
    private String telefone;
    private Boolean ativo;

    @Embedded
    private Endereco endereco;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;


    public Medico(DadosCadastroMedico medico) {
        this.ativo = true;
        this.nome = medico.nome();
        this.email = medico.email();
        this.crm = medico.crm();
        this.telefone = medico.telefone();
        this.especialidade = medico.especialidade();
        this.endereco = new Endereco(medico.endereco());
    }

    public void atualizarInformacoes(DadosAtualizacaoMedico dados) {
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
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Medico medico = (Medico) o;
        return getId() != null && Objects.equals(getId(), medico.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}