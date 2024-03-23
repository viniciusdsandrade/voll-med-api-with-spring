package com.restful.api.entity.validation.cancelamento;

import com.restful.api.dto.consulta.DadosCancelamentoConsulta;
import com.restful.api.exception.ValidacaoException;
import com.restful.api.repository.ConsultaRepository;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidadorDeHorarioAntecedenciaCancelamento")
public class ValidadorHorarioAntecedencia implements ValidadorCancelamentoConsulta {

    private final ConsultaRepository consultaRepository;

    public ValidadorHorarioAntecedencia(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    @Override
    public void validar(DadosCancelamentoConsulta dados) {

        var consulta = consultaRepository.findById(dados.idConsulta()).orElseThrow(() -> new ValidacaoException("Consulta não encontrada"));
        var agora = LocalDateTime.now();
        var diferencaEmHoras = Duration.between(agora, consulta.getDataHora()).toHours();

        if (diferencaEmHoras < 24)
            throw new ValidacaoException("A consulta deve ser cancelada com pelo menos 24 horas de antecedência.");
    }
}
