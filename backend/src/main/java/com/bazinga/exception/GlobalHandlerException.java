package com.bazinga.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import com.bazinga.exception.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ResponseStatus(HttpStatus.NOT_FOUND)
@RestControllerAdvice
public class GlobalHandlerException extends ResponseEntityExceptionHandler {

    protected final Log logger = LogFactory.getLog(this.getClass());


    @ExceptionHandler(TimeNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleTimeNaoEncontrado(TimeNaoEncontradoException exception) {
        logger.error(exception);
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Time não encontrado");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JogadorNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleJogadorNaoEncontrado(JogadorNaoEncontradoException exception) {
        logger.error(exception);
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Jogador não encontrado");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
