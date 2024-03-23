package com.restful.api.entity;

import com.restful.api.dto.medico.DadosAtualizacaoMedico;
import com.restful.api.dto.medico.DadosCadastroMedico;
import com.restful.api.entity.enums.Especialidade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
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
        if (dados.nome() != null) this.nome = dados.nome();
        if (dados.telefone() != null) this.telefone = dados.telefone();
        if (dados.endereco() != null) this.endereco.atualizarInformacoes(dados.endereco());
    }

    public void excluir() {
        this.ativo = false;
    }
}