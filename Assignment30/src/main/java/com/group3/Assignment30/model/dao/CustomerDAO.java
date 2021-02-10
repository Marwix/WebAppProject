/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group3.Assignment30.model.dao;
import com.group3.Assignment30.model.entity.Customer;
import com.group3.Assignment30.model.entity.QCustomer;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.Getter;

@Stateless
public class CustomerDAO extends AbstractDAO<Customer> {
    @Getter @PersistenceContext(unitName = "BigStoreDB")
    private EntityManager em;
 
    public CustomerDAO() {
        super(Customer.class);
    }

    public List<Customer> getUserInformationByID(int user_id) {
        // Get row with user information with ID
        JPAQueryFactory queryFactory = new JPAQueryFactory(getEntityManager());
        QCustomer user = QCustomer.customer;

        List<Customer> u = queryFactory.selectFrom(user).where(user.user_id.eq(user_id)).fetch();
        return u;
    }

    public List<Customer> checkRegistered(String email) {
        // Check if user exists with email from database.
        JPAQueryFactory queryFactory = new JPAQueryFactory(getEntityManager());
        QCustomer user = QCustomer.customer;

        List<Customer> u = queryFactory.selectFrom(user).where(user.email.eq(email)).fetch();
        return u;
    }

    public List<Customer> checkUserLogin(String email, String password) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(getEntityManager());
        QCustomer user = QCustomer.customer;
        
        List<Customer> u = queryFactory.selectFrom(user).where(user.email.eq(email), user.password.eq(password)).fetch();
        return u;
    }
    
    public List<Customer> checkUserExist(String email) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(getEntityManager());
        QCustomer user = QCustomer.customer;
        
        List<Customer> u = queryFactory.selectFrom(user).where(user.email.eq(email)).fetch();
        return u;
    }
    public void cleanAll(){
        em.createQuery("DELETE FROM Customer where 1=1").executeUpdate();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
