package com.restful.api.repository;

import com.restful.api.entity.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("pacienteRepository")
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    /**
     * Retorna uma página de pacientes ativos de acordo com a paginação fornecida.
     *
     * @param paginacao As informações de paginação para a consulta.
     * @return Uma página de pacientes ativos.
     */
    Page<Paciente> findAllByAtivoTrue(Pageable paginacao);

    /**
     * Verifica se existe algum paciente cadastrado com o email especificado.
     *
     * @param email O email do paciente a ser verificado.
     * @return True se existir algum paciente cadastrado com o email especificado, false caso contrário.
     */
    boolean existsByEmail(String email);

    /**
     * Verifica se existe algum paciente cadastrado com o CPF especificado.
     *
     * @param cpf O CPF do paciente a ser verificado.
     * @return True se existir algum paciente cadastrado com o CPF especificado, false caso contrário.
     */
    boolean existsByCpf(String cpf);

    /**
     * Obtém o status de ativação de um paciente pelo ID.
     *
     * @param idPaciente O ID do paciente.
     * @return True se o paciente estiver ativo, false caso contrário.
     */
    @Query("""
                SELECT p.ativo 
                FROM Paciente p 
                WHERE p.id = :idPaciente
            """)
    boolean findAtivoById(Long idPaciente);
}