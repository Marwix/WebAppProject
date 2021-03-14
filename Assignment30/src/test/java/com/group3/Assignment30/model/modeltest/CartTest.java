
package com.group3.Assignment30.model.modeltest;

import com.group3.Assignment30.model.entity.Product;
import com.group3.Assignment30.model.models.Cart;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class CartTest {
    Cart cart;
    
    @Before
    public void init(){
        cart = new Cart();
    }
    
    @Test
    public void cartInitTest(){
        assertEquals(null, cart.getCartInventory());
        cart.init();
        assertNotEquals(null, cart.getCartInventory());
        assertTrue(cart.getCartInventory().keySet().size() == 0);
        assertEquals(HashMap.class,cart.getCartInventory().getClass());
    }
}
