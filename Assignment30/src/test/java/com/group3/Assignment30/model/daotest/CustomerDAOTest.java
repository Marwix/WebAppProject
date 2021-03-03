package com.group3.Assignment30.model.daotest;

import com.group3.Assignment30.model.dao.CustomerDAO;
import com.group3.Assignment30.model.entity.Customer;
import com.group3.Assignment30.service.PasswordManager;
import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.ejb.EJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class CustomerDAOTest {
    
    private Customer user1;
    private Customer user2;
    private Customer user3;
    private Customer user4;
    private PasswordManager pwManager;
    
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addClasses(CustomerDAO.class, Customer.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    
    @EJB
    private CustomerDAO customerDAO;
    
    @Before
    public void init() {
        pwManager = new PasswordManager();
        List<byte[]> passwordInfo;
        
        user1 =  new Customer();
        user1.setAdress("Goteland");
        user1.setCity("Gothenburg");
        user1.setEmail("allan-ridha@live.se");
        user1.setFirst_name("Allan");
        user1.setLast_name("Ridha");
        passwordInfo = pwManager.HashNSalt("Password1");
        user1.setPassword(pwManager.passwordByteArrToString(passwordInfo.get(1)));
        user1.setPhonenumber("0760272740");
        user1.setPostal_code("42455");
        user1.setSalt(passwordInfo.get(0));
        
        user2 =  new Customer();
        user2.setAdress("Goteland");
        user2.setCity("Gothenburg");
        user2.setEmail("test@mail.se");
        user2.setFirst_name("Lars");
        user2.setLast_name("Svensson");
        passwordInfo = pwManager.HashNSalt("Password12");
        user2.setPassword(pwManager.passwordByteArrToString(passwordInfo.get(1)));
        user2.setPhonenumber("0760272740");
        user2.setPostal_code("42455");
        user2.setSalt(passwordInfo.get(0));
        
        user3 =  new Customer();
        user3.setAdress("Goteland");
        user3.setCity("Gothenburg");
        user3.setEmail("mojje@mail.se");
        user3.setFirst_name("Mojtaba");
        user3.setLast_name("Ataie");
        passwordInfo = pwManager.HashNSalt("Password123");
        user3.setPassword(pwManager.passwordByteArrToString(passwordInfo.get(1)));
        user3.setPhonenumber("0760272740");
        user3.setPostal_code("42455");
        user3.setSalt(passwordInfo.get(0));
        
        user4 =  new Customer();
        user4.setAdress("Goteland");
        user4.setCity("Gothenburg");
        user4.setEmail("allan-rid@testmail.se");
        user4.setFirst_name("Ridha");
        user4.setLast_name("Lorensson");
        passwordInfo = pwManager.HashNSalt("Password1234");
        user4.setPassword(pwManager.passwordByteArrToString(passwordInfo.get(1)));
        user4.setPhonenumber("0760272740");
        user4.setPostal_code("42455");
        user4.setSalt(passwordInfo.get(0));
        
        customerDAO.create(user1);
        customerDAO.create(user2);
        customerDAO.create(user3);
        customerDAO.create(user4);
    }
    
    
    @InSequence(0)
    @Test
    public void checkIfWeGetCorrectCustomerWithID() {
        
        Customer p1;
        Customer p2;
        
        List<Customer> value1 = customerDAO.getUserInformationByID(1);
        List<Customer> value2 = customerDAO.getUserInformationByID(2);
        
        p1 = value1.get(0);
        p2 = value2.get(0);
        
        assertEquals(user1, p1);
        assertEquals(user2, p2);     
    }
    
    @InSequence(1)
    @Test
    public void checkIfRegistered() {
        List<Customer> getUsers = customerDAO.checkRegistered("allan-ridha@live.se");
        assertTrue(getUsers.contains(user1));
        assertFalse(getUsers.contains(user2));
        assertFalse(getUsers.contains(user3));
        assertFalse(getUsers.contains(user4));
    }
    
    @InSequence(2)
    @Test
    public void checkIfUserRemoved() {
        List<Customer> customers = customerDAO.findAll();
        // Check if all users exist.
        assertTrue(customers.contains(user1));
        assertTrue(customers.contains(user2));
        assertTrue(customers.contains(user3));
        assertTrue(customers.contains(user4));
        
        // Remove user from table
        customerDAO.remove(user2);
        
        // Re-check table if user2 is gone.
        List<Customer> recustomers = customerDAO.findAll();
        assertTrue(recustomers.contains(user1));
        assertFalse(recustomers.contains(user2));
        assertTrue(recustomers.contains(user3));
        assertTrue(recustomers.contains(user4));
    }
    
    @InSequence(3)
    @Test
    public void checkIfEmailAlreadyExist() {
        // Check if new customer already exists in list of customers.
        List<Customer> customers = customerDAO.checkRegistered(user1.getEmail());
        assertTrue(customers.contains(user1));
    }
    
    @InSequence(4)
    @Test
    public void updateUserInfoTest(){
        user1.setEmail("updateemail@email.se");
        customerDAO.updateUserInformation(user1);
        
        List<Customer> cust1 = customerDAO.checkRegistered("updateemail@email.se");
        List<Customer> cust2 = customerDAO.getUserInformationByID(user1.getUser_id());
        
        assertTrue(cust1.size() == cust2.size());
        assertEquals(cust1.get(0), cust2.get(0));
        assertNotEquals(cust1.get(0), user3);
        assertNotEquals(cust2.get(0), user3);
    }
    
    @InSequence(5)
    @Test
    public void updatePasswordTest(){
        //Store original password
        String oldPw = user1.getPassword();
        
        String newPw = "newpassword76";
        
        // Set the new password in customer instance
        user1.setPassword(newPw);
        
        // Update password in database
        customerDAO.changePassword(user1);
        
        // Update User1 record from DB
        user1 = customerDAO.getUserInformationByID(user1.getUser_id()).get(0);
        
        // Make sure changes have been made
        assertTrue(pwManager.passwordMatching(user1.getPassword(), user1.getSalt(), newPw));
        assertNotEquals(user1.getPassword(), oldPw);
        
    }
    
    @After
    public void cleanup(){
       customerDAO.cleanAll();
    }
}
