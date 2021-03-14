
package com.group3.Assignment30.model.backingbeantest;

import com.group3.Assignment30.model.entity.Product;
import com.group3.Assignment30.model.models.Cart;
import com.group3.Assignment30.views.CartBackingBean;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;


public class CartBackingBeanTest {
    CartBackingBean cartBackingBean;
    Product p1;
    Product p2;
    
    @Before
    public void init(){
        cartBackingBean = new CartBackingBean();
        cartBackingBean.setCart(new Cart());
        p1 = new Product();
        p1.setProduct_name("Product1");
        p1.setProdoct_id(0);
        
        p2 = new Product();
        p2.setProduct_name("Product2");
        p2.setProdoct_id(1);
        
    }
    
    @Test
    public void addItemToCartTest(){
        Product p1 = new Product();
        p1.setProduct_name("Product1");
        p1.setProdoct_id(0);
        
        Product p2 = new Product();
        p2.setProduct_name("Product2");
        p2.setProdoct_id(1);
        
        //Make sure it starts out empty
        assertEquals(null, cartBackingBean.getCart().getCartInventory());
        assertEquals("", cartBackingBean.getAmountItemsCart());
        assertTrue(cartBackingBean.getCount() == 0);
        
        //Add item when cart inventory is null and count is 0
        cartBackingBean.addItemToCart(p1);
        assertTrue(cartBackingBean.getCart().getCartInventory().keySet().size() == 1);
        assertEquals("1", cartBackingBean.getAmountItemsCart());
        assertTrue(cartBackingBean.getCart().getCartInventory().keySet().contains(p1));
        assertTrue(1 == cartBackingBean.getCart().getCartInventory().get(p1));
        assertEquals(String.valueOf(cartBackingBean.getCount()), cartBackingBean.getAmountItemsCart());
        
        //Add the same item again
        cartBackingBean.addItemToCart(p1);
        assertTrue(cartBackingBean.getCart().getCartInventory().keySet().size() == 1);
        assertTrue(2 == cartBackingBean.getCart().getCartInventory().get(p1));
        
        //Add item when inventory is not of size 0
        cartBackingBean.addItemToCart(p2);
        assertTrue(cartBackingBean.getCart().getCartInventory().keySet().size() == 2);
        assertEquals("3", cartBackingBean.getAmountItemsCart());
        assertTrue(cartBackingBean.getCart().getCartInventory().keySet().contains(p1));
        assertTrue(cartBackingBean.getCart().getCartInventory().keySet().contains(p2));
        assertEquals(String.valueOf(cartBackingBean.getCount()), cartBackingBean.getAmountItemsCart());
    }
    
    @Test
    public void removeItemFromCartTest(){
        cartBackingBean.addItemToCart(p1);
        cartBackingBean.addItemToCart(p2);
        cartBackingBean.addItemToCart(p2);
        assertEquals("3", cartBackingBean.getAmountItemsCart());
        System.out.println(cartBackingBean.getCart().getCartInventory().get(p1));
        assertTrue(1 == cartBackingBean.getCart().getCartInventory().get(p1));
        assertTrue(2 == cartBackingBean.getCart().getCartInventory().get(p2));
        
        cartBackingBean.removeItemFromCart(p1);
        assertEquals("2", cartBackingBean.getAmountItemsCart());
        
        cartBackingBean.removeItemFromCart(p2);
        assertEquals("", cartBackingBean.getAmountItemsCart());
    }
}
