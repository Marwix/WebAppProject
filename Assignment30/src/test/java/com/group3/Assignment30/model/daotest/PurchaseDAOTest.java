package com.group3.Assignment30.model.daotest;


import com.group3.Assignment30.model.dao.CustomerDAO;
import com.group3.Assignment30.model.dao.ProductDAO;
import com.group3.Assignment30.model.dao.PurchaseDAO;
import com.group3.Assignment30.model.entity.Customer;
import com.group3.Assignment30.model.entity.Purchase;
import com.group3.Assignment30.model.entity.Product;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    
    private List<Product> products_list;
    
    
    
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addClasses(PurchaseDAO.class, Purchase.class)
                .addClasses(CustomerDAO.class, Customer.class)
                //.addClasses(ProductDAO.class, Product.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    
    @EJB
    private PurchaseDAO purchaseDAO;
    @EJB
    private CustomerDAO customerDAO;
   // @EJB
    //private ProductDAO productDAO;

    @Before
    public void init() {
        
       // p1 = new Product(1, "Catapult", 30000, 4, 1, "Blue", "3m","3m","3m", "3m","HIGH", "TEST");
        //p2 = new Product(2, "Catapult", 30000, 4, 1, "Green", "3m","3m","3m", "3m","HIGH", "TEST");
        //p3 = new Product(3, "Catapult", 30000, 4, 1, "Red", "3m","3m","3m", "3m","HIGH", "TEST");
        //p4 = new Product(4, "Plane", 30000, 4, 1, "Red", "3m","3m","3m", "3m","HIGH", "TEST");
        
        //products_list = new ArrayList<Product>();
        
       // products_list.add(p1);
       // products_list.add(p2);
       // products_list.add(p3);
       // products_list.add(p4);
        
        c1 = new Customer(1, "Sahin1121@hotmail.com", "123456", "Keskin", "Keskin", "0704797796", "Goteborg", "Angered", "42437");
        c2 = new Customer(2, "Robin1121@hotmail.com", "123456", "Robin", "Rehnberg", "0704797796", "Goteborg", "Angered", "42437");
        o1 = new Purchase(1, LocalDate.of(2021, 2, 10),c1 );
        o2 = new Purchase(2, LocalDate.of(2021, 2, 8),c2);
        o3 = new Purchase(3, LocalDate.of(2021, 2, 9),c1);
        o4 = new Purchase(4, LocalDate.of(2020, 2, 10),c2);
        
        o5 = new Purchase(5, LocalDate.of(2021, 1, 10),c1);
        o6 = new Purchase(6, LocalDate.of(2020, 2, 10),c1);
        o7 = new Purchase(7, LocalDate.of(2020, 2, 2),c1);
        o8 = new Purchase(8, LocalDate.of(2020, 1, 5),c1);
        
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
        
        //productDAO.create(p1);
        //productDAO.create(p2);
        //productDAO.create(p3);
       // productDAO.create(p4);
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
              
        assertEquals(6L, retrievedOrder.size());
        assertEquals(2L, retrievedOrder2.size());
        
        assertEquals(c1, retrievedOrder.get(0).getCustomer());
        assertEquals(c1, retrievedOrder.get(1).getCustomer());
        
        assertEquals(c2, retrievedOrder2.get(0).getCustomer());
        assertEquals(c2, retrievedOrder2.get(1).getCustomer());
    }
    
    @InSequence(2)
    @Test
    public void getCorrectOrderHistoryFormat() {
        
        
        List<Purchase> retrievedOrder = purchaseDAO.getOrderByCustumer(c1);
     
        
        assertEquals(o1, retrievedOrder.get(0));
        assertEquals(o3, retrievedOrder.get(1));
        assertEquals(o5, retrievedOrder.get(2));
        assertEquals(o6, retrievedOrder.get(3));
        assertEquals(o7, retrievedOrder.get(4));
        assertEquals(o8, retrievedOrder.get(5));
        
        
        
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
    
    @After
    public void cleanup(){
       purchaseDAO.cleanAll();
       customerDAO.cleanAll();
    }
    
}
