/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group3.Assignment30.model.dao;

import com.group3.Assignment30.model.entity.Product;
import com.group3.Assignment30.model.entity.QProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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
        JPAQueryFactory queryFactory = new JPAQueryFactory(getEntityManager());
        QProduct product = QProduct.product;
        
        List<Product> p = queryFactory.selectFrom(product).where(product.product_name.eq(name)).fetch();
        
        return p;
        
    }
    
    // CREATE TEST FOR THIS METHOD!
    public List<Product> getXUniqueProducts(long count){
        JPAQueryFactory queryFactory = new JPAQueryFactory(getEntityManager());
        QProduct product = QProduct.product;
        
        List<Product> temp = queryFactory.selectFrom(product).orderBy(product.product_name.desc()).fetch();
        
        List<Product> p = new ArrayList<Product>();
        
        String current = "";
        for (Product prod: temp) {
            if (!prod.getProduct_name().equals(current)){
                p.add(prod);
                current = prod.getProduct_name();
                if (p.size() == count) break;
            }
        }
        return p;
    }
   
    public List<Product>  getProductByID(int product_id){
        
        JPAQueryFactory queryFactory = new JPAQueryFactory(getEntityManager());
        QProduct product = QProduct.product;
        
        List<Product> p = queryFactory.selectFrom(product).where(product.prodoct_id.eq(product_id)).fetch();
        
        return p;
    
    }
    
    public void cleanAll(){
        em.createQuery("DELETE FROM Product where 1=1").executeUpdate();
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
  
}
