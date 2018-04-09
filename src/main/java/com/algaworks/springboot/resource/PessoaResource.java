/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algaworks.springboot.resource;

import com.algaworks.springboot.event.ResourceCreatedEvent;
import com.algaworks.springboot.model.Pessoa;
import com.algaworks.springboot.repository.PessoaRepository;
import com.algaworks.springboot.service.PessoaService;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Arthur
 */
@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

    @Autowired
    private PessoaRepository repository;
    
    @Autowired
    private PessoaService service;

    @Autowired
    private ApplicationEventPublisher publisher;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Pessoa> create(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {

        Pessoa pessoaCreated = repository.save(pessoa);
        
        publisher.publishEvent(new ResourceCreatedEvent(this, response, pessoaCreated.getCodigo()));
        
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaCreated);
    }

//    @GetMapping("/{codigo}")
//    public ResponseEntity<Pessoa> buscarPeloCodigo(@PathVariable Long codigo) {
//        return Optional.ofNullable(repository.findOne(codigo)).map(pessoa -> ResponseEntity.ok().body(pessoa)).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//    
//    @DeleteMapping("/{cod}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void delete(@PathVariable Long cod){
//        repository.delete(cod);
//    }
    
    @PutMapping("/{cod}")
    public ResponseEntity<Pessoa> update(@PathVariable Long cod, @Valid @RequestBody Pessoa pessoa){
        
        Pessoa pessoaFind = service.update(pessoa, cod);
        return ResponseEntity.ok(pessoaFind);
    }
    
    @PutMapping("/{cod}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long cod, @RequestBody Boolean ativo){
        service.updatePropertyActive(cod, ativo);
    }
    
    

}
