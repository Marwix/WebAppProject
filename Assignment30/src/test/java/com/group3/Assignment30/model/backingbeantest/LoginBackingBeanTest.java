
package com.group3.Assignment30.model.backingbeantest;

import com.group3.Assignment30.views.LoginBackingBean;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class LoginBackingBeanTest {
    private LoginBackingBean loginBackingBean;
    
    @Before
    public void init(){
        loginBackingBean = new LoginBackingBean();
    }
    
    @Test
    public void gettersSettersTest(){
        loginBackingBean.setId(5);
        assertTrue(5 == loginBackingBean.getId());
        loginBackingBean.setId(Integer.MAX_VALUE);
        assertTrue(Integer.MAX_VALUE == loginBackingBean.getId());
        loginBackingBean.setId(Integer.MIN_VALUE);
        assertTrue(Integer.MIN_VALUE == loginBackingBean.getId());
        
        loginBackingBean.setEmail("Mail.se");
        assertEquals("Mail.se",loginBackingBean.getEmail());
        loginBackingBean.setEmail("secondmail@m.se");
        assertEquals("secondmail@m.se",loginBackingBean.getEmail());
        
        loginBackingBean.setPassword("password");
        assertEquals("password", loginBackingBean.getPassword());
        loginBackingBean.setPassword("drowssap");
        assertEquals("drowssap", loginBackingBean.getPassword());
        
        loginBackingBean.setLoggedin(true);
        assertTrue(loginBackingBean.isLoggedin());
        loginBackingBean.setLoggedin(false);
        assertFalse(loginBackingBean.isLoggedin());
    }
}
