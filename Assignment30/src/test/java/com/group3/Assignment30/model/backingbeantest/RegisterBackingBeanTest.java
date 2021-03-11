
package com.group3.Assignment30.model.backingbeantest;

import com.group3.Assignment30.model.entity.Customer;
import com.group3.Assignment30.views.RegisterBackingBean;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Before;
import org.junit.Test;


public class RegisterBackingBeanTest {
    private RegisterBackingBean registerBackingBean;
    
    @Before
    public void init(){
        registerBackingBean = new RegisterBackingBean();
    }
    
    @Test
    public void getSetTest(){
        assertEquals(null, registerBackingBean.getCustomer());
        Customer c1 = new Customer();
        c1.setFirst_name("Name");
        registerBackingBean.setCustomer(c1);
        assertEquals(c1, registerBackingBean.getCustomer());
        assertNotEquals(null, registerBackingBean.getCustomer());
        
    }
    
    @Test
    public void initTest(){
        assertEquals(null, registerBackingBean.getCustomer());
        registerBackingBean.init();
        assertNotEquals(null, registerBackingBean.getCustomer());
        assertEquals(Customer.class, registerBackingBean.getCustomer().getClass());
    }
}
