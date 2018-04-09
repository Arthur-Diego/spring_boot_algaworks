/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algaworks.springboot.resource;

import com.algaworks.springboot.model.Categoria;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Arthur
 */
public class CategoriaResourceTest {
    
    @Test
    public void testSome(){
        Categoria cat = new Categoria();
        
        cat.setCodigo(1l);
        
        assertEquals(1l, cat.getCodigo());
    }
    
}
