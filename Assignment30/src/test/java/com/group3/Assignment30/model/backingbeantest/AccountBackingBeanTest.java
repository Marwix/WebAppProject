package com.group3.Assignment30.model.backingbeantest;

import com.group3.Assignment30.controller.AccountPageController;
import com.group3.Assignment30.controller.SessionContextController;
import com.group3.Assignment30.model.dao.CustomerDAO;
import com.group3.Assignment30.model.dao.ProductDAO;
import com.group3.Assignment30.model.dao.PurchaseDAO;
import com.group3.Assignment30.model.entity.Customer;
import com.group3.Assignment30.model.entity.Product;
import com.group3.Assignment30.model.entity.Purchase;
import com.group3.Assignment30.service.PasswordManager;
import com.group3.Assignment30.views.AccountBackingBean;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import javax.faces.context.FacesContext;
import org.jboss.arquillian.junit.Arquillian;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class AccountBackingBeanTest{
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addClasses(CustomerDAO.class, Customer.class, ProductDAO.class, Product.class,
                        PurchaseDAO.class, Purchase.class
                        )
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    
    
    private AccountBackingBean accountBackingBean;
    
    @EJB
    private CustomerDAO customerDAO;
    
    @EJB
    private ProductDAO productDAO;
    
    @EJB
    private PurchaseDAO purchaseDAO;

    private Customer user1;
    
    private Purchase o1;
    private Purchase o2;
    private Purchase o3;
    private Purchase o4;
    private Purchase o5;
    private Purchase o6;
    
    private Product p1;
    private Product p2;
    
    private PasswordManager pwManager;
    private int activeUserID;

    
    @Before
    public void init(){
        
        accountBackingBean = new AccountBackingBean();
        
        pwManager = new PasswordManager();
        List<byte[]> passwordInfo;
        
        //Make account
        user1 =  new Customer();
        user1.setAdress("Goteland");
        user1.setCity("Gothenburg");
        user1.setEmail("allan-ridhaa@live.se");
        user1.setFirst_name("Allan");
        user1.setLast_name("Ridha");
        passwordInfo = pwManager.HashNSalt("Password1");
        user1.setPassword(pwManager.passwordByteArrToString(passwordInfo.get(1)));
        user1.setPhonenumber("0760272740");
        user1.setPostal_code("42455");
        user1.setSalt(passwordInfo.get(0));
        
        //Creating Poducts
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
        
        //Creating orders
        o1 = new Purchase();
        o1.setOrder_id(1);
        o1.setTime(LocalDate.of(2021, 2, 10));
        o1.setCustomer(user1);
        o1.setProducts(p1);
        o1.setCount(2);
        
        
        o2 = new Purchase();
        o2.setOrder_id(1);
        o2.setTime(LocalDate.of(2021, 2, 10));
        o2.setCustomer(user1);
        o2.setProducts(p2);
        o2.setCount(5);
        
        o3 = new Purchase();
        o3.setOrder_id(2);
        o3.setTime(LocalDate.of(2021, 2, 9));
        o3.setCustomer(user1);
        o3.setProducts(p1);
        o3.setCount(2);
        
        o4 = new Purchase();
        o4.setOrder_id(2);
        o4.setTime(LocalDate.of(2021, 2, 9));
        o4.setCustomer(user1);
        o4.setProducts(p2);
        o4.setCount(5);
        
        o5 = new Purchase();
        o5.setOrder_id(3);
        o5.setTime(LocalDate.of(2021, 2, 8));
        o5.setCustomer(user1);
        o5.setProducts(p2);
        o5.setCount(5);
        
        o6 = new Purchase();
        o6.setOrder_id(3);
        o6.setTime(LocalDate.of(2021, 2, 5));
        o6.setCustomer(user1);
        o6.setProducts(p2);
        o6.setCount(5);
        
        
        customerDAO.create(user1);
       
        
        productDAO.create(p1);
        productDAO.create(p2);
        
        purchaseDAO.create(o1);
        purchaseDAO.create(o2);
        purchaseDAO.create(o3);
        purchaseDAO.create(o4);
        purchaseDAO.create(o5);
        purchaseDAO.create(o6);
        
       
        accountBackingBean.setCustomer(user1);
       
        
        
    }
    
    @Test
    public void getOrderHistoryTest(){

        List<Purchase> order = new ArrayList<Purchase>();
        List<List<Purchase>> orders = new ArrayList<List<Purchase>>();
        
        

        order.add(o1);
        order.add(o2);
       
      orders.add(order);
        
       System.err.println("" + orders);
       
       accountBackingBean.showOrderHistory(order);
       assertEquals(orders,accountBackingBean.getPurchases());
       
       order = new ArrayList<Purchase>();
       orders = new ArrayList<List<Purchase>>();
       
       order.add(o3);
       order.add(o4);
       
       
       orders.add(order);
       
       order = new ArrayList<Purchase>();
       
       order.add(o1);
       order.add(o2);
       
       orders.add(order);
       
       order = new ArrayList<Purchase>();
       
       order.add(o1);
       order.add(o3);
       order.add(o2);
       order.add(o4);
       
       System.out.println("" + orders);
       System.out.println("" + accountBackingBean.getPurchases());
        
       
       accountBackingBean.showOrderHistory(order);
       
       assertEquals(orders,accountBackingBean.getPurchases());
       
       
    }
    
    @Test
    public void returnTotalPriceTest(){
        
        List<Purchase> order = new ArrayList<Purchase>();
        List<List<Purchase>> orders = new ArrayList<List<Purchase>>();
        
        

        order.add(o1);
        order.add(o2);
       
        orders.add(order);
        accountBackingBean.setPurchases(orders);
        
       System.err.println("" + orders);
      
       
       double totalprice = ((o1.getPrice() * o1.getCount()) + (o2.getPrice() * o2.getCount()));
       
       assertEquals(totalprice, accountBackingBean.getTotalPriceOfOrder(order), 0.0000);
        
        
    }
    
    @Test
    public void setInstanceVariablesTest(){
       
        //start by setting customer to null
        accountBackingBean.setCustomer(null);
        assertEquals(null, accountBackingBean.getCustomer());
        //set customer to user1
        accountBackingBean.setCustomer(user1);
        assertEquals(user1, accountBackingBean.getCustomer());
        
        //check if passwords are null
        assertEquals(null, accountBackingBean.getOldpassword());
        assertEquals(null, accountBackingBean.getPassword());
        
        //set random password(not safe password should be hashed but for testing this doesnt need to be hashed)
        accountBackingBean.setPassword("123456");
        accountBackingBean.setOldpassword("123456");
        
        //check if its correct password
        assertEquals("123456", accountBackingBean.getOldpassword());
        assertEquals("123456", accountBackingBean.getPassword());
        
        
    }
    
    @After
    public void release() {

        purchaseDAO.remove(o1);
        purchaseDAO.remove(o2);
        purchaseDAO.remove(o3);
        purchaseDAO.remove(o4);
        purchaseDAO.remove(o5);
        purchaseDAO.remove(o6);
        
        productDAO.remove(p1);
        productDAO.remove(p2);
        
        
        
         customerDAO.remove(user1);
    }
   
}


