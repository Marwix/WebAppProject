/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group3.Assignment30.model.entity;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import lombok.Data;

/**
 *
 * @author sahin
 */
@Data
@SessionScoped
public class Cart implements Serializable {
    
    private Product product;
    //@inject-- gör detta på platserna som behöver en cart.
}
