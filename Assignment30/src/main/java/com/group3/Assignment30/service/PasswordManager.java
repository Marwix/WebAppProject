
package com.group3.Assignment30.service;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import sun.security.krb5.internal.PAData;

public class PasswordManager {
    private int PASSWORD_STRENGTH = 65536;
    private int PASSWORD_KEYLENGTH = 128;
    private int SALT_LENGTH = 16;
    
    public List<byte[]> HashNSalt(String password) {
        ArrayList<byte[]> hashNSalt = new ArrayList<>();
        byte[] salt = getRandomSalt();
        byte[] hashedPW = getHashedPassword(password, salt);
        
        hashNSalt.add(salt);
        hashNSalt.add(hashedPW);
        
        return hashNSalt;
    } 
    
    private byte[] getRandomSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }
    
    private byte[] getHashedPassword(String password, byte[] salt) {
        byte[] hashedPW;
        
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, PASSWORD_STRENGTH, PASSWORD_KEYLENGTH);
        SecretKeyFactory factory;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hashedPW = factory.generateSecret(spec).getEncoded();
        
        } catch (NoSuchAlgorithmException algE) {
            System.out.println("##################################################");
            System.out.println("Password Algorith missing. class: PaswordManager");
            System.out.println("##################################################");
            algE.printStackTrace();
            return null;
        } catch (InvalidKeySpecException keyE){
            System.out.println("##################################################");
            System.out.println("key Spec issue. class: PaswordManager");
            System.out.println("##################################################");
            keyE.printStackTrace();
            return null;
        }
        return hashedPW;
    }
    
    public boolean passwordMatching(String stored_password, byte[] salt, String entered_password){
        byte[] enteredPasswordHash = getHashedPassword(entered_password, salt);
        String enteredPW = passwordByteArrToString(enteredPasswordHash);

        return enteredPW.equals(stored_password);
    }
    
    public String passwordByteArrToString(byte[] password){
        return Base64.getEncoder().encodeToString(password);
    }
    
    public byte[] passwordStringToByteArr(String password) {
        return Base64.getDecoder().decode(password);
    }
    
}
