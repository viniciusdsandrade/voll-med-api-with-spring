package com.restful.api.entity;

import com.restful.api.entity.enums.MotivoCancelamento;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity(name = "Consulta")
@Table(
        name = "tb_consulta",
        schema = "db_api_voll_med"
)
public class Consulta {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    private LocalDateTime dataHora;

    @Enumerated(STRING)
    @Column(name = "motivo_cancelamento")
    private MotivoCancelamento motivoCancelamento;

    public void cancelar(MotivoCancelamento motivo) {
        this.motivoCancelamento = motivo;
    }

    /**
     * Sobrescreve o metodo {@code equals(Object o)} para comparar entidades levando em conta proxies do Hibernate.
     * <p>
     * Este metodo garante que a comparação entre duas instâncias de uma entidade seja realizada corretamente,
     * mesmo se uma ou ambas as instâncias forem proxies gerados pelo Hibernate. Ele compara as classes efetivas
     * das instâncias e, se forem da mesma classe, verifica a igualdade com base no ID da entidade.
     *
     * @param o O objeto a ser comparado com a instância atual.
     * @return {@code true} se as instâncias forem consideradas iguais; {@code false} caso contrário.
     */
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true; // Verifica se o objeto atual é o mesmo que está sendo comparado
        if (o == null) return false; // Verifica se o objeto comparado é nul

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

        // Faz o cast do objeto para a classe Consulta para acessar seu ID
        Consulta that = (Consulta) o;

        // Verifica se o ID não é nulo e se os IDs são iguais
        return this.getId() != null &&
               Objects.equals(this.getId(), that.getId());
    }


    /**
     * Sobrescreve o metodo {@code hashCode()} para lidar com proxies do Hibernate.
     * <p>
     * Este etodo garante que o código hash da entidade seja calculado corretamente, tanto
     * no caso de a entidade ser um proxy gerado pelo Hibernate (para lazy loading), quanto
     * no caso de ser a instância direta da entidade. Isso é importante para garantir a
     * consistência em coleções que dependem do código hash, como {@code HashSet} e {@code HashMap}.
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
               "\t\t\"id\": " + id + ",\n" +
               "\t\t\"medico\": " + (medico != null ? medico.toString() : "null") + ",\n" +
               "\t\t\"paciente\": " + (paciente != null ? paciente.toString() : "null") + ",\n" +
               "\t\t\"dataHora\": \"" + dataHora + "\",\n" +
               "\t\t\"motivoCancelamento\": \"" + (motivoCancelamento != null ? motivoCancelamento : "null") + "\"\n" +
               "\t}";
    }
}