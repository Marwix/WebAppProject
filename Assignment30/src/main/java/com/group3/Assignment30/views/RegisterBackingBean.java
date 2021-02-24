package com.group3.Assignment30.views;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Named
@ViewScoped
public class RegisterBackingBean implements Serializable {
    
   private int id;
   @NotEmpty private String username;
   @Email @NotEmpty private String email;
   @NotEmpty private String password;
   @NotEmpty private String firstname;
   @NotEmpty private String lastname;
   @NotEmpty private String phonenumber;
   @NotEmpty private String address;
   @NotEmpty private String city;
   @NotEmpty private String zip;
   
            
    
    
    
}
