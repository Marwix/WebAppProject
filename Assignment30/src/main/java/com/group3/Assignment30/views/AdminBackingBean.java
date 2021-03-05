/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group3.Assignment30.views;

import com.group3.Assignment30.model.entity.Coupon;
import com.group3.Assignment30.model.entity.Product;
import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Named
@ViewScoped
public class AdminBackingBean implements Serializable{
    @NotEmpty private String productName;
    private int price;
    private int stars;
    @NotEmpty private String color;
    @NotEmpty private String measurements;
    @NotEmpty private String weight;
    @NotEmpty private String description;
    
    @NotEmpty private String newCouponCode;
    private int newCouponMultiplier;
    
    List<Product> productList;
    List<Coupon> couponList;
    
}
