package com.projetospring.awpag.api.exeptionHandler;

import com.projetospring.awpag.domain.exception.NegocioException;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,

                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ProblemDetail problemDetail=ProblemDetail.forStatus(status);
        problemDetail.setTitle("Um ou mais campos estão invalidos");
        problemDetail.setType(URI.create("https://algaworqs.com/erros/cammpos-invalido"));

        var fields=ex.getBindingResult().getAllErrors().stream()
                .collect(Collectors.toMap(error->((FieldError) error).getField(),
                   error->messageSource.getMessage(error, LocaleContextHolder.getLocale())));

        problemDetail.setProperty("fields",fields);

        return super.handleExceptionInternal(ex,problemDetail, headers, status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ProblemDetail handleNegocio(NegocioException e){
        ProblemDetail problemDetail=ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle(e.getMessage());
        problemDetail.setType(URI.create("https://algaworqs.com/erros/regra-de-negocio"));

        return problemDetail;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegritViolation(DataIntegrityViolationException e){
        ProblemDetail problemDetail=ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle("Recursso esta em uso");
        problemDetail.setType(URI.create("https://algaworqs.com/erros/recursso-em-uso"));

        return problemDetail;
    }
}
