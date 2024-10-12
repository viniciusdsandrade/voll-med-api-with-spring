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

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        Class<?> oEffectiveClass = o instanceof HibernateProxy
                ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
                : o.getClass();

        Class<?> thisEffectiveClass = this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
                : this.getClass();

        if (thisEffectiveClass != oEffectiveClass) return false;

        Consulta that = (Consulta) o;

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
               "\t\t\"medico\": " + (medico != null ? medico.toString() : "null") + ",\n" +
               "\t\t\"paciente\": " + (paciente != null ? paciente.toString() : "null") + ",\n" +
               "\t\t\"dataHora\": \"" + dataHora + "\",\n" +
               "\t\t\"motivoCancelamento\": \"" + (motivoCancelamento != null ? motivoCancelamento : "null") + "\"\n" +
               "\t}";
    }
}