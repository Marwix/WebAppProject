/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group3.Assignment30.model.dao;
import com.group3.Assignment30.model.entity.Customer;
import com.group3.Assignment30.model.entity.QCustomer;
import com.group3.Assignment30.service.PasswordManager;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jdk.internal.misc.Signal;
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
    
    public boolean updateUserInformation(Customer customer){
        JPAQueryFactory queryFactory = new JPAQueryFactory(getEntityManager());
        QCustomer user = QCustomer.customer;
        
        long res = queryFactory.update(user).set(user.email, customer.getEmail())
                .set(user.first_name, customer.getFirst_name())
                .set(user.last_name, customer.getLast_name())
                .set(user.phonenumber, customer.getPhonenumber())
                .set(user.adress, customer.getAdress())
                .set(user.postal_code, customer.getPostal_code())
                .set(user.city, customer.getCity())
                .where(user.user_id.eq(customer.getUser_id())).execute();
        
        return res == 1;
    }
    
    public void changePassword(Customer customer){
        PasswordManager pwManager = new PasswordManager();
        List<byte[]> pw = pwManager.HashNSalt(customer.getPassword());
        JPAQueryFactory queryFactory = new JPAQueryFactory(getEntityManager());
        QCustomer user = QCustomer.customer;
        
        queryFactory.update(user).set(user.password, pwManager.passwordByteArrToString(pw.get(1)))
                .set(user.salt, pw.get(0))
                .where(user.user_id.eq(customer.getUser_id())).execute();
    }
    
    public void cleanAll(){
        em.createQuery("DELETE FROM Customer where 1=1").executeUpdate();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
