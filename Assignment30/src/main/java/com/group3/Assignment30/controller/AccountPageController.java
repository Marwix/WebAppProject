package com.group3.Assignment30.controller;

import com.group3.Assignment30.model.dao.CustomerDAO;
import com.group3.Assignment30.model.dao.ProductDAO;
import com.group3.Assignment30.model.dao.PurchaseDAO;
import com.group3.Assignment30.model.entity.Customer;
import com.group3.Assignment30.model.entity.Product;
import com.group3.Assignment30.model.entity.Purchase;
import com.group3.Assignment30.service.PasswordManager;
import com.group3.Assignment30.views.AccountBackingBean;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Data;

@Data
@Named
@ViewScoped
public class AccountPageController implements Serializable{
    
    @Inject
    private AccountBackingBean accountBackingBean;
    
    private Customer customer;
    
    private SessionContextController sessionContextController = SessionContextController.getInstance();
    private int activeUserID;
    
    @EJB
    private CustomerDAO customerDAO;
    @EJB
    private ProductDAO productDAO;
    @EJB
    private PurchaseDAO purchaseDAO;
    
    //Load active user data
    @PostConstruct
    public void init(){
        try {
            activeUserID = (int) sessionContextController.getAttribu("user_id");
            List<Customer> customerInfo = customerDAO.getUserInformationByID(activeUserID);

            customer = customerInfo.get(0);

            accountBackingBean.setEmail(customer.getEmail());
            accountBackingBean.setFirstname(customer.getFirst_name());
            accountBackingBean.setLastname(customer.getLast_name());
            accountBackingBean.setPhonenumber(customer.getPhonenumber());
            accountBackingBean.setCity(customer.getCity());
            accountBackingBean.setZip(customer.getPostal_code());
            accountBackingBean.setAddress(customer.getAdress());
            getOrderHistory();
        } catch (Exception e) {
        }
            
        
    }
    
    public void updateUserInformation() {
        customer.setEmail(accountBackingBean.getEmail());
        customer.setFirst_name(accountBackingBean.getFirstname());
        customer.setLast_name(accountBackingBean.getLastname());
        customer.setPhonenumber(accountBackingBean.getPhonenumber());
        customer.setAdress(accountBackingBean.getAddress());
        customer.setCity(accountBackingBean.getCity());
        customer.setPostal_code(accountBackingBean.getZip());
        
        //On email change return error if email is already taken
        try {
            customerDAO.updateUserInformation(customer);
        } catch (EJBException e) {
            if(e.toString().contains("duplicate key")){
                System.out.println("dublicate Email");
                
            }

        }
        
    }
    
    public void changePassword(){
       
        PasswordManager pwManager = new PasswordManager();
        List<Customer> customerInfo = customerDAO.getUserInformationByID(activeUserID);
      
        customer = customerInfo.get(0);
        
        if(pwManager.passwordMatching(customer.getPassword(), customer.getSalt(), accountBackingBean.getOldpassword()))
        {
            customer.setPassword(accountBackingBean.getPassword());
            customerDAO.changePassword(customer);
        }
    }
    
    public void getOrderHistory(){
        
        accountBackingBean.setPurchases(purchaseDAO.getOrderByCustumer(customer));
       
    }
    
    public boolean adminAuthorize(){
        return customer.isAdminAccess();
    }
    
    
}
    
