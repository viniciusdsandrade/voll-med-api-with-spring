package com.restful.api.entity;

import com.restful.api.dto.endereco.DadosEndereco;
import jakarta.persistence.Embeddable;
import jakarta.validation.Valid;
import lombok.*;

import static java.util.Optional.ofNullable;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Embeddable
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    private String logradouro;
    private String bairro;
    private String cep;
    private String numero;
    private String complemento;
    private String cidade;
    private String uf;

    public Endereco(DadosEndereco dadosEndereco) {
        this.logradouro = dadosEndereco.logradouro();
        this.bairro = dadosEndereco.bairro();
        this.cep = dadosEndereco.cep();
        this.numero = dadosEndereco.numero();
        this.complemento = dadosEndereco.complemento();
        this.cidade = dadosEndereco.cidade();
        this.uf = dadosEndereco.uf();
    }

    public void atualizarInformacoes(DadosEndereco dados) {
        ofNullable(dados.logradouro()).ifPresent(value -> this.logradouro = value);
        ofNullable(dados.bairro()).ifPresent(value -> this.bairro = value);
        ofNullable(dados.cep()).ifPresent(value -> this.cep = value);
        ofNullable(dados.uf()).ifPresent(value -> this.uf = value);
        ofNullable(dados.cidade()).ifPresent(value -> this.cidade = value);
        ofNullable(dados.numero()).ifPresent(value -> this.numero = value);
        ofNullable(dados.complemento()).ifPresent(value -> this.complemento = value);
    }

    @Override
    public String toString() {
        return "{\n" +
               "\t\t\"logradouro\": \"" + logradouro + "\",\n" +
               "\t\t\"bairro\": \"" + bairro + "\",\n" +
               "\t\t\"cep\": \"" + cep + "\",\n" +
               "\t\t\"numero\": \"" + numero + "\",\n" +
               "\t\t\"complemento\": \"" + complemento + "\",\n" +
               "\t\t\"cidade\": \"" + cidade + "\",\n" +
               "\t\t\"uf\": \"" + uf + "\"\n" +
               "\t}";
    }
}