/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algaworks.springboot.repository;

import com.algaworks.springboot.model.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Arthur
 */
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
    
}
