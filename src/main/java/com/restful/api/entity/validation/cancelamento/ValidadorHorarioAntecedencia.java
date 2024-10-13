package com.restful.api.entity.validation.cancelamento;

import com.restful.api.dto.consulta.DadosCancelamentoConsulta;
import com.restful.api.exception.ValidacaoException;
import com.restful.api.repository.ConsultaRepository;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Validador que verifica se o cancelamento da consulta foi feito com pelo menos 24 horas de antecedência.
 * <p>
 * Se a consulta for cancelada com menos de 24 horas de antecedência, uma {@link ValidacaoException} será lançada.
 */
@Component("ValidadorDeHorarioAntecedenciaCancelamento")
public class ValidadorHorarioAntecedencia implements ValidadorCancelamentoConsulta {

    private final ConsultaRepository consultaRepository;

    /**
     * Construtor que injeta o repositório de consultas necessário para validações.
     *
     * @param consultaRepository O repositório de consultas para verificar a existência e horário da consulta.
     */
    public ValidadorHorarioAntecedencia(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    /**
     * Valida se a consulta está sendo cancelada com antecedência mínima de 24 horas.
     * <p>
     * Caso a consulta esteja muito próxima do horário agendado (menos de 24 horas),
     * lança uma {@link ValidacaoException}.
     *
     * @param dados Os dados de cancelamento da consulta.
     * @throws ValidacaoException Se o cancelamento ocorrer com menos de 24 horas de antecedência.
     */
    @Override
    public void validar(DadosCancelamentoConsulta dados) {

        var consulta = consultaRepository.findById(dados.idConsulta())
                .orElseThrow(() -> new ValidacaoException("Consulta não encontrada"));
        var agora = LocalDateTime.now();
        var diferencaEmHoras = Duration.between(agora, consulta.getDataHora()).toHours();

        if (diferencaEmHoras < 24) throw new ValidacaoException("A consulta deve ser cancelada com pelo menos 24 horas de antecedência.");
    }
}
