package com.group3.Assignment30.model.daotest;


import com.group3.Assignment30.model.dao.CustomerDAO;
import com.group3.Assignment30.model.dao.ProductDAO;
import com.group3.Assignment30.model.dao.PurchaseDAO;
import com.group3.Assignment30.model.entity.Customer;
import com.group3.Assignment30.model.entity.Purchase;
import com.group3.Assignment30.model.entity.Product;
import com.group3.Assignment30.service.PasswordManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.AfterCompletion;
import javax.ejb.EJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class PurchaseDAOTest {
    
    private Product p1;
    private Product p2;
    private Product p3;
    private Product p4;
    
    private Purchase o1;
    private Purchase o2;
    private Purchase o3;
    private Purchase o4;
    private Purchase o5;
    private Purchase o6;
    private Purchase o7;
    private Purchase o8;
    
    
    private Customer c1;
    private Customer c2;
    
    private List<Product> products;
    
    
    
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addClasses(PurchaseDAO.class, Purchase.class)
                .addClasses(CustomerDAO.class, Customer.class)
                .addClasses(ProductDAO.class, Product.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    
    @EJB
    private PurchaseDAO purchaseDAO;
    @EJB
    private CustomerDAO customerDAO;
    @EJB
    private ProductDAO productDAO;

    
    @Before
    public void init() {
        
        p1 = new Product();
        p1.setProduct_name("Catapult");
        p1.setPrice(30000);
        p1.setFullStar(4);
        p1.setColor("Blue");
        p1.setMeasurements("3mx3mx3m");
        p1.setDescription("TEST");
                
        p2 = new Product();
        p2.setProduct_name("Catapult");
        p2.setPrice(30000);
        p2.setFullStar(4);
        p2.setColor("Green");
        p2.setMeasurements("3mx3mx3m");
        p2.setDescription("TEST");
        
        p3 = new Product();
        p3.setProduct_name("Catapult");
        p3.setPrice(30000);
        p3.setFullStar(4);
        p3.setColor("Red");
        p3.setMeasurements("3mx3mx3m");
        p3.setDescription("TEST");
        
        p4 = new Product();
        p4.setProduct_name("Plane");
        p4.setPrice(700000);
        p4.setFullStar(4);
        p4.setColor("Metal");
        p4.setMeasurements("25mx12mx7m");
        p4.setDescription("TEST");
        
        productDAO.create(p1);
        productDAO.create(p2);
        productDAO.create(p3);
        productDAO.create(p4);
        
        
        PasswordManager pwManager = new PasswordManager();
        List<byte[]> passwordInfo;
        
        c1 =  new Customer();
        c1.setAdress("Goteland");
        c1.setCity("Gothenburg");
        c1.setEmail("allan-ridha@live.se");
        c1.setFirst_name("Allan");
        c1.setLast_name("Ridha");
        passwordInfo = pwManager.HashNSalt("Password1");
        c1.setPassword(pwManager.passwordByteArrToString(passwordInfo.get(1)));
        c1.setPhonenumber("0760272740");
        c1.setPostal_code("42455");
        c1.setSalt(passwordInfo.get(0));
        
        c2 =  new Customer();
        c2.setAdress("Goteland");
        c2.setCity("Gothenburg");
        c2.setEmail("test@mail.se");
        c2.setFirst_name("Lars");
        c2.setLast_name("Svensson");
        passwordInfo = pwManager.HashNSalt("Password12");
        c2.setPassword(pwManager.passwordByteArrToString(passwordInfo.get(1)));
        c2.setPhonenumber("0760272740");
        c2.setPostal_code("42455");
        c2.setSalt(passwordInfo.get(0));
        
        o1 = new Purchase();
        o1.setOrder_id(1);
        o1.setTime(LocalDate.of(2021, 2, 10));
        o1.setCustomer(c1);
        o1.setProducts(p1);
        o1.setCount(2);
        
        o2 = new Purchase();
        o2.setOrder_id(1);
        o2.setTime(LocalDate.of(2021, 2, 10));
        o2.setCustomer(c1);
        o2.setProducts(p2);
        o2.setCount(5);
        
        o3 = new Purchase();
        o3.setOrder_id(2);
        o3.setTime(LocalDate.of(2021, 2, 9));
        o3.setCustomer(c1);
        o3.setProducts(p3);
        o3.setCount(6);
        
        o4 = new Purchase();
        o4.setOrder_id(3);
        o4.setTime(LocalDate.of(2020, 2, 10));
        o4.setCustomer(c2);
        o4.setProducts(p4);
        o4.setCount(2);
        
        o5 = new Purchase();
        o5.setOrder_id(5);
        o5.setTime(LocalDate.of(2021, 1, 5));
        o5.setCustomer(c1);
        o5.setProducts(p3);
        o5.setCount(12);
        
        o6 = new Purchase();
        o6.setOrder_id(6);
        o6.setTime(LocalDate.of(2020, 2, 4));
        o6.setCustomer(c1);
        o6.setProducts(p4);
        o6.setCount(23);
        
        o7 = new Purchase();
        o7.setOrder_id(7);
        o7.setTime(LocalDate.of(2020, 2, 2));
        o7.setCustomer(c1);
        o7.setProducts(p1);
        o7.setCount(71);
        
        o8 = new Purchase();
        o8.setOrder_id(8);
        o8.setTime(LocalDate.of(2020, 1, 5));
        o8.setCustomer(c1);
        o8.setProducts(p2);
        o8.setCount(3);
        
        customerDAO.create(c1);
        customerDAO.create(c2);
        
        purchaseDAO.create(o1);
        purchaseDAO.create(o2);
        purchaseDAO.create(o3);
        purchaseDAO.create(o4);
        purchaseDAO.create(o5);
        purchaseDAO.create(o6);
        purchaseDAO.create(o7);
        purchaseDAO.create(o8);
        
    }
    //@InSequence(0) bestämmer ordningen
    @InSequence(0)
    @Test
    public void checkCorrectAmountInserted() {
        
        long Amount = customerDAO.count();
        long Amount2 = purchaseDAO.count();
        
        assertEquals(2L, Amount);
        assertEquals(8L, Amount2);
    }
    
    @InSequence(1)
    @Test
    public void CheckCorectOrderByCustomer() {
        
        
        List<Purchase> retrievedOrder = purchaseDAO.getOrderByCustumer(c1);
        List<Purchase> retrievedOrder2 = purchaseDAO.getOrderByCustumer(c2);
              
        assertEquals(7L, retrievedOrder.size());
        assertEquals(1L, retrievedOrder2.size());
        
        assertEquals(c1, retrievedOrder.get(0).getCustomer());
        assertEquals(c1, retrievedOrder.get(1).getCustomer());
        
        assertEquals(c2, retrievedOrder2.get(0).getCustomer());
    }
    
    @InSequence(2)
    @Test
    public void getCorrectOrderHistoryFormat() {
        
        
        List<Purchase> retrievedOrder = purchaseDAO.getOrderByCustumer(c1);
     
        // OR because it's sorted by date with 2 identical dates
        assertTrue(o1.equals(retrievedOrder.get(0)) || o2.equals(retrievedOrder.get(0)));
        assertTrue(o1.equals(retrievedOrder.get(1)) || o2.equals(retrievedOrder.get(1)));
        
        assertEquals(o3, retrievedOrder.get(2));
        assertEquals(o5, retrievedOrder.get(3));
        assertEquals(o6, retrievedOrder.get(4));
        assertEquals(o7, retrievedOrder.get(5));
        assertEquals(o8, retrievedOrder.get(6));
        
        
        
    }
    
    @InSequence(3)
    @Test
    public void CheckOrderHistoryAfterDeleting() {
        
        
        List<Purchase> retrievedOrder = purchaseDAO.getOrderByCustumer(c1);
     
        assertTrue(retrievedOrder.contains(o5));
        purchaseDAO.remove(o5);
        
       retrievedOrder = purchaseDAO.getOrderByCustumer(c1);
        
        assertFalse(retrievedOrder.contains(o5));  
        
    }
    
    @InSequence(4)
    @Test
    public void checkRetrievedCorrectOrder(){
        List<Purchase> ordersId1 = purchaseDAO.getOrdersByOrderID(1);
        List<Purchase> ordersId2 = purchaseDAO.getOrdersByOrderID(2);
        List<Purchase> ordersId3 = purchaseDAO.getOrdersByOrderID(3);
        
        assertEquals(2, ordersId1.size());
        assertEquals(1, ordersId2.size());
        assertEquals(1, ordersId3.size());
        
    }
    
     @After
    public void cleanup(){
        purchaseDAO.cleanAll();
        customerDAO.cleanAll();
        productDAO.cleanAll();
    }
    
}
