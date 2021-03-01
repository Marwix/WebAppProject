
package com.group3.Assignment30.views;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Named
@ViewScoped
public class LoginBackingBean implements Serializable{
    
    private int id;
    @NotEmpty private String Email;
    @NotEmpty private String password;
}
