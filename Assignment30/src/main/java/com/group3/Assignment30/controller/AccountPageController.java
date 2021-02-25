package com.group3.Assignment30.controller;

import com.group3.Assignment30.model.dao.CustomerDAO;
import com.group3.Assignment30.model.dao.ProductDAO;
import com.group3.Assignment30.model.dao.PurchaseDAO;
import com.group3.Assignment30.model.entity.Customer;
import com.group3.Assignment30.model.entity.Product;
import com.group3.Assignment30.model.entity.Purchase;
import com.group3.Assignment30.views.AccountBackingBean;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
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
    
    @EJB
    private CustomerDAO customerDAO;
    
    @EJB
    private ProductDAO productDAO;

    @EJB
    private PurchaseDAO purchaseDAO;
    
    @PostConstruct
    public void init(){
     List<Customer> customerInfo = customerDAO.getUserInformationByID(600);
      
      customer = customerInfo.get(0);
      System.out.println(customer.getEmail());
      System.out.println("Jag hatar mitt liv");
      
      accountBackingBean.setEmail(customer.getEmail());
      accountBackingBean.setFirstname(customer.getFirst_name());
      accountBackingBean.setLastname(customer.getLast_name());
      accountBackingBean.setPhonenumber(customer.getPhonenumber());
      accountBackingBean.setCity(customer.getCity());
      accountBackingBean.setZip(customer.getPostal_code());
      accountBackingBean.setAddress(customer.getAdress());
      getOrderHistory();
    }
    
    public void updateUserInformation() {
        customer.setEmail(accountBackingBean.getEmail());
        customer.setFirst_name(accountBackingBean.getFirstname());
        customer.setLast_name(accountBackingBean.getLastname());
        customer.setPhonenumber(accountBackingBean.getPhonenumber());
        customer.setAdress(accountBackingBean.getAddress());
        customer.setCity(accountBackingBean.getCity());
        customer.setPostal_code(accountBackingBean.getZip());
        
        try {
            customerDAO.updateUserInformation(customer);
        } catch (EJBException e) {
            if(e.toString().contains("duplicate key")){
                System.out.println("dublicate Email");
                
            }

        }
        System.out.println("JAAAAAA jag är här åtminstone");
        
    }
    
    public void changePassword(){
       
        List<Customer> customerInfo = customerDAO.getUserInformationByID(600);
      
        customer = customerInfo.get(0);
        
        
        if(customer.getPassword().equals(accountBackingBean.getOldpassword())){
            customer.setPassword(accountBackingBean.getPassword());
            customerDAO.changePassword(customer);
        }
    }
    
    public void getOrderHistory(){
        
        accountBackingBean.setPurchases(purchaseDAO.getOrderByCustumer(customer));
       
    }
    
    
}
    
