
package com.group3.Assignment30.views;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Named
@SessionScoped
public class LoginBackingBean implements Serializable{
    
    private int id;
    @NotEmpty private String Email;
    @NotEmpty private String password;
    private boolean loggedin;
}
