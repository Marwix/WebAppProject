
package com.group3.Assignment30.model.dao;
import com.group3.Assignment30.model.entity.Customer;
import com.group3.Assignment30.model.entity.QCustomer;
import com.group3.Assignment30.service.PasswordManager;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.Getter;

@Stateless
public class CustomerDAO extends AbstractDAO<Customer> {
    @PersistenceContext(unitName = "BigStoreDB")
    private EntityManager em;
    private JPAQueryFactory queryFactory;
    private QCustomer user;
 
    public CustomerDAO() {
        super(Customer.class);
        user = QCustomer.customer;
    }

    public List<Customer> getUserInformationByID(int user_id) {
        // Get row with user information with ID
        
        List<Customer> u = getJPAQueryFactory().selectFrom(user).where(user.user_id.eq(user_id)).fetch();
        return u;
    }

    public List<Customer> checkRegistered(String email) {
        // Check if user exists with email from database.

        List<Customer> u = getJPAQueryFactory().selectFrom(user).where(user.email.eq(email)).fetch();
        return u;
    }
    
    public boolean updateUserInformation(Customer customer){

        long res = getJPAQueryFactory().update(user).set(user.email, customer.getEmail())
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
        
        getJPAQueryFactory().update(user).set(user.password, pwManager.passwordByteArrToString(pw.get(1)))
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

    @Override
    protected JPAQueryFactory getJPAQueryFactory() {
        queryFactory = new JPAQueryFactory(em);
       return queryFactory;
    }
}
