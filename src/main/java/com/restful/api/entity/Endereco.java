package com.restful.api.entity;

import com.restful.api.dto.endereco.DadosEndereco;
import jakarta.persistence.Embeddable;
import jakarta.validation.Valid;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

import static java.util.Optional.ofNullable;

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

    public Endereco(@Valid DadosEndereco dadosEndereco) {
        this.logradouro = dadosEndereco.logradouro();
        this.bairro = dadosEndereco.bairro();
        this.cep = dadosEndereco.cep();
        this.numero = dadosEndereco.numero();
        this.complemento = dadosEndereco.complemento();
        this.cidade = dadosEndereco.cidade();
        this.uf = dadosEndereco.uf();
    }

    public void atualizarInformacoes(@Valid DadosEndereco dados) {
        ofNullable(dados.logradouro()).ifPresent(value -> this.logradouro = value);
        ofNullable(dados.bairro()).ifPresent(value -> this.bairro = value);
        ofNullable(dados.cep()).ifPresent(value -> this.cep = value);
        ofNullable(dados.uf()).ifPresent(value -> this.uf = value);
        ofNullable(dados.cidade()).ifPresent(value -> this.cidade = value);
        ofNullable(dados.numero()).ifPresent(value -> this.numero = value);
        ofNullable(dados.complemento()).ifPresent(value -> this.complemento = value);
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

        Endereco that = (Endereco) o;

        // Compara os campos para igualdade (aqui estou comparando logradouro como exemplo, ajuste conforme a necessidade)
        return Objects.equals(this.logradouro, that.logradouro) &&
               Objects.equals(this.bairro, that.bairro) &&
               Objects.equals(this.cep, that.cep) &&
               Objects.equals(this.numero, that.numero) &&
               Objects.equals(this.complemento, that.complemento) &&
               Objects.equals(this.cidade, that.cidade) &&
               Objects.equals(this.uf, that.uf);
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