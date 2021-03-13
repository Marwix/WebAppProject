/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group3.Assigment30.model.backingbeantest;

import com.group3.Assignment30.model.dao.CouponDAO;
import com.group3.Assignment30.model.entity.Coupon;
import com.group3.Assignment30.model.entity.Customer;
import com.group3.Assignment30.model.entity.Product;
import com.group3.Assignment30.model.models.Cart;
import com.group3.Assignment30.views.CheckoutBackingBean;
import java.util.HashMap;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class CheckoutBackingBeanTest {   
    CheckoutBackingBean bean;
    private Product p1;
    private Product p2;
    private Product p3;
           
    private Coupon coupon1;
    private Coupon coupon2;
    
    HashMap<Product, Integer> ItemsCart = new HashMap<>();
    
    private Cart cart;
    
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addClasses(CouponDAO.class, Customer.class, Coupon.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    
    @Before
    public void init() { 
        bean = new CheckoutBackingBean();
        cart = new Cart();

        Customer customer = new Customer();
        customer.setEmail("test@test.se");
        customer.setFirst_name("TestBot");
        bean.setCustomer(customer);
        assertEquals(customer, bean.getCustomer());
        
        assertEquals(null, bean.getCoupon());
        bean.Init();
         
        p1 = new Product(1, "name", 100, 1, 5, "blue", "100x100", "100", "Prod1");
        p2 = new Product(2, "allan", 100, 1, 5, "blue", "100x100", "100", "Prod1");
        p3 = new Product(3, "boy", 100, 0.7, 5, "blue", "100x100", "100", "Prod1");
        ItemsCart.put(p1, 2);
        ItemsCart.put(p2, 3);
        ItemsCart.put(p3, 5);
        cart.setCartInventory(ItemsCart);
        bean.setCart(cart);
        
        // Add coupons for use.
        coupon1 = new Coupon();
        coupon1.setCouponCode("");
        coupon1.setPriceMultiplier(1);
        bean.setCoupon(coupon1);
        
        coupon2 = new Coupon();
        coupon2.setCouponCode("TEST25");
        coupon2.setPriceMultiplier(0.75); 
    }
   
    @Test
    public void getKeyListTest() {
        assertFalse(bean.getKeyList().isEmpty());  
        assertTrue(3 == bean.getKeyList().size());  
    }
    
    @Test
    public void getKeyListNullPointerTest() {
        cart = new Cart();
        cart.setCartInventory(null);
        bean.setCart(cart);
        assertTrue(bean.getKeyList().isEmpty());
        
        bean.setCart(null);
        assertTrue(bean.getKeyList().isEmpty());
    }
    
    @Test
    public void getCountTest() {
        assertTrue(bean.getCount(p1) > 0);
        assertTrue(bean.getCount(p2) > 0);
        assertTrue(bean.getCount(p3) > 0);
        
        bean.getCart().getCartInventory().replace(p1, 10);
        assertEquals(10, bean.getCount(p1));
    }
    
    @Test
    public void removeItemCartTest() {
        // Remove a nd check if item has been removed from cart.
        assertTrue(bean.getCart().getCartInventory().containsKey(p1));
        bean.removeItemCart(p1);
        assertFalse(bean.getCart().getCartInventory().containsKey(p1));
        
        bean.getCart().setCartInventory(new HashMap<>());
        bean.removeItemCart(p1);
        assertEquals("", bean.getCoupon().getCouponCode());
        assertTrue(1 == bean.getCoupon().getPriceMultiplier());
    }
    
    @Test
    public void applyCouponTest() {
       assertEquals("", bean.applyCoupon(coupon1));    
       assertEquals("Coupon code applied!", bean.applyCoupon(coupon2)); 
       assertEquals(coupon2, bean.getCoupon());
       
       Coupon coupon3 = new Coupon();
       coupon3.setPriceMultiplier(0.99);
       coupon3.setCouponCode("discount!");
       assertEquals("Better code applied!", bean.applyCoupon(coupon3));    
    }
   
    @Test
    public void couponAppliedTest() {
        // Check if coupon has been applied to bean.
        bean.setCoupon(coupon2);
        assertTrue(bean.CouponApplied());
        
        // Check if better coupon has been applied.
        Coupon coupon3 = new Coupon();
        coupon3.setCouponCode("RISK50");
        coupon3.setPriceMultiplier(0.5);
        bean.setCoupon(coupon3);
        assertTrue(bean.CouponApplied());
    }

    @Test
    public void getTotalPriceTest() {
        // Check if price is bigger than 0.
        assertTrue(p1.getPrice() > 0 && p2.getPrice() > 0 && p3.getPrice() > 0);
        assertEquals(p1.getPrice(), 100, 0.01);
        
        bean.setCoupon(coupon1);
        assertEquals(1.0, bean.getCoupon().getPriceMultiplier(), 0.01);
        assertTrue((p1.getPrice() * bean.getCount(p1) * p1.getPriceMultiplier()
                + p2.getPrice() * bean.getCount(p2) * p2.getPriceMultiplier()
                + p3.getPrice() * bean.getCount(p3) * p3.getPriceMultiplier())
                == bean.getTotalPrice());
    }

    @Test
    public void resetCartCouponTest() {
        // Reset and check if everything has been reset.
        assertTrue(bean.resetCartAndCoupon());
        assertTrue(bean.getCart().getCartInventory().isEmpty());
        assertTrue(bean.getCoupon().getPriceMultiplier() == 1);
    }
}
