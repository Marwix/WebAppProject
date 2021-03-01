/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group3.Assignment30.service;

import sun.security.krb5.internal.PAData;

/**
 *
 * @author Robin
 */
public class PasswordManager {
    
    public int[] HashNSalt(String password) {
        int[] hns = new int[2];
        hns[0] = getRandomSalt();
        
        //TODO - Improve hash
        hns[1] = (password+hns[0]).hashCode();
        return hns;
    } 
    
    private int getRandomSalt() {
        return (int)(Math.random() * 1009);
    }
    
    public boolean passwordMatching(String stored_password, int salt, String entered_password){
        System.out.println((entered_password+salt).hashCode());
        return stored_password.equals(String.valueOf((entered_password+salt).hashCode()));
    }
    
}
