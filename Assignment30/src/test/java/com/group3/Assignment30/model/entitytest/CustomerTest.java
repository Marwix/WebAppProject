
package com.group3.Assignment30.model.entitytest;

import com.group3.Assignment30.model.entity.Customer;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class CustomerTest {
    private Customer customer;
    
    @Test
    public void customerNoArgs(){
        customer = new Customer();
        assertEquals(null, customer.getFirst_name());
        assertEquals(null, customer.getLast_name());
        assertEquals(null, customer.getEmail());
        assertEquals(null, customer.getPassword());
        assertEquals(null, customer.getPhonenumber());
        assertEquals(null, customer.getCity());
        assertEquals(null, customer.getAdress());
        assertEquals(null, customer.getPostal_code());
        assertArrayEquals(null, customer.getSalt());
        assertFalse(customer.isAdminAccess());
        //Id not tested because it is generated
    }
    
    @Test
    public void customerAllArgs(){
        customer = new Customer(10, "Test", "TestName", "email@email.se", "password", "0123456789", "City", "adress", "44444", null, true);
        assertTrue(10 == customer.getUser_id());
        assertEquals("Test", customer.getFirst_name());
        assertEquals("TestName", customer.getLast_name());
        assertEquals("email@email.se", customer.getEmail());
        assertEquals("password", customer.getPassword());
        assertEquals("0123456789", customer.getPhonenumber());
        assertEquals("City", customer.getCity());
        assertEquals("adress", customer.getAdress());
        assertEquals("44444", customer.getPostal_code());
        assertArrayEquals(null, customer.getSalt());
        assertTrue(customer.isAdminAccess());
    }
    
    
}
