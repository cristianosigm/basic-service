package com.cs.sigm.exception.handler;

import com.cs.sigm.dto.ErrorResponseDTO;
import com.cs.sigm.exception.*;
import com.cs.sigm.util.CSUserSettings;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class CSExceptionHandler {

    @Autowired
    private CSUserSettings settings;

    @ExceptionHandler(value = {AttributeValueException.class})
    public ResponseEntity<Object> handleException(AttributeValueException ex) {
        log.error("Exception caught by Handler: " + ex.getMessage(), ex);
        return new ResponseEntity<>(parseError(HttpStatus.BAD_REQUEST, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<Object> handleException(BadCredentialsException ex) {
        log.error("Exception caught by Handler: " + ex.getMessage(), ex);
        return new ResponseEntity<>(parseError(HttpStatus.UNAUTHORIZED, ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {CSAuthenticationException.class})
    public ResponseEntity<Object> handleException(CSAuthenticationException ex) {
        log.error("Exception caught by Handler: " + ex.getMessage(), ex);
        return new ResponseEntity<>(parseError(HttpStatus.UNAUTHORIZED, settings.getMessage("exception.expired")), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {CSAuthorizationException.class})
    public ResponseEntity<Object> handleException(CSAuthorizationException ex) {
        log.error("Exception caught by Handler: " + ex.getMessage(), ex);
        return new ResponseEntity<>(parseError(HttpStatus.FORBIDDEN, ex.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleException(DataIntegrityViolationException ex) {
        log.error("Exception caught by Handler: " + ex.getMessage() + "; cause: " + ex.getCause(), ex);
        final String msg = ex.getMessage();
        if (msg.contains("person_email_key") || msg.contains("csuser_username_key")) {
            return new ResponseEntity<>(parseError(HttpStatus.CONFLICT, settings.getMessage("exception.conflict")), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(parseError(HttpStatus.CONFLICT, msg), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {EntityValidationException.class})
    public ResponseEntity<Object> handleException(EntityValidationException ex) {
        log.error("Exception caught by Handler: " + ex.getMessage(), ex);
        return new ResponseEntity<>(parseError(HttpStatus.BAD_REQUEST, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {EntryNotFoundException.class})
    public ResponseEntity<Object> handleException(EntryNotFoundException ex) {
        log.error("Exception caught by Handler: " + ex.getMessage(), ex);
        return new ResponseEntity<>(parseError(HttpStatus.NOT_FOUND, settings.getMessage("exception.notFound")), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {JwtException.class})
    public ResponseEntity<Object> handleException(JwtException ex) {
        log.error("Exception caught by Handler: " + ex.getMessage(), ex);
        return new ResponseEntity<>(parseError(HttpStatus.UNAUTHORIZED, ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {LockedException.class})
    public ResponseEntity<Object> handleException(LockedException ex) {
        log.error("Exception caught by Handler: " + ex.getMessage(), ex);
        return new ResponseEntity<>(parseError(HttpStatus.UNAUTHORIZED, settings.getMessage("exception.locked")), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {MailSendException.class})
    public ResponseEntity<Object> handleException(MailSendException ex) {
        log.error("Exception caught by Handler: " + ex.getMessage(), ex);
        return new ResponseEntity<>(parseError(HttpStatus.INTERNAL_SERVER_ERROR, settings.getMessage("exception.mailSend")), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleException(MethodArgumentNotValidException ex) {
        log.error("Exception caught by Handler: " + ex.getMessage(), ex);
        return new ResponseEntity<>(parseError(HttpStatus.BAD_REQUEST, settings.getMessage("exception.argument", ArrayUtils.toArray(ex.getErrorCount())), ex.getBindingResult().getAllErrors()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {PasswordValidationException.class})
    public ResponseEntity<Object> handleException(PasswordValidationException ex) {
        log.error("Exception caught by Handler: " + ex.getMessage(), ex);
        return new ResponseEntity<>(parseError(HttpStatus.BAD_REQUEST, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<Object> handleGeneralException(RuntimeException ex) {
        log.error("Exception caught by Handler (general): " + ex.getMessage(), ex);
        return new ResponseEntity<>(parseError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorResponseDTO parseError(HttpStatus status, String message) {
        String[] errors = {message};
        if (StringUtils.hasText(message) && message.contains("#")) {
            errors = message.split("#");
        }
        // @formatter:off
        return ErrorResponseDTO.builder()
                .httpStatus(ObjectUtils.isEmpty(status) ? "" : status.name())
                .message(errors[0])
                .errors(List.of(errors).stream()
                    .skip(1L)
                    .collect(Collectors.toList()))
                .build();
        // @formatter:on
    }

    private ErrorResponseDTO parseError(HttpStatus status, String message, List<ObjectError> errorList) {
        // @formatter:off
        return ErrorResponseDTO.builder()
                .httpStatus(ObjectUtils.isEmpty(status) ? "" : status.name())
                .message(message)
                .errors(
                    errorList.stream()
                        .map(e -> ((FieldError)e).getField() + ": " + e.getDefaultMessage())
                        .collect(Collectors.toList()))
                .build();
        // @formatter:on
    }

}
