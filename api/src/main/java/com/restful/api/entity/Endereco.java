package com.restful.api.entity;

import com.restful.api.dto.endereco.DadosEndereco;
import jakarta.persistence.Embeddable;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Embeddable
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
        if (dados.logradouro() != null) this.logradouro = dados.logradouro();
        if (dados.bairro() != null) this.bairro = dados.bairro();
        if (dados.cep() != null) this.cep = dados.cep();
        if (dados.uf() != null) this.uf = dados.uf();
        if (dados.cidade() != null) this.cidade = dados.cidade();
        if (dados.numero() != null) this.numero = dados.numero();
        if (dados.complemento() != null) this.complemento = dados.complemento();
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