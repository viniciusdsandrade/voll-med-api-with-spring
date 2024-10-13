package com.restful.api.entity;

import com.restful.api.dto.endereco.DadosCadastroEndereco;
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

    public Endereco(@Valid DadosCadastroEndereco dadosCadastroEndereco) {
        this.logradouro = dadosCadastroEndereco.logradouro();
        this.bairro = dadosCadastroEndereco.bairro();
        this.cep = dadosCadastroEndereco.cep();
        this.numero = dadosCadastroEndereco.numero();
        this.complemento = dadosCadastroEndereco.complemento();
        this.cidade = dadosCadastroEndereco.cidade();
        this.uf = dadosCadastroEndereco.uf();
    }

    public void atualizarInformacoes(@Valid DadosCadastroEndereco dados) {
        ofNullable(dados.logradouro()).ifPresent(value -> this.logradouro = value);
        ofNullable(dados.bairro()).ifPresent(value -> this.bairro = value);
        ofNullable(dados.cep()).ifPresent(value -> this.cep = value);
        ofNullable(dados.uf()).ifPresent(value -> this.uf = value);
        ofNullable(dados.cidade()).ifPresent(value -> this.cidade = value);
        ofNullable(dados.numero()).ifPresent(value -> this.numero = value);
        ofNullable(dados.complemento()).ifPresent(value -> this.complemento = value);
    }

    /**
     * Sobrescreve o metodo {@code equals(Object o)} para comparar entidades do tipo {@code Endereco}, levando em conta proxies do Hibernate.
     * <p>
     * Este metodo garante que a comparação entre duas instâncias de {@code Endereco} seja realizada corretamente,
     * mesmo se uma ou ambas as instâncias forem proxies gerados pelo Hibernate. Ele compara as classes efetivas
     * das instâncias e, se forem da mesma classe, verifica a igualdade com base nos campos relevantes da entidade,
     * como logradouro, bairro, cep, número, complemento, cidade e UF.
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

        // Faz o cast do objeto para a classe Endereco para acessar seus campos
        Endereco that = (Endereco) o;

        // Compara os campos relevantes do Endereco
        return Objects.equals(this.logradouro, that.logradouro) &&
               Objects.equals(this.bairro, that.bairro) &&
               Objects.equals(this.cep, that.cep) &&
               Objects.equals(this.numero, that.numero) &&
               Objects.equals(this.complemento, that.complemento) &&
               Objects.equals(this.cidade, that.cidade) &&
               Objects.equals(this.uf, that.uf);
    }

    /**
     * Sobrescreve o metodo {@code hashCode()} para lidar com proxies do Hibernate na entidade {@code Endereco}.
     * <p>
     * Este metodo garante que o código hash da entidade {@code Endereco} seja calculado corretamente, tanto
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