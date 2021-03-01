//package com.group3.Assignment30.model.daotest;
//
//import com.group3.Assignment30.model.dao.CustomerDAO;
//import com.group3.Assignment30.model.entity.Customer;
//import com.querydsl.core.QueryFactory;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import java.util.List;
//import javax.ejb.EJB;
//import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.arquillian.junit.Arquillian;
//import org.jboss.arquillian.junit.InSequence;
//import org.jboss.shrinkwrap.api.ShrinkWrap;
//import org.jboss.shrinkwrap.api.asset.EmptyAsset;
//import org.jboss.shrinkwrap.api.spec.WebArchive;
//import org.junit.After;
//import org.junit.AfterClass;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//@RunWith(Arquillian.class)
//public class CustomerDAOTest {
//    
//    private Customer user1;
//    private Customer user2;
//    private Customer user3;
//    private Customer user4;
//    
//    @Deployment
//    public static WebArchive createDeployment() {
//        return ShrinkWrap.create(WebArchive.class)
//                .addClasses(CustomerDAO.class, Customer.class)
//                .addAsResource("META-INF/persistence.xml")
//                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
//    }
//    
//    @EJB
//    private CustomerDAO customerDAO;
//    
//    @Before
//    public void init() {
//        user1 =  new Customer(1, "Allan", "Ridha", "allan-ridha@live.se", "Password1", "0760272740", "Gothenburg", "Goteland", "42455");
//        user2 =  new Customer(2, "Lars", "Svensson", "test@mail.se", "Password12", "0760272740", "Gothenburg", "Goteland", "42455");
//        user3 =  new Customer(3, "Mojtaba", "Ataie", "mojje@mail.se", "Password123", "0760272740", "Gothenburg", "Goteland", "42455");
//        user4 =  new Customer(4, "Ridha", "Lorensson", "allan-rid@testmail.se", "Password1234", "0760272740", "Gothenburg", "Goteland", "42455");
//        
//        customerDAO.create(user1);
//        customerDAO.create(user2);
//        customerDAO.create(user3);
//        customerDAO.create(user4);
//    }
//    
//    
//    @InSequence(0)
//    @Test
//    public void checkIfWeGetCorrectCustomerWithID() {
//        
//        Customer p1;
//        Customer p2;
//        
//        List<Customer> value1 = customerDAO.getUserInformationByID(1);
//        List<Customer> value2 = customerDAO.getUserInformationByID(2);
//        
//        p1 = value1.get(0);
//        p2 = value2.get(0);
//        
//        assertEquals(user1, p1);
//        assertEquals(user2, p2);     
//    }
//    
//    @InSequence(1)
//    @Test
//    public void checkIfRegistered() {
//        List<Customer> getUsers = customerDAO.checkRegistered("allan-ridha@live.se");
//        assertTrue(getUsers.contains(user1));
//        assertFalse(getUsers.contains(user2));
//        assertFalse(getUsers.contains(user3));
//        assertFalse(getUsers.contains(user4));
//    }
//    
//    @InSequence(2)
//    @Test
//    public void checkLoginCredentials() {
//        // Test real credentials
//        List<Customer> login1 = customerDAO.checkUserLogin("allan-ridha@live.se", "Password1");
//        // Test invalid credentials
//        List<Customer> login2 = customerDAO.checkUserLogin("allan-ridha@live.se", "Password1234"); 
//        // Test different credentials (wrong)
//        List<Customer> login3 = customerDAO.checkUserLogin("mojje@mail.se", "Password1"); 
//        // Test a login with row which doesn't exist.
//        List<Customer> login4 = customerDAO.checkUserLogin("IDontExist@mail.se", "SomeRandomPassword");
//        
//        // Check if pass or email is valid/invalid and correct credentials
//        assertTrue(login1.contains(user1)); // Correct
//        assertFalse(login2.contains(user1)); // Invalid Password
//        assertFalse(login3.contains(user1)); // Invalid Email
//        
//        // Check all users if they match random data.
//        assertFalse(login4.contains(user1));
//        assertFalse(login4.contains(user2));
//        assertFalse(login4.contains(user3));
//        assertFalse(login4.contains(user4));
//    }
//    
//    @InSequence(3)
//    @Test
//    public void checkIfUserRemoved() {
//        List<Customer> customers = customerDAO.findAll();
//        // Check if all users exist.
//        assertTrue(customers.contains(user1));
//        assertTrue(customers.contains(user2));
//        assertTrue(customers.contains(user3));
//        assertTrue(customers.contains(user4));
//        
//        // Remove user from table
//        customerDAO.remove(user2);
//        
//        // Re-check table if user2 is gone.
//        List<Customer> recustomers = customerDAO.findAll();
//        assertTrue(recustomers.contains(user1));
//        assertFalse(recustomers.contains(user2));
//        assertTrue(recustomers.contains(user3));
//        assertTrue(recustomers.contains(user4));
//    }
//    
//    @InSequence(4)
//    @Test
//    public void checkIfUserAlreadyExist() {
//        // Check if new customer already exists in list of customers.
//        List<Customer> customers = customerDAO.findAll();
//        Customer p1 = new Customer(1, "Allan", "Ridha", "allan-ridha@live.se", "Password1", "0760272740", "Gothenburg", "Goteland", "42455");
//        assertTrue(customers.contains(p1));
//    }
//    
//    @After
//    public void cleanup(){
//       customerDAO.cleanAll();
//    }
//}
