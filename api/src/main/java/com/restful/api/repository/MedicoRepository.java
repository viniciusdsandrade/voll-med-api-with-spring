package com.restful.api.repository;

import com.restful.api.entity.enums.Especialidade;
import com.restful.api.entity.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    /**
     * Busca uma página de médicos ativos.
     *
     * @param paginacao As informações de paginação.
     * @return Uma página de médicos ativos.
     */
    Page<Medico> findAllByAtivoTrue(Pageable paginacao);

    /**
     * Verifica se existe um médico com o email fornecido.
     *
     * @param email O email do médico a ser verificado.
     * @return True se existir um médico com o email fornecido, caso contrário False.
     */
    boolean existsByEmail(String email);

    /**
     * Verifica se existe um médico com o CRM fornecido.
     *
     * @param crm O CRM do médico a ser verificado.
     * @return True se existir um médico com o CRM fornecido, caso contrário False.
     */
    boolean existsByCrm(String crm);

    /**
     * Busca um médico disponível com a especialidade especificada, que esteja ativo e que não tenha consulta marcada na data e hora fornecidas.
     *
     * @param especialidade A especialidade do médico.
     * @param dataHora A data e hora da consulta.
     * @return Um médico disponível com a especialidade especificada, ou null se nenhum médico estiver disponível.
     */
    @Query("SELECT m FROM Medico m WHERE " +
            "m.especialidade = :especialidade AND " +
            "m.ativo = true AND " +
            "m.id NOT IN (" +
            "SELECT c.medico.id " +
            "FROM Consulta c " +
            "WHERE c.dataHora = :dataHora " +
            "AND c.medico.especialidade IS NULL) " +
            "ORDER BY RAND() " +
            "LIMIT 1")
    Medico findFirstByEspecialidadeAndAtivoAndIdNotIn(
            @Param("especialidade") Especialidade especialidade,
            @Param("dataHora") LocalDateTime dataHora
    );

    /**
     * Obtém o status de ativação de um médico pelo ID.
     *
     * @param idMedico O ID do médico.
     * @return True se o médico estiver ativo, caso contrário False.
     */
    @Query("""
            SELECT m.ativo 
            FROM Medico m 
            WHERE m.id = :idMedico
        """)
    Boolean findAtivoById(Long idMedico);

}