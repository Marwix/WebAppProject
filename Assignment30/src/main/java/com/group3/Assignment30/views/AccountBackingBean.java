
package com.group3.Assignment30.views;


import com.group3.Assignment30.model.entity.Customer;
import com.group3.Assignment30.model.entity.Purchase;
import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Named
@ViewScoped
public class AccountBackingBean implements Serializable{
  
    Customer customer;
    @NotEmpty String oldpassword; 
    @NotEmpty String password; 
   
   @NotEmpty private List<Purchase> purchases;
 
}
