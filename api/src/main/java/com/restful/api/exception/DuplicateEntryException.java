package com.restful.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando ocorre uma tentativa de adicionar uma entrada duplicada.
 * Esta exceção é geralmente lançada em operações de inserção em que a entrada já existe.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateEntryException extends RuntimeException {

    /**
     * Construtor para criar uma nova instância de DuplicateEntryException com uma mensagem de erro personalizada.
     *
     * @param message A mensagem de erro detalhando a natureza do problema.
     */
    public DuplicateEntryException(String message) {
        super(message);
    }
}