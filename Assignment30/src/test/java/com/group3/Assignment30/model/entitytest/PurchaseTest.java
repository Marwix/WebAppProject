package com.group3.Assignment30.model.entitytest;

import com.group3.Assignment30.model.entity.Customer;
import com.group3.Assignment30.model.entity.Product;
import com.group3.Assignment30.model.entity.Purchase;
import java.time.LocalDate;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;


public class PurchaseTest {
    Purchase purchase;
    
    @Test
    public void createPurchaseNoArgsTest(){
        purchase = new Purchase();
        assertTrue(purchase.getId() == 0);
        assertEquals(null, purchase.getTime());
        assertEquals(null, purchase.getCustomer());
        assertTrue(0 == purchase.getCount());
        assertTrue(0 == purchase.getPrice());
        // Id is generated so not testing that
    }
    
    
    @Test
    public void createPurchaseAllArgsTest(){
        Customer customer = new Customer();
        customer.setUser_id(0);
        Customer customer2 = new Customer();
        customer2.setUser_id(1);
        
        Product product = new Product();
        product.setProdoct_id(0);
        Product product2 = new Product();
        product.setProdoct_id(1);
        
        purchase = new Purchase(800, LocalDate.now(), customer, product, 5, 500, 999);
        assertTrue(800 == purchase.getOrder_id());
        assertEquals(LocalDate.now(), purchase.getTime());
        assertEquals(customer, purchase.getCustomer());
        assertNotEquals(customer2, purchase.getCustomer());
        assertEquals(product, purchase.getProducts());
        assertNotEquals(product2, purchase.getProducts());
        assertTrue(5 == purchase.getCount());
        assertTrue(500 == purchase.getPrice());
        assertTrue(999 == purchase.getId());
    }
}
