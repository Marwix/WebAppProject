
package com.group3.Assignment30.controller;

import com.group3.Assignment30.model.dao.CustomerDAO;
import com.group3.Assignment30.model.entity.Customer;
import com.group3.Assignment30.service.PasswordManager;
import com.group3.Assignment30.views.LoginBackingBean;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.print.attribute.standard.Severity;
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
        
        PasswordManager pwManager = new PasswordManager();
        
        // See if user exists
        List<Customer> customer = customerDAO.checkUserExist(loginBackingBean.getEmail());
        
        //If user does not exist
        if (customer.size()==0){
            
            sendWarning(FacesMessage.SEVERITY_ERROR, "Account not found");
        } 
        else {
            if (pwManager.passwordMatching(customer.get(0).getPassword(), customer.get(0).getSalt(), loginBackingBean.getPassword())){
                sessionContextController.setAttribute("user_id", customer.get(0).getUser_id());
                return "accountPage";
            } else {
               sendWarning(FacesMessage.SEVERITY_ERROR, "Account not found"); 
            }
           
            
        }
        
        return "Not found";
    }
    
    private void sendWarning(FacesMessage.Severity severity, String message){
            FacesMessage fMessage = new FacesMessage();
            fMessage.setSeverity(severity);
            fMessage.setSummary(message);
            FacesContext.getCurrentInstance().addMessage(null,fMessage);
    }
    
    
}
