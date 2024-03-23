package com.restful.api.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manipula exceções globais lançadas por controladores REST.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Manipula a exceção de entidade não encontrada.
     *
     * @param exception   A exceção de entidade não encontrada.
     * @param webRequest  O objeto WebRequest que fornece informações sobre a solicitação.
     * @return            Uma ResponseEntity contendo detalhes do erro e status HTTP 404 (Not Found).
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<List<ErrorDetails>> handleResourceNotFoundException(EntityNotFoundException exception,
                                                                              WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),                            // Data e hora do erro.
                exception.getMessage(),                         // Mensagem de erro da exceção.
                webRequest.getDescription(false),              // Descrição da solicitação.
                "RESOURCE_NOT_FOUND"                           // Tipo de erro.
        );

        return new ResponseEntity<>(List.of(errorDetails), HttpStatus.NOT_FOUND);
    }

    /**
     * Manipula a exceção de violação de integridade dos dados.
     *
     * @param exception   A exceção de violação de integridade dos dados.
     * @param webRequest  O objeto WebRequest que fornece informações sobre a solicitação.
     * @return            Uma ResponseEntity contendo detalhes do erro e status HTTP 400 (Bad Request).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorDetails>> handleDataIntegrityViolationException(MethodArgumentNotValidException exception,
                                                                                    WebRequest webRequest) {
        // Obtém a lista de erros de campo da exceção.
        var errors = exception.getFieldErrors();

        // Mapeia os erros de campo para objetos ErrorDetails e os coleta em uma lista.
        List<ErrorDetails> errorDetailsList = errors.stream()
                .map(ErrorDetails::new)
                .collect(Collectors.toList());

        // Retorna uma ResponseEntity contendo a lista de ErrorDetails e o status HTTP 400 (Bad Request).
        return new ResponseEntity<>(errorDetailsList, HttpStatus.BAD_REQUEST);
    }

    /**
     * Manipula a exceção de entrada duplicada.
     *
     * @param exception   A exceção de entrada duplicada.
     * @param webRequest  O objeto WebRequest que fornece informações sobre a solicitação.
     * @return            Uma ResponseEntity contendo detalhes do erro e status HTTP 409 (Conflict).
     */
    @ExceptionHandler(DuplicateEntryException.class)
    public ResponseEntity<List<ErrorDetails>> handleDuplicateEntryException(DuplicateEntryException exception,
                                                                            WebRequest webRequest) {
        // Cria um objeto ErrorDetails para encapsular os detalhes do erro.
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "DUPLICATE_ENTRY"
        );

        // Retorna uma ResponseEntity contendo a lista de ErrorDetails e o status HTTP 409 (Conflict).
        return ResponseEntity.status(HttpStatus.CONFLICT).body(List.of(errorDetails));
    }

    /**
     * Manipula exceções globais não especificadas.
     *
     * @param exception   A exceção global.
     * @param webRequest  O objeto WebRequest que fornece informações sobre a solicitação.
     * @return            Uma ResponseEntity contendo detalhes do erro e status HTTP 500 (Internal Server Error).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception,
                                                              WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "INTERNAL_SERVER_ERROR"
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Manipula exceções do tipo AccessDeniedException.
     *
     * @param exception a exceção lançada
     * @param webRequest a requisição web atual
     * @return ResponseEntity contendo os detalhes do erro e o status HTTP FORBIDDEN
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> handleAccessDeniedException(Exception exception,
                                                                    WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "ACCESS_DENIED"
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }

    /**
     * Manipula exceções do tipo AuthenticationException.
     *
     * @param exception a exceção lançada
     * @param webRequest a requisição web atual
     * @return ResponseEntity contendo os detalhes do erro e o status HTTP UNAUTHORIZED
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorDetails> handleAuthenticationException(Exception exception,
                                                                      WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "UNAUTHORIZED"
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Manipula exceções do tipo BadCredentialsException.
     *
     * @param exception a exceção lançada
     * @param webRequest a requisição web atual
     * @return ResponseEntity contendo os detalhes do erro e o status HTTP UNAUTHORIZED
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDetails> handleBadCredentialsException(Exception exception,
                                                                      WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "BAD_CREDENTIALS"
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Manipula exceções do tipo BadCredentialsException.
     *
     * @param exception a exceção lançada
     * @param webRequest a requisição web atual
     * @return ResponseEntity contendo os detalhes do erro e o status HTTP UNAUTHORIZED
     */
    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<List<ErrorDetails>> handleValidacaoException(ValidacaoException exception,
                                                                  WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "VALIDATION_ERROR"
        );

        return new ResponseEntity<>(List.of(errorDetails), HttpStatus.BAD_REQUEST);
    }
}