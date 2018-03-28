/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algaworks.springboot.event;

import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author Arthur
 */
public class ResourceCreatedEvent  extends ApplicationEvent{
    
    private HttpServletResponse response;

    private Long cod;
    
    public ResourceCreatedEvent(Object source, HttpServletResponse response, Long cod) {
        super(source);
        this.response = response;
        this.cod = cod;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public Long getCod() {
        return cod;
    }
    
    
    
}
