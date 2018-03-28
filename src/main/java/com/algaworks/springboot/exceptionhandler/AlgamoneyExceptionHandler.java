/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algaworks.springboot.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author arthur
 */
@ControllerAdvice
public class AlgamoneyExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    MessageSource message;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String msgInvalida = message.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
        String msgErroDesenvolvedor = ex.getCause().toString();

        List<Erro> erros = Arrays.asList(new Erro(msgInvalida, msgErroDesenvolvedor));

        return handleExceptionInternal(ex, erros, headers, status, request); //To change body of generated methods, choose Tools | Templates.;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<Erro> erros = criarListaDeErros(ex.getBindingResult());
        return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);//To change body of generated methods, choose Tools | Templates.
    }

    @ExceptionHandler({EmptyResultDataAccessException.class})
    private ResponseEntity<Object> handleEmptyResultDataException(EmptyResultDataAccessException ex, WebRequest request) {
        String msgInvalida = message.getMessage("resource.not_found", null, LocaleContextHolder.getLocale());
        String msgErroDesenvolvedor = ex.toString();

        List<Erro> erros = Arrays.asList(new Erro(msgInvalida, msgErroDesenvolvedor));
        return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        String mensagemUsuario = message.getMessage("recurso.operacao-nao-permitida", null, LocaleContextHolder.getLocale());
        String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);
        List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
        return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    public List<Erro> criarListaDeErros(BindingResult result) {
        List<Erro> erros = new ArrayList<>();
        for (FieldError fieldError : result.getFieldErrors()) {

            String mensagemUsuario = message.getMessage(fieldError, LocaleContextHolder.getLocale());
            String mensagemDesenvolvedor = fieldError.toString();
            erros.add(new Erro(mensagemUsuario, mensagemDesenvolvedor));
        }

        return erros;
    }

    public static class Erro {

        String erroUsuario;
        String erroDesenvolvedor;

        public Erro(String erroUsuario, String erroDesenvolvedor) {
            this.erroUsuario = erroUsuario;
            this.erroDesenvolvedor = erroDesenvolvedor;
        }

        public String getErroUsuario() {
            return erroUsuario;
        }

        public void setErroUsuario(String erroUsuario) {
            this.erroUsuario = erroUsuario;
        }

        public String getErroDesenvolvedor() {
            return erroDesenvolvedor;
        }

        public void setErroDesenvolvedor(String erroDesenvolvedor) {
            this.erroDesenvolvedor = erroDesenvolvedor;
        }

    }

}
