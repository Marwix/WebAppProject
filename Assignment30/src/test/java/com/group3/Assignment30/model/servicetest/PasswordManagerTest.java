
package com.group3.Assignment30.model.servicetest;

import com.group3.Assignment30.service.PasswordManager;
import org.junit.Assert;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class PasswordManagerTest {
    
    PasswordManager pwManager;
    
    @Before
    public void init(){
        pwManager = new PasswordManager();
    }
    
    @Test
    public void singleTest() {
        String password = "myTestPassword7234";
        int[] SaltNHash = pwManager.HashNSalt(password);
        
        assertTrue(pwManager.passwordMatching(String.valueOf(SaltNHash[1]), SaltNHash[0], password));
        assertFalse(pwManager.passwordMatching(String.valueOf(SaltNHash[1]), SaltNHash[0]+5, password));
        assertFalse(pwManager.passwordMatching(String.valueOf(SaltNHash[1]+5), SaltNHash[0], password));
        assertFalse(pwManager.passwordMatching(String.valueOf(SaltNHash[1]), SaltNHash[0]+5, "someotherpassword"));
    }
    
    @Test
    public void passwordCompareHashNSaltTest(){
        String password1 = "myTestPassword7234";
        String password2 = "s34ep7698n5ys";
        int[] SaltNHash1 = pwManager.HashNSalt(password1);
        int[] SaltNHash2 = pwManager.HashNSalt(password2);
        
        assertNotEquals(SaltNHash1[1], SaltNHash2[1]);
        // Not comparing salt because there's a chance they are equal
        // --> The test might fail by pure chance
    }
    
    
}
