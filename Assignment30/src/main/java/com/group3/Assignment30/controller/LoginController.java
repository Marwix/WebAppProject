
package com.group3.Assignment30.controller;

import com.group3.Assignment30.model.dao.CustomerDAO;
import com.group3.Assignment30.views.LoginBackingBean;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Data;

@Data
@Named
@SessionScoped
public class LoginController  implements Serializable{
    
    @Inject
    private LoginBackingBean loginBackingBean;
    
    @EJB
    private CustomerDAO customerDAO;
    
    private SessionContextController sessionContextController = SessionContextController.getInstance();
    
    public String onLogin(){
        boolean exists = customerDAO.checkUserLogin(loginBackingBean.getEmail(), loginBackingBean.getPassword()).size() == 1;
        
        if (exists){
          System.out.println("YAY Logged in!");
           sessionContextController.setAttribute("name", loginBackingBean.getEmail()); 
        } 
        else
            System.out.println("who da fuck are you");
        
        return "hej";
    }
    
    
}
