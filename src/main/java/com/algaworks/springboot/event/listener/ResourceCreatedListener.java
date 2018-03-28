/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algaworks.springboot.event.listener;

import com.algaworks.springboot.event.ResourceCreatedEvent;
import java.net.URI;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author Arthur
 */
@Component
public class ResourceCreatedListener implements ApplicationListener<ResourceCreatedEvent> {

    @Override
    public void onApplicationEvent(ResourceCreatedEvent e) {
        HttpServletResponse response = e.getResponse();
        Long cod = e.getCod();
        addHeaderLocation(cod, response);
    }

    private void addHeaderLocation(Long cod, HttpServletResponse response) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}").
                buildAndExpand(cod).toUri();
        response.setHeader("location", uri.toASCIIString());
    }

}
