/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group3.Assignment30.model.dao;

import com.group3.Assignment30.model.entity.Customer;
import com.group3.Assignment30.model.entity.Purchase;
import com.group3.Assignment30.model.entity.QPurchase;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.Getter;


@Stateless
public class PurchaseDAO extends AbstractDAO<Purchase> {
    @Getter @PersistenceContext(unitName = "BigStoreDB")
    private EntityManager em;

    public PurchaseDAO() {
        super(Purchase.class);
    }
   
    public List<Purchase>  getOrderByCustumer(Customer customer){
        
        JPAQueryFactory queryFactory = new JPAQueryFactory(getEntityManager());
        QPurchase purchase = QPurchase.purchase;
        
        List<Purchase> orders = queryFactory.selectFrom(purchase).where(purchase.customer.eq(customer)).orderBy(purchase.time.desc()).fetch();
        
        return orders;
    
    }
    
    public List<Purchase>  getOrderPurchasesByCustomer(Customer customer){
        
        JPAQueryFactory queryFactory = new JPAQueryFactory(getEntityManager());
        QPurchase purchase = QPurchase.purchase;
        
        List<Purchase> orders = queryFactory.selectFrom(purchase).where(purchase.customer.eq(customer)).fetch();
        
        return orders;
    
    }
    
    public List<Purchase> getOrdersByOrderID(int order_id){
        JPAQueryFactory queryFactory = new JPAQueryFactory(getEntityManager());
        QPurchase purchase = QPurchase.purchase;
        
        List<Purchase> orders = queryFactory.selectFrom(purchase).where(purchase.order_id.eq(order_id)).fetch();
        
        return orders;
    }
    
    public void cleanAll(){
        em.createQuery("DELETE FROM Purchase where 1=1").executeUpdate();
    }
    
    
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    
    
}
