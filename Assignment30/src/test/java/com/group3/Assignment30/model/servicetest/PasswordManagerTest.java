
package com.group3.Assignment30.model.servicetest;

import com.group3.Assignment30.service.PasswordManager;
import java.util.List;
import static org.junit.Assert.assertEquals;
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
        List<byte[]> SaltNHash = pwManager.HashNSalt(password);
        byte[] badSalt = SaltNHash.get(0).clone();
        badSalt[2] = 2;
        
        assertTrue(pwManager.passwordMatching(pwManager.passwordByteArrToString(SaltNHash.get(1)), SaltNHash.get(0), password));
        assertFalse(pwManager.passwordMatching(pwManager.passwordByteArrToString(SaltNHash.get(1)), badSalt, password));
        assertFalse(pwManager.passwordMatching(pwManager.passwordByteArrToString(SaltNHash.get(1))+5, SaltNHash.get(0), password));
        assertFalse(pwManager.passwordMatching(pwManager.passwordByteArrToString(SaltNHash.get(1)), SaltNHash.get(0), "someotherpassword"));
    }
    
    @Test
    public void byteToarrToStringTest(){
        String pw = "TestPassword";
        String testString = pwManager.passwordByteArrToString(pwManager.passwordStringToByteArr(pw));
        
        assertEquals(pw, testString);
    }
    
    @Test
    public void passwordCompareHashNSaltTest(){
        String password1 = "myTestPassword7234";
        String password2 = "s34ep7698n5ys";
        List<byte[]> SaltNHash1 = pwManager.HashNSalt(password1);
        List<byte[]> SaltNHash2 = pwManager.HashNSalt(password2);
        
        assertNotEquals(SaltNHash1.get(1), SaltNHash2.get(1));
        // Not comparing salt because there's a chance they are equal
        // --> The test might fail by pure chance
    }
    
    
}
