
package com.group3.Assignment30.controller;

import com.group3.Assignment30.model.dao.CustomerDAO;
import com.group3.Assignment30.model.entity.Customer;
import com.group3.Assignment30.views.RegisterBackingBean;
import java.io.Serializable;
import javax.ejb.EJB;
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
    
    private Customer customer;
    @EJB
    private CustomerDAO customerDAO;
    
    
    
    public String onRegister(){
        customer = new Customer();
        
        customer.setUser_id(registerBackingBean.getId());
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
        System.out.println(registerBackingBean.getUsername());
        System.out.println(registerBackingBean.getLastname());
        System.out.println(registerBackingBean.getEmail());
        System.out.println(registerBackingBean.getFirstname());
        System.out.println(registerBackingBean.getPassword());
        System.out.println(registerBackingBean.getZip());
        System.out.println(registerBackingBean.getAddress());
        System.out.println(registerBackingBean.getCity());
        return registerBackingBean.getUsername();
    }
    
}
