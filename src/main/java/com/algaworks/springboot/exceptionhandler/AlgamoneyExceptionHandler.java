/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algaworks.springboot.exceptionhandler;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author arthur
 */
@ControllerAdvice
public class AlgamoneyExceptionHandler extends ResponseEntityExceptionHandler{

    @Autowired
    MessageSource message;
    
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        
        String msgInvalida = message.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
        String msgErroDesenvolvedor = ex.getCause().toString();
        return handleExceptionInternal(ex, new Erro(msgInvalida, msgErroDesenvolvedor), headers, status, request); //To change body of generated methods, choose Tools | Templates.;
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
