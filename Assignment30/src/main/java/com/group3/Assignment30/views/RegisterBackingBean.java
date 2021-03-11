package com.group3.Assignment30.views;

import com.group3.Assignment30.model.entity.Customer;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Data;

@Data
@Named
@ViewScoped
public class RegisterBackingBean implements Serializable {
    
    
    private Customer customer;
   
    @PostConstruct
    public void init(){
        customer = new Customer();
    }
            
    
    
    
}
