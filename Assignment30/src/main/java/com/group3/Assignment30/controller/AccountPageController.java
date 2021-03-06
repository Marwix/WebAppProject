package com.group3.Assignment30.controller;

import com.group3.Assignment30.model.dao.CustomerDAO;
import com.group3.Assignment30.model.dao.ProductDAO;
import com.group3.Assignment30.model.dao.PurchaseDAO;
import com.group3.Assignment30.model.entity.Customer;
import com.group3.Assignment30.service.MessageCenter;
import com.group3.Assignment30.service.PasswordManager;
import com.group3.Assignment30.views.AccountBackingBean;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
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
            
            accountBackingBean.setCustomer(customer);
            
            getOrderHistory();
        } catch (NullPointerException e) {
            System.err.println("User not logged in. So the website cant get its userID from SessionContext");
        }
    }
    
    //gets all information about the customer and sends it to customerDAO
    public void updateUserInformation() {

        //On email change return error if email is already taken
        List<Customer> existingCustomer = customerDAO.checkRegistered(accountBackingBean.getCustomer().getEmail());
        
        if (existingCustomer.size() > 0 && existingCustomer.get(0).getUser_id() != accountBackingBean.getCustomer().getUser_id()){
            MessageCenter.SendPageMessage(FacesMessage.SEVERITY_ERROR, "Email already in use!", "accountInfoForm:email");
            return;
        }
        customerDAO.updateUserInformation(accountBackingBean.getCustomer()); 
        MessageCenter.SendPageMessage(FacesMessage.SEVERITY_INFO, "User information successfully updated!");
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
    
    public void getOrderHistory() 
    {
        accountBackingBean.showOrderHistory(purchaseDAO.getOrderByCustumer(accountBackingBean.getCustomer()));

        //accountBackingBean.setPurchases(purchaseDAO.getOrderByCustumer(customer));
    }
    
    public boolean adminAuthorize(){
        return accountBackingBean.getCustomer().isAdminAccess();
    }
}
    
