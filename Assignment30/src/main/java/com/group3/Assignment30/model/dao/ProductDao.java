/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group3.Assignment30.model.dao;

import com.group3.Assignment30.model.entity.Product;
import com.group3.Assignment30.model.entity.QProduct;
import static com.group3.Assignment30.model.entity.QProduct.product;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import lombok.Getter;


@Stateless
public class ProductDAO extends AbstractDAO<Product> {
    @Getter @PersistenceContext(unitName = "BigStoreDB")
    private EntityManager em;
    
    
    public ProductDAO() {
        super(Product.class);
    }
    

    public List<Product> getProductByName(String name){
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("BigStoreDB");
        EntityManager em = emf.createEntityManager();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        
        List<Product> p = queryFactory.selectFrom(product).where(product.product_name.eq(name)).fetch();
        
        return p;
        
    }
    
    public Product getProductByID(int product_id){
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("BigStoreDB");
        EntityManager em = emf.createEntityManager();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        
         Product p = queryFactory.selectFrom(product).where(product.prodoct_id.eq(product_id)).fetchOne();
        
        return p;
    
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    
    
}
