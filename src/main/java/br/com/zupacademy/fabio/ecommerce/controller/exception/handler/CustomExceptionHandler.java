package br.com.zupacademy.fabio.ecommerce.controller.exception.handler;

import br.com.zupacademy.fabio.ecommerce.controller.exception.StandardError;
import br.com.zupacademy.fabio.ecommerce.controller.exception.ValidationFieldsError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class CustomExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ValidationFieldsError> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<ValidationFieldsError> listValidationFieldsError = new ArrayList<>();

        List<FieldError> listFieldErrors = exception.getBindingResult().getFieldErrors();

        listFieldErrors.forEach(fieldError -> {
            String messageContext = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());

            ValidationFieldsError validationFieldsError = new ValidationFieldsError(LocalDateTime.now(),
                    HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(), exception.getClass().toString(),
                    fieldError.getField(), messageContext);

            listValidationFieldsError.add(validationFieldsError);
        });
        return listValidationFieldsError;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public StandardError handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        return new StandardError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.toString(), exception.getClass().toString());
    }


    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public StandardError handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException exception) {
        return new StandardError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.toString(), exception.getClass().toString());
    }
}
