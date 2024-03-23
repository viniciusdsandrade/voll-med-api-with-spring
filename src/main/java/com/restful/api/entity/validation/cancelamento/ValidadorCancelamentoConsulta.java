package com.restful.api.entity.validation.cancelamento;

import com.restful.api.dto.consulta.DadosCancelamentoConsulta;

public interface ValidadorCancelamentoConsulta {

    void validar(DadosCancelamentoConsulta dados);
}
