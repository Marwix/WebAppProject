
package com.group3.Assignment30.controller;

import com.group3.Assignment30.model.dao.CustomerDAO;
import com.group3.Assignment30.model.entity.Customer;
import com.group3.Assignment30.service.MessageCenter;
import com.group3.Assignment30.service.PasswordManager;
import com.group3.Assignment30.views.LoginBackingBean;
import com.group3.Assignment30.views.RegisterBackingBean;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Data;

@Data
@Named
@ViewScoped
public class RegisterController implements Serializable{
    
    @Inject
    private RegisterBackingBean registerBackingBean;
    @Inject
    private LoginBackingBean loginBackingBean;
    @EJB
    private CustomerDAO customerDAO;
    
    private SessionContextController sessionContextController = SessionContextController.getInstance();
    
    
    public String onRegister(){
        // Retrieve user and check if email in use
        Customer newCustomer = registerBackingBean.getCustomer();
        boolean emailTaken = customerDAO.checkRegistered(newCustomer.getEmail()).size()==1;
        
        if (emailTaken) {
            MessageCenter.SendPageMessage(FacesMessage.SEVERITY_ERROR, "Email already in use!", "accountInfoForm:email");
        }
        
        //Hash and Salt password
        // Turn PasswordManager to singleton/inject (?)
        PasswordManager pwManager = new PasswordManager();
        List<byte[]> pw = pwManager.HashNSalt(newCustomer.getPassword());
        newCustomer.setPassword(pwManager.passwordByteArrToString(pw.get(1)));
        newCustomer.setSalt(pw.get(0));
        
        //Add user to DB
        customerDAO.create(newCustomer);
        
        sessionContextController.setAttribute("user_id", newCustomer.getUser_id());
        loginBackingBean.setLoggedin(true);
        
        return "homepage";
    }
    
}
