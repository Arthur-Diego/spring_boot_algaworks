/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algaworks.springboot.service;

import com.algaworks.springboot.model.Pessoa;
import com.algaworks.springboot.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Arthur
 */
@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repository;

    public Pessoa update(Pessoa pessoa, Long cod) {
        Pessoa pessoaFind = findPessoaByCodigo(cod);
        BeanUtils.copyProperties(pessoa, pessoaFind, "codigo");
        return repository.save(pessoaFind);
    }
    
    public void updatePropertyActive(Long cod, Boolean ativo){
        Pessoa pessoaSaved = findPessoaByCodigo(cod);
        pessoaSaved.setAtivo(ativo);
        repository.save(pessoaSaved);
    }
    
    private Pessoa findPessoaByCodigo(Long cod) throws EmptyResultDataAccessException {
        Pessoa pessoaFind = repository.findById(cod).get();
        if (pessoaFind == null) {
            throw new EmptyResultDataAccessException(1);
        }
        return pessoaFind;
    }

}
