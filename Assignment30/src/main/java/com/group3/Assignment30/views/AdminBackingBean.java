
package com.group3.Assignment30.views;

import com.group3.Assignment30.model.entity.Coupon;
import com.group3.Assignment30.model.entity.Product;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Data;

@Data
@Named
@ViewScoped
public class AdminBackingBean implements Serializable{
    
    private Product product;
    private Coupon coupon;
    
    List<Product> productList;
    List<Coupon> couponList;
    
    @PostConstruct
    public void init(){
        product = new Product();
        coupon = new Coupon();
    }    
}
