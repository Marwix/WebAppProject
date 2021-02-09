/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group3.mavenproject1.model.dao;

import com.group3.mavenproject1.model.entity.Product;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.Getter;


@Stateless
public class ProductDao extends AbstractDAO<Product> {
    @Getter @PersistenceContext(unitName = "BigStoreDB")
    private EntityManager em;
    
    public ProductDao() {
        super(Product.class);
    }
    
    

    @Override
    protected EntityManager getEntityManager() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
