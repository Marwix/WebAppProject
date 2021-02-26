
package com.group3.Assignment30.controller;

import com.group3.Assignment30.model.dao.CustomerDAO;
import com.group3.Assignment30.model.entity.Customer;
import com.group3.Assignment30.views.RegisterBackingBean;
import java.io.Serializable;
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
@SessionScoped
public class RegisterController implements Serializable{
    
    @Inject
    private RegisterBackingBean registerBackingBean;
    
    private Customer customer;
    @EJB
    private CustomerDAO customerDAO;
    
    private SessionContextController sessionContextController = SessionContextController.getInstance();
    
    
    public String onRegister(){
        boolean emailTaken = customerDAO.checkUserExist(registerBackingBean.getEmail()).size()==1;
        
        if (emailTaken) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary("Email already in use.");
            FacesContext.getCurrentInstance().addMessage(null,message);
        }
        customer = new Customer();
        
        customer.setFirst_name(registerBackingBean.getFirstname());
        customer.setLast_name(registerBackingBean.getLastname());
        customer.setEmail(registerBackingBean.getEmail());
        customer.setPassword(registerBackingBean.getPassword());
        customer.setPhonenumber(registerBackingBean.getPhonenumber());
        customer.setCity(registerBackingBean.getCity());
        customer.setAdress(registerBackingBean.getAddress());
        customer.setPostal_code(registerBackingBean.getZip());
        
        customerDAO.create(customer);
        
        System.out.println("hej");
        System.out.println(registerBackingBean.getLastname());
        System.out.println(registerBackingBean.getEmail());
        System.out.println(registerBackingBean.getFirstname());
        System.out.println(registerBackingBean.getPassword());
        System.out.println(registerBackingBean.getZip());
        System.out.println(registerBackingBean.getAddress());
        System.out.println(registerBackingBean.getCity());
        sessionContextController.setAttribute("user_id", customer.getUser_id());
        
        return "accountPage";
    }
    
}
