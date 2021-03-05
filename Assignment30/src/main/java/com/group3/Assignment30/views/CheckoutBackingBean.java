/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group3.Assignment30.views;

import com.group3.Assignment30.model.entity.Product;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Named
@SessionScoped
public class CheckoutBackingBean implements Serializable {
    @NotEmpty private String country;
    @NotEmpty private String coupon;
    @Email @NotEmpty private String email;
    @NotEmpty private String firstname;
    @NotEmpty private String lastname;
    @NotEmpty private String address;
    @NotEmpty private String city;
    @NotEmpty private String postcode;
    @NotEmpty private String phonenumber;
    private HashMap<Product,Integer> products;
}
