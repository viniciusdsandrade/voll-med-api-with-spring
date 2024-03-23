package com.restful.api.entity;

import com.restful.api.dto.paciente.DadosAtualizacaoPaciente;
import com.restful.api.dto.paciente.DadosCadastroPaciente;
import jakarta.persistence.*;
import lombok.*;

@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
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
        if (dados.nome() != null) this.nome = dados.nome();
        if (dados.telefone() != null) this.telefone = dados.telefone();
        if (dados.endereco() != null) this.endereco.atualizarInformacoes(dados.endereco());
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

}